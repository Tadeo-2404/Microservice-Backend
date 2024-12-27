pipeline {
    agent any

    parameters {
        booleanParam(name: 'skip_sonar', defaultValue: false, description: 'Set to true to run sonar analysis')
        booleanParam(name: 'run_inventory_service', defaultValue: false, description: 'Set to true to run the inventory service')
        booleanParam(name: 'run_order_service', defaultValue: false, description: 'Set to true to run the order service')
        booleanParam(name: 'run_product_service', defaultValue: false, description: 'Set to true to run the product service')
    }

    tools {
        maven 'maven'
    }

    stages {
        stage('Clean Stage') {
            steps {
                sh "docker stop sonarqube || true"
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
                script {
                   if (!params.skip_sonar) {
                      sh "docker start sonarqube"
                   }
                }
            }
        }

        stage('Gateway Service Stage') {
            when { expression { params.run_gateway_service != false } } 
            steps {
                dir("gateway") {
                    script {
                        if (!params.skip_sonar) {
                            withSonarQubeEnv('SonarServer') {
                                sh "mvn clean package sonar:sonar \
                                -Dsonar.projectKey=gateway-service \
                                -Dsonar.projectName=gateway-service \
                                -Dsonar.sources=src/main"
                            }
                        }
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
                    script {
                        if (!params.skip_sonar) {
                            withSonarQubeEnv('SonarServer') {
                                sh "mvn clean package sonar:sonar \
                                -Dsonar.projectKey=inventory-service \
                                -Dsonar.projectName=inventory-service \
                                -Dsonar.sources=src/main"
                            }
                        }
                    }

                    sh "docker-compose up -d --no-color --wait"
                    sh "docker-compose ps"
                }
            }
        }

        stage('Product Service Stage') {
            when { expression { params.run_product_service != false } } 
            steps {
                dir("product-service") {
                    script {
                        if (!params.skip_sonar) {
                            withSonarQubeEnv('SonarServer') {
                                sh "mvn clean package sonar:sonar \
                                -Dsonar.projectKey=product-service \
                                -Dsonar.projectName=product-service \
                                -Dsonar.sources=src/main"
                            }
                        }
                    }
                }

                dir('product-service') {
                    sh "docker build -t product-service ."
                }
            }
        }

        stage('Order Service Stage') {
            when { expression { params.run_order_service != false } } 
            steps {
                dir("order-service") {
                    script {
                        if (!params.skip_sonar) {
                            withSonarQubeEnv('SonarServer') {
                                sh "mvn clean package sonar:sonar \
                                -Dsonar.projectKey=order-service \
                                -Dsonar.projectName=order-service \
                                -Dsonar.sources=src/main"
                            }
                        }
                    }
                }

                dir('order-service') {
                    sh "docker build -t order-service ."
                }
            }
        }
    }
}
