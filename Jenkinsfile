pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6'
        jdk 'Java 17'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/farsbrayek3/adoption-Project.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Docker Build & Push') {
            steps {
                sh 'docker build -t adoption-project .'
                // si tu veux pousser vers Docker Hub : docker push
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('MySonarQubeServer') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Upload to Nexus') {
            steps {
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: 'nexus:8081',
                    groupId: 'com.adoption',
                    version: '1.0.0',
                    repository: 'maven-releases',
                    credentialsId: 'nexus-creds',
                    artifacts: [[artifactId: 'adoption', classifier: '', file: 'target/adoption.jar', type: 'jar']]
                )
            }
        }
    }
}
