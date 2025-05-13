pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'jdk-17'
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
