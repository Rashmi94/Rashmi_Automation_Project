pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Rashmi94/Rashmi_Automation_Project.git'
            }
        }

        stage('Test') {
            steps {
                sh "chmod +x gradlew"
                sh "./gradlew clean test -DseleniumGrid=false"
            }
        }

        stage('Publish Report') {
            steps {
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'build/reports/extent',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'build/reports/extent/*.html', fingerprint: true
            // junit 'build/test-results/test/*.xml'
        }

        success {
            emailext(
                to: 'rashmi94a@gmail.com',
                subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                attachLog: true,
                body: """
                <html>
                <body>
                <p>Hello Team,</p>

                <p><b>Status:</b> <span style="color:green;"><b>SUCCESS</b></span></p>
                <p><b>Job:</b> ${env.JOB_NAME}</p>
                <p><b>Build:</b> #${env.BUILD_NUMBER}</p>

                <p><b>Build URL:</b>
                <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>

                <p><b>Extent Report:</b>
                <a href="${env.BUILD_URL}Extent_20Report/">
                View Report</a></p>

                <p>Regards,<br><b>Automation Team</b></p>
                </body>
                </html>
                """
            )
        }

        failure {
            emailext(
                to: 'rashmi94a@gmail.com',
                subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                attachLog: true,
                body: """
                <html>
                <body>
                <p><b style="color:red;">BUILD FAILED</b></p>

                <p><b>Job:</b> ${env.JOB_NAME}</p>
                <p><b>Build:</b> #${env.BUILD_NUMBER}</p>

                <p><b>Build URL:</b>
                <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>

                <p><b>Extent Report (if generated):</b>
                <a href="${env.BUILD_URL}Extent_20Report/">Open Report</a></p>

                <p>Regards,<br><b>Automation Team</b></p>
                </body>
                </html>
                """
            )
        }
    }
}
