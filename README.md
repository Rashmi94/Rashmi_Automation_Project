# Rashmi UI Automation Project

A comprehensive Selenium-based UI automation framework built with Java, Gradle, and TestNG for web application testing.

## 📋 Table of Contents
- [Overview](#overview)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Reports](#reports)
- [CI/CD Integration](#cicd-integration)
- [Features](#features)
- [Contributing](#contributing)

## 🎯 Overview

This is a robust UI automation framework designed for testing web applications using Selenium WebDriver. The framework follows the Page Object Model (POM) design pattern and includes comprehensive reporting, logging, and CI/CD integration capabilities.

**Application Under Test:** [Rahul Shetty Academy Login Practice](https://rahulshettyacademy.com/loginpagePractise/)

## 🛠 Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | - | Programming Language |
| Gradle | - | Build Tool |
| Selenium WebDriver | 4.39.0 | Browser Automation |
| TestNG | 7.11.0 | Testing Framework |
| ExtentReports | 5.1.1 | Test Reporting |
| Log4j2 | 2.25.2 | Logging Framework |
| Apache POI | 5.4.1 | Excel Data Handling |
| REST Assured | 5.5.6 | API Testing |
| MySQL Connector | 9.4.0 | Database Connectivity |
| Commons IO | 2.20.0 | File Operations |

## 📁 Project Structure

```
Rashmi_UI_Automation_Project/
├── src/
│   ├── main/
│   │   ├── java/com/project/
│   │   │   ├── actiondriver/      # WebDriver action utilities
│   │   │   ├── base/              # Base test setup and configuration
│   │   │   ├── listners/          # TestNG listeners
│   │   │   ├── pages/             # Page Object Model classes
│   │   │   └── utilities/         # Utility classes
│   │   │       ├── ApiUtility.java
│   │   │       ├── DatabaseUtility.java
│   │   │       ├── DataProvidersUtility.java
│   │   │       ├── ExcelReaderUtility.java
│   │   │       ├── ExtentManager.java
│   │   │       ├── LoggingManager.java
│   │   │       └── RetryAnalyzer.java
│   │   └── resources/
│   │       ├── config.properties  # Application configuration
│   │       └── log4j2.xml        # Logging configuration
│   └── test/
│       ├── java/com/project/test/ # Test classes
│       └── resources/
│           ├── testng.xml        # TestNG suite configuration
│           ├── testdata/         # Test data files
│           └── screenshots/      # Test screenshots
├── build/                        # Build artifacts
│   ├── reports/                  # Test reports
│   └── test-results/            # TestNG results
├── logs/                         # Application logs
│   ├── app.log
│   └── error.log
├── build.gradle                  # Gradle build configuration
├── Jenkinsfile                   # Jenkins CI/CD pipeline
└── README.md                     # Project documentation
```

## ✅ Prerequisites

Before running this project, ensure you have the following installed:

- **Java Development Kit (JDK)** 8 or higher
- **Gradle** (or use the included Gradle wrapper)
- **Git** (for version control)
- **Chrome/Firefox/Edge** browser (based on your test requirements)
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)

## 🚀 Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Rashmi94/Rashmi_Automation_Project.git
   cd Rashmi_UI_Automation_Project
   ```

2. **Verify Java installation:**
   ```bash
   java -version
   ```

3. **Build the project:**
   ```bash
   ./gradlew build
   ```
   
   On Windows:
   ```bash
   gradlew.bat build
   ```

## ⚙️ Configuration

### Application Configuration (`src/main/resources/config.properties`)

```properties
# Application URL
url=https://rahulshettyacademy.com/loginpagePractise/

# Browser Configuration
browser=chrome

# Credentials
username=rahulshettyacademy
password=learning

# Wait Times (in seconds)
implicitWait=10
explicitWait=30
```

### TestNG Configuration (`src/test/resources/testng.xml`)

The TestNG suite is configured to run tests in parallel with 3 threads:
- **Suite Name:** UI Automation Suite
- **Parallel Execution:** Methods level
- **Thread Count:** 3
- **Listeners:** Custom TestListener for enhanced reporting

## 🧪 Running Tests

### Run all tests:
```bash
./gradlew clean test
```

### Run tests with specific browser:
```bash
./gradlew clean test -Dbrowser=chrome
```

### Run tests without Selenium Grid:
```bash
./gradlew clean test -DseleniumGrid=false
```

### Run specific test class:
```bash
./gradlew clean test --tests "com.project.test.LoginPageTest"
```

### Run tests via TestNG XML:
```bash
./gradlew clean test -DsuiteXmlFile=src/test/resources/testng.xml
```

## 📊 Reports

### ExtentReports
After test execution, ExtentReports are generated at:
```
build/reports/extent/ExtentReport.html
```

Open the HTML file in a browser to view detailed test execution reports with:
- Test pass/fail status
- Screenshots on failure
- Execution time
- Test logs and stack traces

### TestNG Reports
Standard TestNG reports are available at:
```
build/reports/tests/test/index.html
```

### Logs
Application logs are stored in:
- `logs/app.log` - General application logs
- `logs/error.log` - Error logs

## 🔄 CI/CD Integration

### Jenkins Pipeline

The project includes a Jenkinsfile for CI/CD automation with the following stages:

1. **Checkout:** Clones the repository from GitHub
2. **Test:** Executes the test suite
3. **Publish Report:** Publishes ExtentReports to Jenkins
4. **Post Actions:**
   - Archives test reports
   - Sends email notifications on success/failure

### Running in Jenkins:
1. Create a new Pipeline job in Jenkins
2. Point to the repository: `https://github.com/Rashmi94/Rashmi_Automation_Project.git`
3. Jenkins will automatically detect and use the Jenkinsfile
4. Configure email notifications (update email address in Jenkinsfile)

## ✨ Features

### Framework Capabilities:
- ✅ **Page Object Model (POM)** design pattern
- ✅ **Data-Driven Testing** using Excel files (Apache POI)
- ✅ **Parallel Test Execution** with TestNG
- ✅ **Cross-Browser Testing** support
- ✅ **ExtentReports** for detailed HTML reporting
- ✅ **Log4j2** for comprehensive logging
- ✅ **Screenshot capture** on test failures
- ✅ **Retry Analyzer** for flaky test handling
- ✅ **Custom TestNG Listeners** for enhanced reporting
- ✅ **API Testing** support with REST Assured
- ✅ **Database Testing** capabilities
- ✅ **Jenkins CI/CD** integration
- ✅ **Email notifications** for test results

### Utility Classes:
- **ActionDriver:** Reusable WebDriver actions
- **ExtentManager:** ExtentReports configuration and management
- **LoggingManager:** Centralized logging utility
- **ExcelReaderUtility:** Read/write Excel test data
- **DataProvidersUtility:** TestNG data providers
- **DatabaseUtility:** Database operations
- **ApiUtility:** REST API testing utilities
- **RetryAnalyzer:** Automatic retry for failed tests

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📧 Contact

**Project Maintainer:** Rashmi Devanshu Pal 
**Email:** rashmi94a@gmail.com  
**Repository:** [https://github.com/Rashmi94/Rashmi_Automation_Project](https://github.com/Rashmi94/Rashmi_Automation_Project)

## 📝 License

This project is available for demo purposes.

---

**Happy Testing! 🚀**

