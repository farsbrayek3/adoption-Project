pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6'
        jdk 'Java 17'
    }

    environment {
        // Pour éviter les erreurs d'encodage dans Maven
        JAVA_OPTS = "-Dfile.encoding=UTF-8"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/farsbrayek3/adoption-Project.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('MySonarQubeServer') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t adoption-project .'
            }
        }
    }
}
