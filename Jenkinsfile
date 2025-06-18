pipeline {
    agent any

    tools {
        maven 'Maven 3.6.3'
        jdk 'Java 11'
    }

    environment {
        GITHUB_CREDENTIALS = 'your-credential-id'
        DOCKER_IMAGE = 'yourdockerhubusername/adoption-project'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: "${GITHUB_CREDENTIALS}", url: 'https://github.com/farsbrayek3/adoption-Project.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }


        stage('SonarQube Analysis') {
          environment { scannerHome = tool 'SonarScanner' }
          steps {
            withSonarQubeEnv('SonarQube') {
              sh "${scannerHome}/bin/sonar-scanner " +
                 "-Dsonar.projectKey=adoptionproject " +
                 "-Dsonar.sources=src "
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
                sh 'docker build -t $DOCKER_IMAGE .'
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh 'docker push $DOCKER_IMAGE'
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                sh 'docker-compose down || true'
                sh 'docker-compose up -d'
            }
        }

        stage('Publish to Nexus') {
            steps {
                sh 'mvn deploy'
            }
        }
    }
}
