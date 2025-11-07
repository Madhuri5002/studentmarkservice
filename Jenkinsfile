pipeline {
    agent any

    environment {
        APP_NAME       = "studentmarkservice"
        APP_NAMESPACE  = "${APP_NAME}-ns"
        IMAGE_NAME     = "${APP_NAME}-image"
        IMAGE_TAG      = "${BUILD_NUMBER}"     // correct tag = build number only
        APP_PORT       = "8100"
        NODE_PORT      = "30081"
        REPLICA_COUNT  = "2"
        KUBE_CONFIG    = "c:\\users\\test\\.kube\\config"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/Madhuri5002/studentmarkservice.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} -f Dockerfile ."
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    withEnv(["KUBECONFIG=${KUBE_CONFIG}"]) {

                        // first-time creation (if not created before)
                        bat "kubectl apply -f k8s/namespace-template.yaml --validate=false"
                        bat "kubectl apply -f k8s/service-template.yaml --validate=false"

                        // update deployment image
                        bat "kubectl set image deployment/studentmarkservice-deployment studentmarkservice=${IMAGE_NAME}:${IMAGE_TAG} -n ${APP_NAMESPACE}"

                        // if deployment does not exist, create it
                        bat "kubectl apply -f k8s/deployment-template.yaml --validate=false"

                        // wait for rollout completion
                        bat "kubectl rollout status deployment/studentmarkservice-deployment -n ${APP_NAMESPACE} --timeout=120s"
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ Build & Deployment Successful!"
        }
        failure {
            echo "❌ Build Failed!"
        }
    }
}
