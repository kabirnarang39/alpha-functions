package com.alpha.functions.utils.utilities.implementations;

import com.alpha.functions.entities.AlphaFunction;
import com.alpha.functions.utils.constants.ApplicationConstants;
import com.alpha.functions.utils.enums.DockerfileVariables;
import com.alpha.functions.utils.enums.FileSystemPaths;
import com.alpha.functions.utils.enums.Language;
import com.alpha.functions.utils.utilities.DockerfileConfigStrategy;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Map;

@Component
public class JavaDockerfileConfigStrategy implements DockerfileConfigStrategy {


    @Override
    public Map<String, String> createConfig(AlphaFunction alphaFunction, Path executablePath) {
        return Map.of(
                DockerfileVariables.BASE_IMAGE.toString(), Language.JAVA.baseImage + ":" + alphaFunction.getRuntime(),
                DockerfileVariables.APP_FILE.toString(), FileSystemPaths.ALPHA_FUNCTIONS_SDK_PATH.path,
                DockerfileVariables.ALPHA_FUNCTION_EXECUTABLE_PATH.toString(), executablePath.getFileName().toString(),
                DockerfileVariables.ENTRY_COMMAND.toString(), Language.JAVA.language,
                DockerfileVariables.ENTRY_COMMAND_OPTION.toString(), "-cp",
                DockerfileVariables.ENTRY_ARGS.toString(), String.join(":", FileSystemPaths.ALPHA_FUNCTIONS_SDK_PATH.path, FileSystemPaths.ALPHA_FUNCTION_IMAGE_EXECUTABLE_PATH.path),
                DockerfileVariables.MAIN_FUNCTION_PATH.toString(), ApplicationConstants.ALPHA_FUNCTION_MAIN_PATH,
                DockerfileVariables.EXECUTION_HANDLER.toString(), alphaFunction.getHandler()
        );
    }
}
