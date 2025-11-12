pipeline {
    agent any

    environment {
        DOCKER_HUB_REPO = 'skynex-airlines'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'dev',
                    url: 'https://github.com/rajeshkr4293/SkyNex-Airlines.git',
                    credentialsId: 'github-token'
            }
        }

        stage('Build & Package Microservices') {
            steps {
                echo 'Building all SkyNex microservices...'
                dir('flight-service/flight-service') {
                    bat 'mvn clean package -DskipTests'
                }
                dir('booking-service/booking-service') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                echo 'Building Docker images...'
                dir('flight-service/flight-service') {
                    bat 'docker build -t skynex-flight-service .'
                }
                dir('booking-service/booking-service') {
                    bat 'docker build -t skynex-booking-service .'
                }
            }
        }

        stage('Post-Build Summary') {
            steps {
                echo '✅ Build and Docker image creation completed successfully!'
            }
        }
    }

    post {
        failure {
            echo '❌ Build failed. Please check logs in Jenkins.'
        }
    }
}
