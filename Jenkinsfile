pipeline {
    agent any

    environment {
        registry = "mkrishnap/university-enrollment"
        registryCredential = 'dockerhub'
        dockerImage = ''
    }

    tools {
        gradle 'gradle-711'
    }

    triggers {
        pollSCM '* * * * *'
    }

    stages {
        stage('Source') {
            steps {
                git clone 'https://github.com/murali101/university-enrollment.git'
            }
        }
        stage('Assemble') {
            steps {
                sh 'gradle clean assemble'
            }
        }
        stage('Build') {
            steps {
                sh 'gradle clean build'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'gradle bootBuildImage'
                sh 'docker build -t university-enrollment:latest .'

            }
        }

        stage('Publish Docker Image') {
            steps {
                sh 'docker login -u mkrishnap -p abcd@1234'
                sh 'docker tag springboot-jenkins mkrishnap/university-enrollment'
                sh 'docker push mkrishnap/university-enrollment'
            }
        }

        stage('Chart') {
                    steps {
                        sh 'helm create university-enrollment'
                        sh 'helm lint ./university-enrollment'
                        sh 'helm install --dry-run -name university-enrollment ./university-enrollment'
                    }
                }

        stage('Deploy') {
            steps {
                sh 'helm install -name university-enrollment ./university-enrollment'
            }
        }
     }
}