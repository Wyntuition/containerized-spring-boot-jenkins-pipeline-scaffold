# Jenkins Declarative Scaffold, with Dockerized Sprint Boot Sample App, Using Multi-Stage Builds for Building

This repo containers 4 examples, working together but usable separately: 

* *Jenkins pipeline with Docker Pipeline plugin* With the introduction of the Pipeline plugin, users now can implement a project’s entire build/test/deploy pipeline in a Jenkinsfile and store that alongside their code, treating their pipeline as another piece of code checked into source control.

* *Jenkins in a container, using host Docker* to have Jenkins in a container build containers, the container only runs the CLI and connects back to the host to start sister containers (not Docker-in-Docker)

* *Spring Boot scaffold, containerized*

* *Multi-stage build of Spring Boot pattern*

## Set up your app to be run in a pipeline

Jenkins can be added to any projects with a `Jenkinsfile`. This scaffold provides a basic one that does the key steps: build the app in the container, test, and if successful, push the image.

1. *Build Step* The Jenkinsfile will build your app per your Dockerfile, or you can tweak it (i.e. using docker-compose).

1. *Testing Step* The Jenkins file has a test section in order for you to trigger your test suite.

1. *Push Image* If the tests pass, the next step will run which is typically to push the image. Update this with your repo and credentials (which can stored and be pulled from Jenkins credentials, etc).

## Run Jenkins locally in a container to run a pipeline

1. *Run Jenkins locally in via the specified image* It will use your host's Docker Engine so Jenkins can run Docker but does not need its own engine in the Jenkins container.

```bash
docker run -d -v /var/run/docker.sock:/var/run/docker.sock -v $(pwd):/var/jenkins_home -p 8080:8080 -p 50000:50000 jareddlc/jenkins-with-docker-socket
```

1. *Log into Jenkins* The console output will provide an access key if its the first time you're starting a container with Jenkins. Use that key when logging in for the first time.

Browse to Jenkins, running on `https://localhost:8080`.

1. *Set up Jenkins with your job* To tell Jenkins about your job, create a pipeline job, and point to your repo and Jenkinsfile.





## Running the Spring Boot app

Starter for building and running a Spring Boot app via Docker with multi-stage build.

1. Build & Run the app using a multi-stage build:

    To build the app with Docker Compose, run:

    ```bash
    docker-compose up
    ```

    To build the app using an official Gradle container, run:

    ```bash
    docker run --rm registry.hub.docker.com/library/gradle:4.2-jdk8-alpine gradle build && java -jar build/libs/hello-spring-boot-0.0.1.jar
    ```

1. Test with: `curl localhost:8080`

## MISC NOTES

You can have Jenkins pull from a local repo if you pull it into a subdirectory off the Jenkins home directory, such as, `file://$JENKINS_HOME/apps/vis-scaffold-pipeline-declarative-springboot`

You can build & run the Spring Boot app with the Gradle image directly, with this command, 

```bash
docker run --rm -v $(pwd):/home/gradle/project -w /home/gradle/project registry.hub.docker.com/library/gradle:4.2-jdk8-alpine gradle build && java -jar build/libs/hello-spring-boot-0.0.1.jar
```



