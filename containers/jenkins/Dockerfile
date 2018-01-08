FROM jenkins/jenkins:alpine

USER root
RUN   apk update \
      && apk add sudo \
      && apk add curl \
      && apk add nodejs \
      && apk add ruby \
      && apk add go \
      && rm -rf /var/lib/apt/lists/*

RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers

# Getting the docker-cli
# NOTE: docker.sock needs to be mounted as volume in docker-compose.yml
RUN curl -sSL -O https://get.docker.com/builds/Linux/x86_64/docker-latest.tgz && tar -xvzf docker-latest.tgz
RUN mv docker/* /usr/bin/

RUN set -ex \
      && apk update \
      && apk add --no-cache \
        jq \
        python \
        py-pip \
        ruby ruby-bigdecimal ruby-bundler \
        ca-certificates libressl \
        libressl-dev build-base ruby-dev \
        postgresql-client && rm -rf /var/cache/apk/* \
      && pip install docker-compose==1.17.1 \
      && gem install --no-ri --no-rdoc \
        json \
        keystore

# Put jenkins into the root group so it can run Docker. Ok since this is run locally, but other ways like giving the jenkins group permission could be explored.
# TODO: disabling as didn't have perms to mount docker.sock
#USER jenkins:root

############################################
# Configure Jenkins
############################################

# Jenkins settings
#COPY config/config.xml /usr/share/jenkins/ref/config.xml

# installing specific list of plugins. see: https://github.com/jenkinsci/docker#preinstalling-plugins
COPY jenkins_home/plugins.txt /var/jenkins_home/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /var/jenkins_home/plugins.txt

# Adding jobs
COPY jenkins_home/jobs/ /usr/share/jenkins/ref/jobs

#copy over the job script this works better if it is in git
#COPY seedjobs/dockerTestJob.groovy /var/jenkins_home/seedjobs/dockerTestJob.groovy

# SSH Keys & Credentials
#COPY config/credentials.xml /usr/share/jenkins/ref/credentials.xml
#COPY config/ssh-keys/cd-demo /usr/share/jenkins/ref/.ssh/id_rsa
#COPY config/ssh-keys/cd-demo.pub /usr/share/jenkins/ref/.ssh/id_rsa.pub

# tell Jenkins that no banner prompt for pipeline plugins is needed
# see: https://github.com/jenkinsci/docker#preinstalling-plugins
RUN echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state