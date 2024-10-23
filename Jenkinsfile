pipeline {
    agent any

    parameters {
        booleanParam(name: 'run_discovery_service', defaultValue: false, description: 'Set to true to run the discovery service')
        booleanParam(name: 'run_gateway_service', defaultValue: false, description: 'Set to true to run the gateway service')
        booleanParam(name: 'run_inventory_service', defaultValue: false, description: 'Set to true to run the inventory service')
        booleanParam(name: 'run_product_service', defaultValue: false, description: 'Set to true to run the product service')
    }

    tools {
        maven 'maven'
    }

    stages {
        stage('Clean Stage') {
            steps {
                sh "docker stop api-gateway || true"
                sh "docker rm api-gateway || true"
                sh "docker stop discovery || true"
                sh "docker rm discovery || true"
                sh "docker stop inventory-service || true"
                sh "docker rm inventory-service || true"
                sh "docker rmi inventory-service || true"
                sh "docker stop product-service || true"
                sh "docker rm product-service || true"
                sh "docker rmi product-service || true"
            }
        }

        stage('Discovery Service Stage') {
            when { expression { params.run_discovery_service != false } } 
            steps {
                dir("discovery-service") {
                    withSonarQubeEnv('SonarServer') {
                    sh "mvn clean package sonar:sonar \
                    -Dsonar.projectKey=discovery-service \
                    -Dsonar.projectName=discovery-service \
                    -Dsonar.sources=src/main"
                   }
                }

                dir("discovery") {
                    sh "docker build -t discovery ."
                }
            }
        }

        stage('Gateway Service Stage') {
            when { expression { params.run_gateway_service != false } } 
            steps {
                dir("gateway-service") {
                    withSonarQubeEnv('SonarServer') {
                    sh "mvn clean package sonar:sonar \
                    -Dsonar.projectKey=gateway-service \
                    -Dsonar.projectName=gateway-service \
                    -Dsonar.sources=src/main"
                   }
                }

                dir("gateway") {
                    sh "docker build -t gateway ."
                }
            }
        }

        stage('Inventory Service Stage') {
            when { expression { params.run_inventory_service != false } } 
            steps {
                dir("inventory-service") {
                    withSonarQubeEnv('SonarServer') {
                    sh "mvn clean package sonar:sonar \
                    -Dsonar.projectKey=inventory-service \
                    -Dsonar.projectName=inventory-service \
                    -Dsonar.sources=src/main"
                   }
                }

                dir("inventory-service") {
                    sh "docker-compose up -d"
                }
            }
        }

        stage('Product Service Stage') {
            when { expression { params.run_product_service != false } } 
            steps {
                dir("product-service") {
                    withSonarQubeEnv('SonarServer') {
                    sh "mvn clean package sonar:sonar \
                    -Dsonar.projectKey=product-service \
                    -Dsonar.projectName=product-service \
                    -Dsonar.sources=src/main"
                   }
                }

                dir("product-service") {
                    sh "docker-compose up -d"
                }
            }
        }
    }

    post {
        always {
            dir("product-service") {
                sh "docker-compose down --volumnes"
            }

            dir("inventory-service") {
                sh "docker-compose down --volumes"
            }

            sh "docker stop api-gateway || true"
            sh "docker rm api-gateway || true"
            sh "docker stop discovery || true"
            sh "docker rm discovery || true"
        }
    }
}
