# MyContactsApp

## UC-07: Delete Contact
The Delete Contact feature allows users to permanently remove an existing contact from their contact list.  
Users can select a contact and choose the delete option with confirmation before removal.  
The contact is safely removed from the user's contact collection, ensuring data consistency.

## UC-06: Edit Contact
The Edit Contact feature allows users to update existing contact details.  
Users can modify the name, number, or email of a selected contact.  
Changes are applied instantly while maintaining proper encapsulation.

## UC-05: View Contact Details
This use case allows users to view the complete information of a selected contact from their contact list. Getter methods provide controlled access to contact fields, and the `toString()` method is overridden to present formatted contact details for display. The **Decorator Pattern** can be used to add different display formats without modifying the base contact class. Java concepts such as string formatting and `Optional` are used to handle nullable fields and present data safely.

## UC-04: Create Contact
This use case allows a user to add a new contact with details such as name, phone numbers, email addresses, and optional information. Contacts are modeled using a class hierarchy such as `Person` and `Organization`, while related data like phone numbers and emails are managed through composition. The **Builder Pattern** helps construct complex contact objects step-by-step, and a **Factory Pattern** determines which type of contact should be created. Collections like `List` are used to store multiple phone numbers or emails, while unique identifiers are generated using `UUID`.

## UC-03: User Profile Management
This use case allows logged-in users to manage and update their profile information, including changing passwords or updating preferences. The `User` class provides controlled setter methods that enforce validation and maintain data integrity. The **Command Pattern** is used to represent profile update operations as individual commands, improving flexibility and maintainability of profile-related actions.

## UC-02: User Authentication
This use case enables registered users to log in to the system using their credentials. Authentication logic is implemented using an `Authentication` interface with multiple implementations such as BasicAuth or OAuth, demonstrating polymorphism. The **Strategy Pattern** allows switching between authentication methods dynamically, while a **Singleton SessionManager** maintains the active user session. Password verification uses hashing techniques and login results can be safely handled using `Optional`.

## UC-01: User Registration
This use case allows a new user to create an account by providing email, password, and profile details. A `User` class is designed with encapsulated fields to securely store user information and apply validation logic such as email format checking and password rules. The system uses the **Factory Pattern** to create different types of users such as FreeUser or PremiumUser, while the **Builder Pattern** simplifies the creation of complex User objects with multiple attributes.
