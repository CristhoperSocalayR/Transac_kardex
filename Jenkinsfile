pipeline {
    agent any

    tools {
        jdk 'jdk-17'
        maven 'maven-3.9.4' // <-- nombre que configuraste en Jenkins
    }

    stages {
        stage('Clonar repositorio') {
            steps {
                git branch: 'main', url: 'https://github.com/CristhoperSocalayR/Transac_kardex.git'
            }
        }

        stage('Compilar') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
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
