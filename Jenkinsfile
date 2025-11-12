pipeline {
    agent any

    environment {
        DOCKER_HUB_REPO = 'skynex'     // change this if you push to DockerHub later
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'github-token', url: 'https://github.com/rajeshkr4293/SkyNex-Airlines.git'
            }
        }

        stage('Build & Package Microservices') {
            steps {
                script {
                    echo "Building all SkyNex microservices..."
                    def services = ['flight-service', 'booking-service', 'payment-service', 'user-service', 'config-server', 'service-registry', 'api-gateway', 'admin-server']
                    for (service in services) {
                        dir("${service}/${service}") {
                            sh 'chmod +x mvnw'
                            sh './mvnw clean package -DskipTests'
                        }
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    def services = ['flight-service', 'booking-service', 'payment-service', 'user-service', 'config-server', 'service-registry', 'api-gateway', 'admin-server']
                    for (service in services) {
                        dir("${service}/${service}") {
                            sh "docker build -t ${DOCKER_HUB_REPO}-${service}:latest -f Dockerfile ."
                        }
                    }
                }
            }
        }

        stage('Post-Build Summary') {
            steps {
                echo '✅ All microservices built successfully!'
            }
        }
    }

    post {
        failure {
            echo '❌ Build failed. Please check logs in Jenkins.'
        }
    }
}
