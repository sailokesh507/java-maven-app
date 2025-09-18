def buildJar() {
    echo 'Building the Maven application...'
    sh 'mvn clean package'
}

def buildImage() {
    echo "Building Docker image..."
    // Get short Git commit hash for tagging
    def commitHash = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()

    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
        sh """
            docker build -t lokesh537/docker:${commitHash} .
            echo \$PASS | docker login -u \$USER --password-stdin
            docker push lokesh537/docker:${commitHash}
        """
    }
}

def deployApp() {
    echo 'Deploying the application...'
    // Add your deployment commands here (e.g., SSH to server, docker run, docker-compose)
}

return this
