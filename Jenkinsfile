pipeline {
    agent any

    tools {
        maven 'Maven3'  // Asegúrate de que Maven esté instalado y configurado en Jenkins
        jdk tool: 'jdk-17'  // Asegúrate de que el JDK 17 esté configurado correctamente en Jenkins
    }

    environment {
        SONARQUBE_ENV = 'MySonar'
    }

    stages {
        stage('Clonar repositorio') {
            steps {
                git 'https://github.com/CristhoperSocalayR/Transac_kardex.git'
            }
        }

        stage('Compilar') {
            steps {
                script {
                    // Verifica que Maven y JDK están disponibles
                    sh 'java -version'
                    sh 'mvn -version'
                }
                sh 'mvn clean compile'
            }
        }

        stage('Pruebas unitarias') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Analizar con SonarQube') {
            steps {
                withSonarQubeEnv("${SONARQUBE_ENV}") {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=TransacKardex -Dsonar.host.url=http://sonarqube:9000'
                }
            }
        }

        stage('Generar artefacto') {
            steps {
                sh 'mvn package'
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
    }
}
