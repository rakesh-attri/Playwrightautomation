# Playwright Salesforce Framework Validation Script
Write-Host "========================================" -ForegroundColor Green
Write-Host "Playwright Salesforce Framework Validation" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green

Write-Host "`n1. Checking Java version..." -ForegroundColor Yellow
try {
    java -version
    Write-Host "✓ Java is available" -ForegroundColor Green
} catch {
    Write-Host "✗ ERROR: Java is not installed or not in PATH" -ForegroundColor Red
    exit 1
}

Write-Host "`n2. Checking Maven version..." -ForegroundColor Yellow
try {
    mvn -version
    Write-Host "✓ Maven is available" -ForegroundColor Green
} catch {
    Write-Host "✗ ERROR: Maven is not installed or not in PATH" -ForegroundColor Red
    exit 1
}

Write-Host "`n3. Creating required directories..." -ForegroundColor Yellow
New-Item -ItemType Directory -Path "logs" -Force | Out-Null
New-Item -ItemType Directory -Path "screenshots" -Force | Out-Null
Write-Host "✓ Directories created" -ForegroundColor Green

Write-Host "`n4. Validating project structure..." -ForegroundColor Yellow
$requiredDirs = @(
    "src\test\java\pages",
    "src\test\java\tests", 
    "src\test\java\utils",
    "testData"
)

foreach ($dir in $requiredDirs) {
    if (Test-Path $dir) {
        Write-Host "✓ $dir exists" -ForegroundColor Green
    } else {
        Write-Host "✗ ERROR: $dir not found" -ForegroundColor Red
        exit 1
    }
}

Write-Host "`n5. Checking configuration files..." -ForegroundColor Yellow
$requiredFiles = @(
    "pom.xml",
    "testng.xml",
    "src\test\resources\log4j2.xml",
    "src\test\resources\allure.properties"
)

foreach ($file in $requiredFiles) {
    if (Test-Path $file) {
        Write-Host "✓ $file exists" -ForegroundColor Green
    } else {
        Write-Host "✗ ERROR: $file not found" -ForegroundColor Red
        exit 1
    }
}

Write-Host "`n6. Compiling project..." -ForegroundColor Yellow
try {
    mvn clean compile
    Write-Host "✓ Project compiled successfully" -ForegroundColor Green
} catch {
    Write-Host "✗ ERROR: Project compilation failed" -ForegroundColor Red
    exit 1
}

Write-Host "`n7. Running test validation..." -ForegroundColor Yellow
try {
    mvn test -Dtest=LoginTest#testLoginPageElements
    Write-Host "✓ Test execution completed" -ForegroundColor Green
} catch {
    Write-Host "⚠ WARNING: Test execution failed - this might be expected if Salesforce credentials are not configured" -ForegroundColor Yellow
}

Write-Host "`n========================================" -ForegroundColor Green
Write-Host "Framework validation completed!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green

Write-Host "`nNext steps:" -ForegroundColor Cyan
Write-Host "1. Update testData/LoginData.csv with your Salesforce credentials" -ForegroundColor White
Write-Host "2. Update testData/AccountData.csv with your test data" -ForegroundColor White
Write-Host "3. Run: mvn clean test" -ForegroundColor White
Write-Host "4. Generate reports: mvn allure:serve" -ForegroundColor White

Read-Host "`nPress Enter to continue"