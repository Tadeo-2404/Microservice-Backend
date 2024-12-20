pipeline {
    agent any

    parameters {
        booleanParam(name: 'run_discovery_service', defaultValue: false, description: 'Set to true to run the discovery service')
        booleanParam(name: 'run_gateway_service', defaultValue: false, description: 'Set to true to run the gateway service')
        booleanParam(name: 'run_inventory_service', defaultValue: false, description: 'Set to true to run the inventory service')
        booleanParam(name: 'run_order_service', defaultValue: false, description: 'Set to true to run the order service')
        booleanParam(name: 'run_product_service', defaultValue: false, description: 'Set to true to run the product service')
        booleanParam(name: 'run_full_enviroment', defaultValue: false, description: 'Set to true to run the full enviroment')
        booleanParam(name: 'destroy_compose', defaultValue: false, description: 'Set to true to destroy the compose build')
    }

    tools {
        maven 'maven'
    }

    stages {
        stage('Clean Stage') {
            steps {
                sh "docker-compose down --volumes"
                sh "docker stop sonarqube || true"
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

        stage ('Set Up Stage') {
            steps {
                sh "docker start sonarqube"
            }
        }

        stage('Discovery Service Stage') {
            when { expression { params.run_discovery_service != false } } 
            steps {
                dir("discovery") {
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
                dir("gateway") {
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
                    sh "docker-compose up -d --no-color --wait"
                    sh "docker-compose ps"
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
                    sh "docker-compose up -d --no-color --wait"
                    sh "docker-compose ps"
                }
            }
        }

        stage('Order Service Stage') {
            when { expression { params.run_order_service != false } } 
            steps {
                dir("order-service") {
                    withSonarQubeEnv('SonarServer') {
                    sh "mvn clean package sonar:sonar \
                    -Dsonar.projectKey=order-service \
                    -Dsonar.projectName=order-service \
                    -Dsonar.sources=src/main"
                   }
                }

                dir("order-service") {
                    sh "docker-compose up -d --no-color --wait"
                    sh "docker-compose ps"
                }
            }
        }

        stage('Full Environment Stage') {
            when { expression { params.run_full_enviroment != false } } 
            steps {
                sh "docker-compose up -d --no-color --wait"
                sh "docker-compose ps"
            }
        }
    }

    post {
        always {
            script {
                if (params.destroy_compose && params.run_product_service) {
                    dir("product-service") {
                        sh "docker-compose down --volumes" 
                    }
                }

                if (params.destroy_compose && params.run_inventory_service) {
                    dir("inventory-service") {
                        sh "docker-compose down --volumes" 
                    }
                }

                if (params.destroy_compose && params.run_order_service) {
                    dir("order-service") {
                        sh "docker-compose down --volumes" 
                    }
                }

                if (params.destroy_compose && params.run_full_enviroment) {
                    sh "docker-compose down --volumes" 
                }
            }
        }
    }
}
