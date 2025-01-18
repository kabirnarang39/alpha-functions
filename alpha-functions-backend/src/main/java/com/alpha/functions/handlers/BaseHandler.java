package com.alpha.functions.handlers;

import ch.qos.logback.core.joran.spi.HttpUtil;
import com.alpha.functions.dto.*;
import com.alpha.functions.utils.AException;
import com.alpha.functions.utils.enums.AlphaOperations;
import com.alpha.functions.utils.enums.Response;
import com.alpha.functions.utils.utilities.JsonUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class BaseHandler {

    @Autowired
    ARequestWrapper aRequestWrapper;

    @Autowired
    ARequest aRequest;

    @Autowired
    AUploadRequest aUploadRequest;

    @Autowired
    JsonUtils jsonUtils;

    @Autowired
    AResponse aResponse;

    public static AFunction aFunction = new AFunction();

    public void preHandle() throws IOException {
        String data = null;
        if (aUploadRequest != null && aUploadRequest.getHttpRequest() instanceof MultipartHttpServletRequest) {
            data = aUploadRequest.getData();
            aUploadRequest.setRequestStartTime(LocalDateTime.now());
        } else if (aRequest != null) {
            if (aRequest.getRequestMethod().equalsIgnoreCase(HttpUtil.RequestMethod.GET.toString())) {
                Map<String, String> convertedMap = new HashMap<>();
                for (Map.Entry<String, String[]> entry : aRequest.getQueryParams().entrySet()) {
                    String key = entry.getKey();
                    String[] values = entry.getValue();
                    if (values != null && values.length > 0) {
                        String latestValue = values[values.length - 1];
                        convertedMap.put(key, latestValue);
                    }
                }
                data = jsonUtils.convertToJsonString(convertedMap);
                aRequest.setRequestStartTime(LocalDateTime.now());
            } else {
                data = aRequest.getData().toString();
                aRequest.setRequestStartTime(LocalDateTime.now());
            }
        }
        aFunction = jsonUtils.convertStringToClass(data, AFunction.class);
    }


    public AResponse handleRequest() {
        Object response = null;
        try {
            preHandle();
            if (aRequestWrapper.getAlphaFunctionOperation().equalsIgnoreCase(AlphaOperations.CREATE.operation)) {
                response = create(aFunction);
            } else if (aRequestWrapper.getAlphaFunctionOperation().equalsIgnoreCase(AlphaOperations.EXECUTE.operation)) {
                response = execute(aFunction);
            } else if (aRequestWrapper.getAlphaFunctionOperation().equalsIgnoreCase(AlphaOperations.READ.operation)) {
                response = read(aFunction);
            } else {
                throw new AException(Response.INVALID_OPERATION.message);
            }
            postHandle(response);
        } catch (Exception e) {
            exceptionHandle(response, e);
        }
        return aResponse;
    }

    private void exceptionHandle(Object response, Exception e) {
        if (aUploadRequest != null && aUploadRequest.getHttpRequest() instanceof MultipartHttpServletRequest) {
            aUploadRequest.setRequestEndTime(LocalDateTime.now());
            HttpServletResponse servletResponse = aUploadRequest.getHttpResponse();
            servletResponse.setStatus((int) Response.INTERNAL_SERVER_ERROR.code);
        } else {
            aRequest.setRequestEndTime(LocalDateTime.now());
            HttpServletResponse servletResponse = aRequest.getHttpResponse();
            servletResponse.setStatus((int) Response.INTERNAL_SERVER_ERROR.code);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("response", response);
        aResponse.setCode(Response.INTERNAL_SERVER_ERROR.code);
        aResponse.setMessage(e.getMessage());
        aResponse.setData(data);
        aResponse.setHasError(true);
    }

    public void postHandle(Object response) {
        Map<String, Object> data = new HashMap<>();
        data.put("response", response);
        aResponse.setCode(Response.SUCCESS.code);
        aResponse.setMessage(Response.SUCCESS.message);
        aResponse.setData(data);
        aResponse.setHasError(false);
        if (aUploadRequest != null && aUploadRequest.getHttpRequest() instanceof MultipartHttpServletRequest) {
            aUploadRequest.setRequestEndTime(LocalDateTime.now());
            HttpServletResponse servletResponse = aUploadRequest.getHttpResponse();
            servletResponse.setStatus((int) Response.SUCCESS.code);
        } else {
            aRequest.setRequestEndTime(LocalDateTime.now());
            HttpServletResponse servletResponse = aRequest.getHttpResponse();
            servletResponse.setStatus((int) Response.SUCCESS.code);
        }
    }

    public abstract Object create(AFunction aFunction);

    public abstract Object read(AFunction aFunction);

    public abstract Object update(AFunction aFunction);

    public abstract Object delete(AFunction aFunction);

    public abstract Object execute(AFunction aFunction);
}
