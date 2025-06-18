pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6'   // configure this Maven in Jenkins tools
        jdk 'Java 21'         // configure this Java in Jenkins tools
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/farsbrayek3/adoption-Project.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('MySonarServer') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    docker.build("farsbrayek3/adoption-app:latest").push()
                }
            }
        }

        stage('Docker Compose Deploy') {
            steps {
                sh 'docker-compose up -d'
            }
        }
    }
}
