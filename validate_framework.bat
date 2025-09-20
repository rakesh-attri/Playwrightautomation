@echo off
echo ========================================
echo Playwright Salesforce Framework Validation
echo ========================================

echo.
echo 1. Checking Java version...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    exit /b 1
)

echo.
echo 2. Checking Maven version...
mvn -version
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    exit /b 1
)

echo.
echo 3. Creating logs directory...
if not exist "logs" mkdir logs

echo.
echo 4. Creating screenshots directory...
if not exist "screenshots" mkdir screenshots

echo.
echo 5. Validating project structure...
if not exist "src\test\java\pages" (
    echo ERROR: Pages directory not found
    exit /b 1
)
if not exist "src\test\java\tests" (
    echo ERROR: Tests directory not found
    exit /b 1
)
if not exist "src\test\java\utils" (
    echo ERROR: Utils directory not found
    exit /b 1
)
if not exist "testData" (
    echo ERROR: TestData directory not found
    exit /b 1
)

echo.
echo 6. Compiling project...
mvn clean compile
if %errorlevel% neq 0 (
    echo ERROR: Project compilation failed
    exit /b 1
)

echo.
echo 7. Running tests (dry run)...
mvn test -Dtest=LoginTest#testLoginPageElements
if %errorlevel% neq 0 (
    echo WARNING: Test execution failed - this might be expected if Salesforce credentials are not configured
)

echo.
echo ========================================
echo Framework validation completed!
echo ========================================
echo.
echo Next steps:
echo 1. Update testData/LoginData.csv with your Salesforce credentials
echo 2. Update testData/AccountData.csv with your test data
echo 3. Run: mvn clean test
echo 4. Generate reports: mvn allure:serve
echo.
pause
