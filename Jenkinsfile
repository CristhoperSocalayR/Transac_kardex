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
            steps {
                git 'https://github.com/CristhoperSocalayR/Transac_kardex.git'
            }
        }

        stage('Compile with Maven') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv("${env.SONARQUBE}") {
                        sh "mvn sonar:sonar -Dsonar.projectKey=TransacKardex -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=${SONAR_TOKEN}"
                    }
                }
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh 'mvn test -Dtest=TestClass1,TestClass2,TestClass3'  // Reemplaza TestClass1, TestClass2, TestClass3 con los nombres de tus clases de prueba
            }
        }

        stage('Generate .jar Artifact') {
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
