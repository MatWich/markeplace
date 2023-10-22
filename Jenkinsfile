pipeline {
    agent any
    environment {
        DOCKERHUB_CREDENTIALS = credentials('agroniks-dockerhub')
    }

    stages {
        stage("Build") {
            steps {
                echo "LOL BUILD"
                sh 'docker build -t agroniks/markeplace:$BUILD_NUMBER .'
            }
        }
        stage("Login to docker hub") {
           steps {
                echo "Logging to dockerhub"
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $$DOCKERHUB_CREDENTIALS_USR --password-stdin'
           }
        }
       stage("Pushing image") {
           steps {
                sh 'docker push agroniks/markeplace:$BUILD_NUMBER'
           }
       }
    }
post {
    always {
        sh 'docker logout'
    }
}

