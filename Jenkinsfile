pipeline {
    agent any

    environment {
        SONARQUBE = 'SonarCloud'  // Nombre de tu servidor SonarQube configurado en Jenkins
        SONAR_TOKEN = '6ad549b1e284510156162c102325bb0ead18db5b'  // Tu token de SonarQube
    }

    tools {
        maven 'Maven 3.8.1'  // Asegúrate de que Maven esté configurado en Jenkins
        jdk 'JDK 17'         // Asegúrate de que JDK 17 esté configurado en Jenkins
    }

    stages {
        stage('Clone Repository') {
            when {
                branch 'main'  // Solo ejecuta esta etapa en la rama 'main'
            }
            steps {
                git 'https://github.com/CristhoperSocalayR/Transac_kardex.git'
            }
        }

        stage('Compile with Maven') {
            when {
                branch 'main'  // Solo ejecuta esta etapa en la rama 'main'
            }
            steps {
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            when {
                branch 'main'  // Solo ejecuta esta etapa en la rama 'main'
            }
            steps {
                script {
                    withSonarQubeEnv("${env.SONARQUBE}") {
                        sh "mvn sonar:sonar -Dsonar.projectKey=TransacKardex -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=${SONAR_TOKEN}"
                    }
                }
            }
        }

        stage('Wait for SonarQube analysis') {
            when {
                branch 'main'  // Solo ejecuta esta etapa en la rama 'main'
            }
            steps {
                script {
                    waitForQualityGate abortPipeline: true  // Esto espera que el análisis de SonarQube termine
                }
            }
        }

        stage('Run Unit Tests') {
            when {
                branch 'main'  // Solo ejecuta esta etapa en la rama 'main'
            }
            steps {
                sh 'mvn test -Dtest=ProductServiceTest,SupplierServiceTest,TypeSupplierServiceTest'  // Reemplaza TestClass1, TestClass2, TestClass3 con los nombres de tus clases de prueba
            }
        }

        stage('Generate .jar Artifact') {
            when {
                branch 'main'  // Solo ejecuta esta etapa en la rama 'main'
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
