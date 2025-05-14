pipeline {
    agent any
    
    environment {
        SONARQUBE = 'SonarCloud'  // Nombre del servidor SonarQube configurado en Jenkins
        SONAR_TOKEN = credentials('SONAR_TOKEN')  // Token de SonarCloud como credencial
        PROJECT_KEY = 'CristhoperSocalay_SupplierNPH'  // Nombre corregido del proyecto en SonarCloud
        SONAR_ORGANIZATION = 'cristhopersocalayr'  // Organización en SonarCloud
    }
    
    tools {
        maven 'Maven 3.8.1'  // Maven configurado en Jenkins
        jdk 'JDK 17'  // JDK 17 configurado en Jenkins
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
                    // Publicar resultados de pruebas
                    junit testResults: 'target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv("${env.SONARQUBE}") {
                        sh '''
                            mvn sonar:sonar \
                                -Dsonar.projectKey=${PROJECT_KEY} \
                                -Dsonar.organization=${SONAR_ORGANIZATION} \
                                -Dsonar.host.url=https://sonarcloud.io \
                                -Dsonar.login=${SONAR_TOKEN}
                        '''
                    }
                    
                    // Guardar el taskId para la Quality Gate
                    def ceTaskId = sh(returnStdout: true, script: 'cat target/sonar/report-task.txt | grep ceTaskId | cut -d= -f2').trim()
                    env.SONAR_CE_TASK_ID = ceTaskId
                }
            }
        }
        
        stage('Wait for SonarQube Quality Gate') {
            steps {
                script {
                    // Añadir timeout para evitar que el pipeline se quede colgado indefinidamente
                    timeout(time: 10, unit: 'MINUTES') {
                        // Esperar un momento para que SonarQube procese
                        sleep(10)
                        
                        // Usar el ID de tarea guardado
                        def qg = waitForQualityGate(abortPipeline: true, taskId: env.SONAR_CE_TASK_ID)
                        if (qg.status != 'OK') {
                            error "Pipeline abortado debido a fallo en la Quality Gate: ${qg.status}"
                        }
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
            echo 'Build completado exitosamente!'
            echo 'Análisis de SonarQube pasado!'
        }
        failure {
            echo 'Build fallido!'
        }
        unstable {
            echo 'Build inestable!'
        }
    }
}