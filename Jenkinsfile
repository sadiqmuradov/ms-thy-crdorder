pipeline {
    agent any
    
    options {
        skipDefaultCheckout true
    }

    stages {

        stage ('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/${BRANCH_NAME}']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'LocalBranch', localBranch: "**"]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'UFCBitbucket', 
                url: 'https://UFCBitbucket@bitbucket.pshb.local:8463/scm/apl/ms-thy-crdorder.git']]])
            }
        }
      
        stage ('Checkout config-repo') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'config-repo']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'UFCBitbucket', 
                url: 'https://UFCBitbucket@bitbucket.pshb.local:8463/scm/sp/config-repo.git']]])
            }
        }
      
        stage ('Build') {
            steps {
                script {
                    sh 'chmod +x gradlew'
                    sh './gradlew build -x test'
                }
            }
        }
       /* 
        stage ('SonarQube') {
            steps {
                script {
                    if (BRANCH_NAME == 'master') {
                        withSonarQubeEnv ('SonarQube') {
                            sh 'chmod +x gradlew'
                            sh './gradlew sonarqube -Dsonar.branch.name=master -Dsonar.host.url=http://192.168.10.125 -Dsonar.login=18b145db92099cd1ae7fc5514633ccf07ada9343'
                        }
                    } else if (BRANCH_NAME == 'development') {
                        withSonarQubeEnv ('SonarQube') {
                            sh 'chmod +x gradlew'
                            sh './gradlew sonarqube -Dsonar.branch.name=development -Dsonar.host.url=http://192.168.10.125 -Dsonar.login=18b145db92099cd1ae7fc5514633ccf07ada9343'
                        }
                    } else {
                        withSonarQubeEnv ('SonarQube') {
                            sh 'chmod +x gradlew'
                            sh './gradlew sonarqube -Dsonar.branch.name=refs/pull-requests/${PRID}/from -Dsonar.host.url=http://192.168.10.125 -Dsonar.login=18b145db92099cd1ae7fc5514633ccf07ada9343'
                        }
                    }
                }
            }
        }
      */
        stage ('Building Docker container') {
            steps {
                script {
                    sh 'docker build -t 192.168.10.253:18443/repository/analoglab/ms-thy-crdorder:${BUILD_NUMBER} . || docker rmi 192.168.10.253:18443/repository/analoglab/ms-thy-crdorder:${BUILD_NUMBER}'
            }
        }
    }
        stage ('Uploading docker image to Nexus') {
            steps {
                script {
                    sh 'docker login 192.168.10.253:18443'
                    sh 'docker push 192.168.10.253:18443/repository/analoglab/ms-thy-crdorder:${BUILD_NUMBER} || docker rmi 192.168.10.253:18443/repository/analoglab/ms-thy-crdorder:${BUILD_NUMBER}'
                    sh 'docker rmi 192.168.10.253:18443/repository/analoglab/ms-thy-crdorder:${BUILD_NUMBER}'
            }
        }
    }
        stage ('Working with k8s') {
            steps {
                script {
                  if (PRDESTINATION == 'development') {
                    sh 'ssh -oStrictHostKeyChecking=no kadmin@192.168.110.130 "ls -la"'
                    sh 'sed -i "s/latest/${BUILD_NUMBER}/g" ms-thy-crdorder.yml'
                    sh 'scp -r ms-thy-crdorder.yml kadmin@192.168.110.130:~/'
                    sh 'ssh kadmin@192.168.110.130 "kubectl apply -f ms-thy-crdorder.yml"'
                  } else if (PRDESTINATION == 'master') {
                    sh 'ssh -oStrictHostKeyChecking=no kadmin@192.168.10.146 "ls -la"'
                    sh 'sed -i "s/latest/${BUILD_NUMBER}/g" ms-thy-crdorder.yml'
                    sh 'scp -r ms-thy-crdorder.yml kadmin@192.168.10.146:~/'
                    sh 'ssh kadmin@192.168.10.146 "kubectl apply -f ms-thy-crdorder.yml"'
                  }
            }
        }
    }
    }
    post {
        always {
            echo 'JENKINS PIPELINE'
            deleteDir()
        }
        success {
            echo 'JENKINS PIPELINE SUCCESSFUL'
            slackSend baseUrl: 'https://pashabank-az.slack.com/services/hooks/jenkins-ci/', channel: '#ufc-builds', color: '#00BB00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${BRANCH_NAME})", teamDomain: 'pashabank-az', token: 'JVbYAwQ0TOqVIuFBduadhblz', tokenCredentialId: 'slack-token'
        }
        aborted {
            echo 'JENKINS PIPELINE ABORTED'
            slackSend baseUrl: 'https://pashabank-az.slack.com/services/hooks/jenkins-ci/', channel: '#ufc-builds', color: '#FF5500', message: "ABORTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${BRANCH_NAME})", teamDomain: 'pashabank-az', token: 'JVbYAwQ0TOqVIuFBduadhblz', tokenCredentialId: 'slack-token'
        }
        failure {
            echo 'JENKINS PIPELINE FAILED'
            slackSend baseUrl: 'https://pashabank-az.slack.com/services/hooks/jenkins-ci/', channel: '#ufc-builds', color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${BRANCH_NAME})", teamDomain: 'pashabank-az', token: 'JVbYAwQ0TOqVIuFBduadhblz', tokenCredentialId: 'slack-token'
        }
    }
}
