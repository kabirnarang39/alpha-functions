package com.alpha.functions.handlers.implementations;

import com.alpha.functions.dto.AFunction;
import com.alpha.functions.dto.ARequest;
import com.alpha.functions.dto.ARequestWrapper;
import com.alpha.functions.dto.AUploadRequest;
import com.alpha.functions.entities.AlphaFunction;
import com.alpha.functions.entities.FileUtility;
import com.alpha.functions.handlers.BaseHandler;
import com.alpha.functions.repositories.AlphaFunctionRepository;
import com.alpha.functions.utils.AException;
import com.alpha.functions.utils.utilities.FileUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Component
@Data
public class OperationsHandler extends BaseHandler {

    private final FileUtils fileUtils;
    private final AlphaFunctionRepository alphaFunctionRepository;
    private final ARequestWrapper aRequestWrapper;
    private final ARequest aRequest;
    private final AUploadRequest aUploadRequest;
    private final AFunctionExecutionLifecycleHandler aFunctionExecutionLifecycleHandler;

    @Override
    public Object create(AFunction aFunction) {
        AlphaFunction alphaFunction = null;
        try {
            List<FileUtility> fileUtilities = new ArrayList<>();
            if (aUploadRequest.getAlphaFunctionFiles() != null) {
                for (MultipartFile file : aUploadRequest.getAlphaFunctionFiles()) {
                    String fileName = file.getOriginalFilename();
                    String fileExtension = null;
                    if (fileName != null && fileName.contains(".")) {
                        fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
                    }
                    String physicalFileName = UUID.randomUUID() + "." + fileExtension;
                    Path filePath = fileUtils.uploadFile(file, physicalFileName);
                    fileUtilities.add(FileUtility.builder()
                            .id(null)
                            .actualFileName(fileName)
                            .physicalFileName(physicalFileName)
                            .fileExtension(fileExtension)
                            .filePath(filePath.toString())
                            .isLatestVersion(Boolean.TRUE)
                            .createdDateTime(LocalDateTime.now())
                            .version(1L)
                            .build());
                }
            }
            if (alphaFunctionRepository != null) {
                alphaFunction = alphaFunctionRepository.save(AlphaFunction.builder()
                        .id(null)
                        .alphaName(aFunction.getAlphaFunctionName())
                        .handler(aFunction.getAlphaFunctionHandler())
                        .createdDateTime(LocalDateTime.now())
                        .description(aFunction.getAlphaFunctionDescription())
                        .isLatestVersion(Boolean.TRUE)
                        .runtime(aFunction.getAlphaFunctionRuntime())
                        .version(1L)
                        .fileUtilities(fileUtilities)
                        .envVariables(aFunction.getEnvVariables())
                        .language(aFunction.getAlphaFunctionLanguage())
                        .maximumCpu(aFunction.getMaximumCpu())
                        .maximumMemory(aFunction.getMaximumMemory())
                        .parallelReplicas(aFunction.getParallelReplicas())
                        .minimumCpu(aFunction.getMinimumCpu())
                        .minimumMemory(aFunction.getMinimumMemory())
                        .repositoryUserName(aFunction.getRepositoryUserName())
                        .timeout(aFunction.getAlphaFunctionTimeout())
                        .parallelExecutionEnabled(aFunction.isParallelExecutionEnabled())
                        .isExecuted(false)
                        .maxRetries(aFunction.getMaxRetries())
                        .build());
            }
        } catch (Exception e) {
            throw new AException(e.getMessage());
        }
        return alphaFunction;
    }

    @Override
    public Object read(AFunction aFunction) {
        if (aFunction.getAlphaFunctionId() != null && aFunction.getVersion() != null) {
            return alphaFunctionRepository.findByIdAndVersion(aFunction.getAlphaFunctionId(), aFunction.getVersion());
        } else if (aFunction.getAlphaFunctionId() != null) {
            return alphaFunctionRepository.findByIdAndIsLatestVersion(aFunction.getAlphaFunctionId(), true);
        } else {
            return alphaFunctionRepository.findAllByIsLatestVersion(true);
        }
    }

    @Override
    public Object update(AFunction aFunction) {
        return null;
    }

    @Override
    public Object delete(AFunction aFunction) {
        return null;
    }

    @Override
    public Object execute(AFunction aFunction) {
        return aFunctionExecutionLifecycleHandler.execute(aFunction);
    }
}
