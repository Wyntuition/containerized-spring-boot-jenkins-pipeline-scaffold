#!/usr/bin/env groovy

pipeline {
  // NOTE: not necessary now b/c the parent multistage job polls SCM ~every min, but could be reduced and frequent polling could be controlled in branches here like this
  // triggers {
  //   pollSCM('0 0 * * 0')
  // }
  agent any
  environment {
      MIGRATE_ENV = 'nonprod-pipeline'
      BRANCH_NAME = "${env.BRANCH_NAME}"
  }
  stages {
    stage('Setup') {
      steps {
        sh 'sudo apt-get install jq'
      }
    }
    stage('Build') {
      steps {
          sh './bin/migrate'
      }
    }
    stage('Run integration tests') {
      steps {
        sh '''
          source "env/${MIGRATE_ENV}.sh"
          
          docker-compose run \
            -e ENV=${MIGRATE_ENV} \
            -e PG_HOSTNAME=${PG_HOSTNAME} \
            -e PG_PORT=${PG_PORT} \
            -e PG_DBNAME=${PG_DBNAME} \
            -e PG_USERNAME=${PG_USERNAME} \
            -e PG_PASSWORD=${PG_PASSWORD} \
            int_tests
        '''
      }
    }
  }
  post 
  {
    always {
      script {
        if (env.BRANCH_NAME != 'master') {
          sh '''
            docker-compose down
          '''
        }
      }
    }
  }
}
