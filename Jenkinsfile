pipeline {
    agent any

    stages {

        stage('Order Service - Build') {
            steps {
                dir('Order-Service') {
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('Product Service - Build') {
            steps {
                dir('product-service') {
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('User Service - Build') {
            steps {
                dir('user-service') {
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('API Gateway - Build') {
            steps {
                dir('api-Gateway') {
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('Discovery Service - Build') {
            steps {
                dir('descovery-service') {
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('Config Server - Build') {
            steps {
                dir('config-server') {
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('Notification Service - Build') {
            steps {
                dir('notification') {
                    sh './mvnw clean package -DskipTests'
                }
            }
        }
    }
}