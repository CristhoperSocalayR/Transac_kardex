pipeline {
    agent any

    environment {
        JAVA_HOME = tool(name: 'jdk17') // Usa el nombre correcto configurado en Jenkins
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/CristhoperSocalayR/Transac_kardex.git'
            }
        }

        stage('Compilar') {
            steps {
                sh './mvnw clean compile' // O usa 'mvn' si no estás usando mvnw
            }
        }

        stage('Pruebas unitarias') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Generar artefacto') {
            steps {
                sh './mvnw package'
            }
        }
    }

    post {
        failure {
            echo 'La compilación ha fallado.'
        }
        success {
            echo 'La compilación fue exitosa.'
        }
    }
}
