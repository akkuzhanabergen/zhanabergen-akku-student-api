pipeline {
    agent { label 'docker-gradle-jdk17' }

    options {
        timestamps()
        skipDefaultCheckout(true)
    }

    triggers {
        pollSCM('H/2 * * * *')
    }

    environment {
        IMAGE_NAME = 'zhanabergen-akku-student-api'
        IMAGE_TAG = 'v1.0-zhanabergen-akku'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo 'Student: Zhanabergen Akku'
            }
        }
        stage('Build') {
            steps {
                sh 'chmod +x ./gradlew && ./gradlew --no-daemon clean compileJava'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew --no-daemon test'
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t "$IMAGE_NAME:$IMAGE_TAG" .'
            }
        }
        stage('Verify') {
            steps {
                sh 'docker image inspect "$IMAGE_NAME:$IMAGE_TAG" --format "Image: {{.RepoTags}} Size: {{.Size}} bytes"'
                echo 'Docker image is available for Zhanabergen Akku.'
            }
        }
    }

    post {
        success { echo 'Student API pipeline succeeded for Zhanabergen Akku.' }
        failure { echo 'Pipeline failed: inspect the Build, Test or Docker Build stage logs.' }
    }
}
