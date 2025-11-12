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
                // Flight service path
                dir('flight-service/flight-service') {
                    bat 'mvn clean package -DskipTests'
                }
                // Booking service path corrected
                dir('booking-service') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                echo 'Building Docker images...'
                // Flight service Docker image
                dir('flight-service/flight-service') {
                    bat 'docker build -t skynex-flight-service .'
                }
                // Booking service Docker image path corrected
                dir('booking-service') {
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
