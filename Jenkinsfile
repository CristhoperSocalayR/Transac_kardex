pipeline {
    agent any

    environment {
        SONARQUBE = 'SonarCloud'
        SONAR_TOKEN = '6ad549b1e284510156162c102325bb0ead18db5b'
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
                sh 'mvn test'
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
