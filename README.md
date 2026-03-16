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
## UC-01 to UC-05: User and Contact Management
The system allows a new user to register by providing email, password, and profile information (UC-01) and enables registered users to securely log in using valid credentials (UC-02). 
Once authenticated, the logged-in user can manage their profile information, including updating details or changing their password (UC-03). 
The user can also create new contacts by adding name, phone number, email, and optional fields (UC-04), and view complete details of any specific contact stored in their contact list (UC-05).
