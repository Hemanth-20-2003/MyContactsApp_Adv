# MyContactsApp

## UC-03: User Profile Management
This use case allows logged-in users to manage and update their profile information, including changing passwords or updating preferences. The `User` class provides controlled setter methods that enforce validation and maintain data integrity. The **Command Pattern** is used to represent profile update operations as individual commands, improving flexibility and maintainability of profile-related actions.

## UC-02: User Authentication
This use case enables registered users to log in to the system using their credentials. Authentication logic is implemented using an `Authentication` interface with multiple implementations such as BasicAuth or OAuth, demonstrating polymorphism. The **Strategy Pattern** allows switching between authentication methods dynamically, while a **Singleton SessionManager** maintains the active user session. Password verification uses hashing techniques and login results can be safely handled using `Optional`.

## UC-01: User Registration
This use case allows a new user to create an account by providing email, password, and profile details. A `User` class is designed with encapsulated fields to securely store user information and apply validation logic such as email format checking and password rules. The system uses the **Factory Pattern** to create different types of users such as FreeUser or PremiumUser, while the **Builder Pattern** simplifies the creation of complex User objects with multiple attributes.
