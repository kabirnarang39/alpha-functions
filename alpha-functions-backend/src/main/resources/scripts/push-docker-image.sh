#!/bin/bash

# ------------------------------------------------------
# Default Values
# ------------------------------------------------------

DEFAULT_TAG="latest"  # Default image tag if not provided

# ------------------------------------------------------
# Parse Command-Line Arguments using getopts
# ------------------------------------------------------

# Function to display usage
usage() {
  echo "Usage: $0 -u <docker-hub-username> -i <image-name> [-t <image-tag>]"
  echo "  -u  Docker Hub username (required)"
  echo "  -i  Docker image name (required)"
  echo "  -t  Docker image tag (optional, default: latest)"
  exit 1
}

# Parse options
while getopts ":u:i:t:" opt; do
  case ${opt} in
    u) DOCKER_HUB_USERNAME=$OPTARG ;;  # Docker Hub username
    i) IMAGE_NAME=$OPTARG ;;           # Docker image name
    t) IMAGE_TAG=$OPTARG ;;            # Docker image tag (optional)
    \?) usage ;;                       # Invalid option
  esac
done

# Check if mandatory options are set
if [ -z "$DOCKER_HUB_USERNAME" ] || [ -z "$IMAGE_NAME" ]; then
  echo "Error: Docker Hub username and image name are required."
  usage
fi

# Set the default image tag if not specified
IMAGE_TAG=${IMAGE_TAG:-$DEFAULT_TAG}  # Default to "latest" if no tag is provided

# Full repository path for Docker Hub (format: "myuser/myapp")
DOCKER_HUB_REPOSITORY="$DOCKER_HUB_USERNAME/$IMAGE_NAME"

# ------------------------------------------------------
# Helper Functions
# ------------------------------------------------------

# Function to log messages in a standard format
log() {
  echo "[INFO] $(date '+%Y-%m-%d %H:%M:%S') - $1"
}

# Function to tag Docker image for Docker Hub
tag_image() {
  log "Tagging Docker image as $DOCKER_HUB_REPOSITORY:$IMAGE_TAG"
  docker tag "$IMAGE_NAME:$IMAGE_TAG" "$DOCKER_HUB_REPOSITORY:$IMAGE_TAG"
}

# Function to push the Docker image to Docker Hub
push_to_dockerhub() {
  log "Pushing image to Docker Hub..."
  docker push "$DOCKER_HUB_REPOSITORY:$IMAGE_TAG"
}

# ------------------------------------------------------
# Main Script Logic
# ------------------------------------------------------

log "Starting Docker image push script..."

# Step 1: Tag the image
tag_image

# Step 2: Push Docker image to Docker Hub
push_to_dockerhub

log "Docker image push completed successfully."
