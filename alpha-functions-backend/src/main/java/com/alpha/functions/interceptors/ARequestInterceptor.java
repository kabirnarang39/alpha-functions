package com.alpha.functions.interceptors;

import com.alpha.functions.dto.ARequest;
import com.alpha.functions.dto.ARequestWrapper;
import com.alpha.functions.dto.AUploadRequest;
import com.alpha.functions.utils.utilities.FileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ARequestInterceptor implements HandlerInterceptor {

    @Autowired
    ARequestWrapper aRequestWrapper;

    @Autowired
    ARequest aRequest;

    @Autowired
    AUploadRequest aUploadRequest;

    @Autowired
    ObjectMapper objectMapper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (request.getContentType() != null && request.getContentType().contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> files = multipartRequest.getFiles("alphaFunctionFiles");
            Map<String, Object> parameterMap = multipartRequest.getParameterMap().entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue() != null && entry.getValue().length > 0 ? entry.getValue()[0] : null
                    ));
            String jsonString = objectMapper.writeValueAsString(parameterMap);
            aUploadRequest.setData(jsonString);
            aUploadRequest.setAlphaFunctionFiles(files);
            aUploadRequest.setHttpRequest(request);
            aUploadRequest.setHttpResponse(response);
            aUploadRequest.setPathParams((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            aUploadRequest.setQueryParams(request.getParameterMap());
            aUploadRequest.setRequestMethod(request.getMethod());
            aRequestWrapper.setAUploadRequest(aUploadRequest);
        } else {
            String requestBody = FileUtils.getRequestBody(request);
            aRequest.setHttpRequest(request);
            aRequest.setHttpResponse(response);
            aRequest.setData(requestBody);
            aRequest.setPathParams((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            aRequest.setQueryParams(request.getParameterMap());
            aRequest.setRequestMethod(request.getMethod());
            aRequestWrapper.setARequest(aRequest);
        }
        return true;
    }
}
