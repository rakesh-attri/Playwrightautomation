# Playwright Salesforce Automation Framework

A comprehensive automation framework built with Playwright, Java, TestNG, and Allure Reports for testing Salesforce applications.

## Framework Features

- **Playwright**: Modern browser automation with auto-waiting and stable locators
- **TestNG**: Test execution and data-driven testing
- **Page Object Model**: Maintainable and reusable page objects
- **Log4j**: Comprehensive logging throughout test execution
- **Allure Reports**: Beautiful and detailed test reports
- **Data-Driven Testing**: Support for Excel and CSV test data
- **Maven**: Build and dependency management

## Project Structure

```
src/test/java/
├── pages/
│   ├── BasePage.java          # Base page with common functionality
│   ├── LoginPage.java         # Salesforce login page object
│   └── AccountPage.java       # Salesforce account page object
├── tests/
│   ├── BaseTest.java          # Base test class with setup/teardown
│   ├── LoginTest.java         # Login functionality tests
│   └── AccountTest.java       # Account management tests
└── utils/
    ├── Log.java               # Logging utility
    ├── ExcelUtils.java        # Excel data reading utility
    └── CSVUtils.java          # CSV data reading utility

testData/
├── LoginData.csv              # Login test data
└── AccountData.csv            # Account test data

Configuration Files:
├── pom.xml                    # Maven configuration
├── testng.xml                 # TestNG suite configuration
├── src/test/resources/
│   ├── log4j2.xml            # Log4j configuration
│   └── allure.properties     # Allure configuration
```

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Chrome browser (for Playwright)

## Setup Instructions

1. **Clone or download the framework**
2. **Update test data files**:
   - Edit `testData/LoginData.csv` with your Salesforce credentials
   - Edit `testData/AccountData.csv` with your test account data
3. **Configure Salesforce URL** in test data files
4. **Install dependencies**: `mvn clean install`

## Test Data Configuration

### Login Data (testData/LoginData.csv)
```csv
TestCase,Username,Password,URL,ExpectedResult
ValidLogin,your-username@domain.com,your-password,https://test.salesforce.com,Success
```

### Account Data (testData/AccountData.csv)
```csv
TestCase,Username,Password,URL,AccountName,AccountType,Industry,Phone,Website
AccountCreation1,your-username@domain.com,your-password,https://test.salesforce.com,Test Account 1,Customer - Direct,Technology,123-456-7890,https://www.testaccount1.com
```

## Running Tests

### Run all tests
```bash
mvn clean test
```

### Run specific test class
```bash
mvn test -Dtest=LoginTest
mvn test -Dtest=AccountTest
```

### Run with specific browser
```bash
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
```

## Generating Reports

### Generate Allure Report
```bash
mvn allure:report
```

### Serve Allure Report
```bash
mvn allure:serve
```

## Test Cases Covered

### Login Tests
- Valid login with correct credentials
- Invalid login with wrong credentials
- Login page elements visibility
- Forgot password functionality

### Account Tests
- Account creation with valid data
- Data-driven account creation
- Account page navigation
- Form validation

## Framework Best Practices

1. **Page Object Model**: Each page has its own class with locators and methods
2. **Data-Driven Testing**: Test data is externalized in CSV/Excel files
3. **Comprehensive Logging**: Every action is logged for debugging
4. **Screenshot Capture**: Screenshots are taken at key points
5. **Stable Locators**: Uses Playwright's auto-waiting and stable selectors
6. **Modular Design**: Reusable components and utilities

## Configuration

### Log4j Configuration
Logs are written to both console and file (`logs/automation.log`).

### Allure Configuration
Reports are generated in `target/allure-results/` directory.

### Browser Configuration
Default browser is Chrome. Can be changed in `BaseTest.java`.

## Troubleshooting

1. **Browser not found**: Ensure Chrome is installed and in PATH
2. **Test data not found**: Check file paths in `testData/` directory
3. **Login failures**: Verify Salesforce credentials and URL
4. **Element not found**: Check if Salesforce UI has changed and update locators

## Contributing

1. Follow the existing code structure
2. Add proper logging for new methods
3. Update test data files as needed
4. Add appropriate Allure annotations for new tests

## Support

For issues and questions, please check the logs in `logs/automation.log` and screenshots in `screenshots/` directory.
