package com.alpha.functions.utils.enums;

public enum FileSystemPaths {
    DOCKER_IMAGE_BUILD_SCRIPT_PATH("src/main/resources/scripts/build-docker-image.sh"),
    DOCKER_IMAGE_PUSH_SCRIPT_PATH("src/main/resources/scripts/push-docker-image.sh"),
    DOCKERFILE_TEMPLATE_PATH("src/main/resources/deployment-templates/Dockerfile"),
    ALPHA_FUNCTIONS_SDK_PATH("./alpha-functions-sdk.jar"),
    ALPHA_FUNCTION_IMAGE_EXECUTABLE_PATH("./app.jar");
    public final String path;

    FileSystemPaths(String path) {
        this.path = path;
    }
}
