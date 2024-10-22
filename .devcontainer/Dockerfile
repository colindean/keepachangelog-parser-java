FROM eclipse-temurin:17-jdk

## set current user to `root`
USER root

# Install additional dependencies
RUN apt-get update --yes && \
    DEBIAN_FRONTEND=noninteractive apt-get install --yes --no-install-recommends \
      git && \
    apt-get clean

## define build arguments
ARG USERNAME=vscode
ARG VSC_SERVER_PATH="/home/${USERNAME}/.vscode-server"
ARG USER_UID=1001
ARG USER_GID=$USER_UID

# Create the user
RUN groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USERNAME \
    #
    # [Optional] Add sudo support. Omit if you don't need to install software after connecting.
    && apt-get update \
    && apt-get install -y sudo \
    && echo $USERNAME ALL=\(root\) NOPASSWD:ALL > /etc/sudoers.d/$USERNAME \
    && chmod 0440 /etc/sudoers.d/$USERNAME

## ################################################################ ##
## Directive 1: define a Docker VOLUME for `~/.vscode-server`
VOLUME [ "${VSC_SERVER_PATH}" ]

## Directive 2: create directory and set ownership to non-root user
RUN set -eux; \
  mkdir -p "${VSC_SERVER_PATH}" && \
  chown ${USERNAME}:${USERNAME} "${VSC_SERVER_PATH}"
## ################################################################ ##

## set current user to non-root user
USER "${USERNAME}"

WORKDIR /workspace
