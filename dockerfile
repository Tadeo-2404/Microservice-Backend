# Use the official Jenkins LTS image based on Debian
FROM jenkins/jenkins:lts-jdk11

# Switch to the root user to install additional packages
USER root

# Update Debian and install necessary utilities
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    vim \
    git \
    ca-certificates \
    apt-transport-https && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Switch back to the Jenkins user
USER jenkins
