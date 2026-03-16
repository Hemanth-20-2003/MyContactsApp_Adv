# MyContactsApp

## UC-01: User Registration
This use case allows a new user to create an account by providing email, password, and profile details. A `User` class is designed with encapsulated fields to securely store user information and apply validation logic such as email format checking and password rules. The system uses the **Factory Pattern** to create different types of users such as FreeUser or PremiumUser, while the **Builder Pattern** simplifies the creation of complex User objects with multiple attributes.
