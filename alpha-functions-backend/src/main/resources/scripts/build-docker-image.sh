#!/bin/bash

# Function to print usage instructions
usage() {
    echo "Usage: $0 -n <image_name> -d <dockerfile_directory> -t <tag> [-p <docker_context>] [-f <dockerfile_name>]"
    echo "  -n    Name for the Docker image (required)"
    echo "  -d    Directory containing the Dockerfile (default: current directory)"
    echo "  -t    Tag for the Docker image (default: latest)"
    echo "  -p    Directory for the Docker build context (default: -d)"
    echo "  -f    Dockerfile name (default: Dockerfile)"
    echo "  -h    Show this help message"
    exit 1
}

# Default values
DOCKERFILE_DIR="."
DOCKERFILE_NAME="Dockerfile"
IMAGE_NAME=""
TAG="latest"
DOCKER_CONTEXT="."

# Parse input arguments
while getopts "n:d:t:p:f:h" opt; do
    case $opt in
        n) IMAGE_NAME="$OPTARG" ;;
        d) DOCKERFILE_DIR="$OPTARG" ;;
        t) TAG="$OPTARG" ;;
        p) DOCKER_CONTEXT="$OPTARG" ;;
        f) DOCKERFILE_NAME="$OPTARG" ;;
        h) usage ;;
        *) usage ;;
    esac
done

# Check if image name is provided
if [ -z "$IMAGE_NAME" ]; then
    echo "Error: Image name is required."
    usage
fi

# Validate Dockerfile directory
if [ ! -d "$DOCKERFILE_DIR" ]; then
    echo "Error: Dockerfile directory '$DOCKERFILE_DIR' does not exist."
    exit 1
fi

# Check if the specified Dockerfile exists in the directory
if [ ! -f "$DOCKERFILE_DIR/$DOCKERFILE_NAME" ]; then
    echo "Error: Dockerfile '$DOCKERFILE_NAME' not found in directory '$DOCKERFILE_DIR'."
    exit 1
fi

# If Docker context is not specified, use the Dockerfile directory as context
if [ -z "$DOCKER_CONTEXT" ]; then
    DOCKER_CONTEXT="$DOCKERFILE_DIR"
fi

# Validate Docker context directory
if [ ! -d "$DOCKER_CONTEXT" ]; then
    echo "Error: Docker context directory '$DOCKER_CONTEXT' does not exist."
    exit 1
fi

# Build Docker image
echo "Building Docker image..."
echo "Dockerfile directory: $DOCKERFILE_DIR"
echo "Dockerfile name: $DOCKERFILE_NAME"
echo "Docker image name: $IMAGE_NAME"
echo "Tag: $TAG"
echo "Build context: $DOCKER_CONTEXT"

# Command to build the image with the custom Dockerfile name
docker build -f "$DOCKERFILE_DIR/$DOCKERFILE_NAME" -t "$IMAGE_NAME:$TAG" "$DOCKER_CONTEXT"

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "Docker image '$IMAGE_NAME:$TAG' built successfully!"
else
    echo "Error: Failed to build the Docker image."
    exit 1
fi
