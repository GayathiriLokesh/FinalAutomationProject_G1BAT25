
# Final Project HRMS Automation Framework

## Project Overview
This project is a Selenium BDD automation framework developed for the HRMS application.  
It covers major admin and ESS user workflows using Java, Selenium WebDriver, Cucumber, Maven, and JUnit.

## Tools and Technologies
- Java
- Selenium WebDriver
- Cucumber BDD
- Maven
- JUnit
- Git and GitHub

## Framework Features
- Page Object Model design
- Reusable utility methods
- Hooks for setup and teardown
- Screenshot capture for failed scenarios
- Maven execution support
- HTML test reports
- Excel-based test data handling
- Database validation for selected scenarios
- Logging using Log4j2

## Project Structure
- `src/test/java` - step definitions, runners, utilities, page classes
- `src/test/resources` - feature files, configuration
- `testdata` - input test data files
- `logs` - execution logs
- `target` - generated reports

## Covered Modules
- Login
- login error validation
- Add Employee
- Create Login Details
- Personal Details
- Contact Details
- Dependents
- Profile Picture Upload
- Search Employee

## How to Run
1. Clone the repository
2. Open in IntelliJ
3. Refresh Maven project
4. Run the test runner class or use:
   ```bash
   mvn test
