pipeline {
    agent any
    tools {
        maven 'MAVEN_HOME'
        jdk 'JAVA_HOME'
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out code from Git"
                checkout scm
            }
        }

        stage('MVN Build') {
            steps {
                echo "Running Maven build"
                sh 'mvn clean install'
            }
        }

        stage('JDK Test') {
            steps {
                echo "Testing JDK Installation"
                sh '''
                # Print Java version to verify JDK installation
                java -version

                # Print JAVA_HOME to ensure it's set correctly
                echo $JAVA_HOME
                '''
            }
        }
        stage('Unit Tests - JUnit & Mockito') {  
            steps {  
                echo 'Running unit tests...'  
                script {  
                    // Run tests with JUnit and Mockito  
                    sh 'mvn test'  
                }  
            }  
            post {  
                always {  
                    junit 'target/surefire-reports/*.xml' // Collect JUnit test results  
                }  
                failure {  
                    echo 'Unit tests failed!'  
                }  
            }  
        }  

        stage('Code Quality Test') {
            steps {
                withSonarQubeEnv('Sonar') {
                    sh 'mvn sonar:sonar -Dsonar.host.url=http://172.17.0.2:9000 -Dsonar.login=squ_bdf4362b8971c688e1ac355bd433ff1a1664f75d'
                }
            }
        }
        stage('Prepare for Nexus Deployment') {
            steps {
                echo "Preparing for Nexus deployment"
                script {
                    def settingsFile = '/usr/share/maven/conf/settings.xml'
                    if (fileExists(settingsFile)) {
                        sh "cat ${settingsFile}"
                    } else {
                        error "Maven settings.xml file not found at ${settingsFile}"
                    }
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                echo "Deploying to Nexus"
                withCredentials([usernamePassword(credentialsId: 'deploymentRepo', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                    sh '''
                    mvn deploy \
                        -DaltDeploymentRepository=deploymentRepo::default::http://${NEXUS_USERNAME}:${NEXUS_PASSWORD}@172.17.0.3:8081/repository/maven-releases/
                    '''
                }
            }
        }
        stage('Push to Docker Hub') {
            steps {
                sh '''
                docker login -u ikbel345 -p Mi@1995500
                docker push ikbel345/tp-foyer:5.0.0
                '''
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
    }
}
