#!/usr/bin/env python3
"""
Script to create Excel test data files for the Playwright Salesforce Automation Framework
Run this script to generate TestData.xlsx with proper sheets for LoginData and AccountData
"""

import pandas as pd
import os

def create_excel_test_data():
    """Create Excel file with test data sheets"""
    
    # Create testData directory if it doesn't exist
    os.makedirs('testData', exist_ok=True)
    
    # Login Data
    login_data = {
        'TestCase': ['ValidLogin', 'InvalidLogin', 'EmptyCredentials', 'EmptyUsername', 'EmptyPassword'],
        'Username': ['testuser@example.com', 'invalid@example.com', '', '', 'testuser@example.com'],
        'Password': ['password123', 'wrongpassword', '', 'password123', ''],
        'URL': ['https://test.salesforce.com'] * 5,
        'ExpectedResult': ['Success', 'Failure', 'Failure', 'Failure', 'Failure']
    }
    
    # Account Data
    account_data = {
        'TestCase': ['AccountCreation1', 'AccountCreation2', 'AccountCreation3'],
        'Username': ['testuser@example.com'] * 3,
        'Password': ['password123'] * 3,
        'URL': ['https://test.salesforce.com'] * 3,
        'AccountName': ['Test Account 1', 'Test Account 2', 'Test Account 3'],
        'AccountType': ['Customer - Direct', 'Customer - Channel', 'Prospect'],
        'Industry': ['Technology', 'Healthcare', 'Finance'],
        'Phone': ['123-456-7890', '987-654-3210', '555-123-4567'],
        'Website': ['https://www.testaccount1.com', 'https://www.testaccount2.com', 'https://www.testaccount3.com']
    }
    
    # Create Excel file with multiple sheets
    with pd.ExcelWriter('testData/TestData.xlsx', engine='openpyxl') as writer:
        pd.DataFrame(login_data).to_excel(writer, sheet_name='LoginData', index=False)
        pd.DataFrame(account_data).to_excel(writer, sheet_name='AccountData', index=False)
    
    print("Excel test data file created successfully: testData/TestData.xlsx")
    print("Sheets created: LoginData, AccountData")

if __name__ == "__main__":
    create_excel_test_data()
