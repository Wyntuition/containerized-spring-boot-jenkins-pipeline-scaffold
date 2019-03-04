docker run \
  --rm \
  -u root \
  -p 10001:8080 \
  -v jenkins-data:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v "$HOME":/home \
  -name jenkins-local
  jenkinsci/blueocean

# Access Jenkins bash
docker exec -it jenkins-tutorials bash

