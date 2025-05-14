pipeline {
    agent any

    environment {
        SONARQUBE = 'SonarCloud'  // Nombre de tu servidor SonarQube configurado en Jenkins
        SONAR_TOKEN = credentials('SONAR_TOKEN')  // Se obtiene el token como credencial
    }

    tools {
        maven 'Maven 3.8.1'  // Asegúrate de que Maven esté configurado en Jenkins
        jdk 'JDK 17'         // Asegúrate de que JDK 17 esté configurado en Jenkins
    }

    stages {
        stage('Clone Repository') {
            steps {
                // Clonar el repositorio y cambiar a la rama 'main'
                git branch: 'main', url: 'https://github.com/CristhoperSocalayR/Transac_kardex.git'
            }
        }

        stage('Compile with Maven') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh '''
                    mvn test \
                        -Dtest=ProductServiceTest,SupplierServiceTest,TypeSupplierServiceTest
                '''
            }
            post {
                always {
                    // Usar el paso junit para publicar resultados de pruebas
                    junit testResults: 'target/surefire-reports/*.xml'
                    
                    // Opcional: Generar reporte de cobertura
                    jacoco()
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv("${env.SONARQUBE}") {
                        sh '''
                            mvn sonar:sonar \
                                -Dsonar.projectKey=cristhopersocalayr_Transac_kardex \
                                -Dsonar.organization=cristhopersocalayr \
                                -Dsonar.host.url=https://sonarcloud.io \
                                -Dsonar.login=${SONAR_TOKEN}
                        '''
                    }
                }
            }
        }

        stage('Wait for SonarQube Quality Gate') {
            steps {
                script {
                    def qg = waitForQualityGate()
                    if (qg.status != 'OK') {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                    }
                }
            }
        }

        stage('Generate .jar Artifact') {
            steps {
                sh 'mvn package -DskipTests'
            }
            post {
                always {
                    // Archivar el JAR generado
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
    }

    post {
        always {
            // Limpiar workspace después de cada build
            cleanWs()
        }
        success {
            echo 'Build completed successfully!'
            echo 'SonarQube analysis passed!'
        }
        failure {
            echo 'Build failed!'
        }
        unstable {
            echo 'Build is unstable!'
        }
    }
}