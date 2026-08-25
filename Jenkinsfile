pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Order Service - Build') {
            steps {
                dir('Order-Service') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
    }

    post {
        success {
            echo 'Order-Service build successful'
        }

        failure {
            echo 'Order-Service build failed'
        }
    }
}