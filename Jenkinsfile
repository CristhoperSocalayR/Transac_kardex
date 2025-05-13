pipeline {
    agent any

    tools {
        jdk 'jdk-17' // Esto debe coincidir con el nombre configurado en Jenkins
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

        stage('Generar artefacto') {
            steps {
                sh 'mvn package'
            }
        }
    }
}
