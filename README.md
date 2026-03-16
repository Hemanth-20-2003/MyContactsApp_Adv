# MyContactsApp

## UC-12: Apply Tags to Contacts
The Apply Tags feature allows a user to assign one or multiple existing tags to contacts. 
The relationship between `Contact` and `Tag` is maintained using object references to ensure proper two-way mapping. This enables structured categorization and efficient filtering of contacts.

## UC-11: Create Tag
The Create Tag feature allows a user to create custom tags (such as Family, Work, Friends) for organizing contacts. A dedicated `Tag` class is introduced with attributes like tag name and a list of associated contacts. Tags are stored under the user and can be reused while assigning them to contacts.

## UC-10: Apply Filters on Contacts
The Apply Filters feature allows a user to filter contacts based on specific criteria such as tag, date added, or frequently contacted status. The `dateAdded` field is automatically assigned when a contact is created, while the frequently contacted option is currently a dummy flag. This feature helps users quickly narrow down and manage contacts efficiently using structured filtering options.

## UC-09: Search Contacts
The Search Contacts feature allows a user to search contacts by name, phone number, email, or tags using regular expressions.  
After selecting a search category, the user enters a keyword (e.g., "ra"), and the system displays all matching contacts with their original index numbers.  
The user can then select a specific contact by entering its displayed number to view detailed information.

## UC-08: Bulk Operations
The Bulk Operations feature allows a logged-in user to perform actions on multiple contacts at once.  
Users can select multiple contacts and choose to delete them, add a common tag, or export them (dummy operation).  
This feature improves efficiency by enabling batch processing instead of handling contacts individually.

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
