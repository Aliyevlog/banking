UniTech - Backend Developer Guide
Welcome to UniTech! As a backend developer for our fintech solution, your role is crucial in implementing and maintaining the backend functionalities. Below are the descriptions of the initial functionalities along with the definition of done for each one. Your task is to implement these functionalities.

Functionalities
1. Register API
Description: Register in UniTech app with a PIN.

Definition of Done:

Ability to register with a PIN.
Proper error message when trying to register with an already registered PIN.
2. Login API
Description: Login to UniTech account with PIN and password.

Definition of Done:

Ability to login with a PIN and password.
Proper error message for wrong credentials.
3. Get Accounts API
Description: View user's active accounts.

Definition of Done:

Display only active accounts.
4. Account to Account API
Description: Make transfers between accounts.

Definition of Done:

Successful transfers between accounts.
Proper error messages for insufficient balance, transfer to the same account, transfer to inactive accounts, and non-existing accounts.
5. Currency Rates API
Description: View currency rates.

Definition of Done:

Ability to see up-to-date currency rates.
Cost optimization for the company.
Implementation Guide
Register API: Implement the registration endpoint to allow users to register with a PIN. Ensure proper validation and error handling.
Login API: Implement the login endpoint to allow users to login with their PIN and password. Validate credentials and handle errors gracefully.
Get Accounts API: Implement the endpoint to retrieve active user accounts. Filter out inactive accounts and ensure proper data retrieval.
Account to Account API: Implement the endpoint for making transfers between accounts. Validate transfer details and handle errors appropriately.
Currency Rates API: Implement the endpoint to fetch up-to-date currency rates. Optimize cost while ensuring timely and accurate data retrieval.
Git Repository
Ensure that your code is properly organized and documented. Use meaningful commit messages and follow best practices for version control. Collaborate with team members effectively by creating branches for feature development and merging changes through pull requests.
