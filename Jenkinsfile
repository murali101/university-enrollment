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
                git 'https://github.com/murali101/university-enrollment.git'
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
                sh 'docker tag university-enrollment mkrishnap/university-enrollment'
                sh 'docker push mkrishnap/university-enrollment'
            }
        }
     }
}