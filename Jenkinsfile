pipeline {
    agent any

    environment {
        SONARQUBE = 'SonarCloud'
        SONAR_TOKEN = '6ad549b1e284510156162c102325bb0ead18db5b'
    }

    tools {
        maven 'Maven 3.8.1'  // Asegúrate de que esté configurado en Jenkins
        jdk 'JDK 17'         // Asegúrate de que JDK 17 esté configurado en Jenkins
    }

    stages {
        stage('Clone Repository') {
            when {
                branch 'main' // Solo se ejecuta en la rama 'main'
            }
            steps {
                git 'https://github.com/CristhoperSocalayR/Transac_kardex.git'
            }
        }

        stage('Compile with Maven') {
            when {
                branch 'main' // Solo se ejecuta en la rama 'main'
            }
            steps {
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            when {
                branch 'main' // Solo se ejecuta en la rama 'main'
            }
            steps {
                script {
                    withSonarQubeEnv("${env.SONARQUBE}") {
                        sh "mvn sonar:sonar -Dsonar.projectKey=TransacKardex -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=${SONAR_TOKEN}"
                    }
                }
            }
        }

        stage('Run Unit Tests') {
            when {
                branch 'main' // Solo se ejecuta en la rama 'main'
            }
            steps {
                sh 'mvn test -Dtest=TestClass1,TestClass2,TestClass3'  // Reemplaza TestClass1, TestClass2, TestClass3 con los nombres de tus clases de prueba
            }
        }

        stage('Generate .jar Artifact') {
            when {
                branch 'main' // Solo se ejecuta en la rama 'main'
            }
            steps {
                sh 'mvn package'
            }
        }
    }

    post {
        success {
            echo 'Build completed successfully.'
        }
        failure {
            echo 'Build failed.'
        }
    }
}
