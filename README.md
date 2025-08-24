## Project Description

This project represents the development of the back-end for a tourism application.  
Its main purpose is to allow users to create and manage a database of museums open to the public, as well as organize tourist groups.
The system provides functionalities for adding museums, creating and managing groups, handling schedules, and ensuring consistency through database operations. 

## Relationship Between Classes and Description and Justification of the Design Patterns Used in the Application

In this application, I used four design patterns that I considered practical for simplifying development: **Singleton**, **Builder**, **Command**, and **Observer**.
Note: For more detailed explanations about the methods, please check the JavaDoc comments in each class.

## Singleton Pattern

I applied the Singleton pattern in the `Database` class, since the database must be unique per session.
This pattern allowed me to define a private constructor and a static field
`private static Database uniqueDatabase` used inside the method `public static Database Instance()`.
This method is called in the Main class (and in specific commands). It checks if the static attribute `uniqueDatabase` is `null` — in which case it creates the database using the private constructor — or otherwise simply returns the already existing instance.

## Builder Pattern

I used the Builder pattern in two classes: Museum and Location.
The reason is that in the CSV-like input file, not all parameters always appear (as specified in the assignment statement: some parameters are mandatory, others optional).
Therefore, I defined inner builder classes (MuseumBuilder and LocationBuilder) that:
    1. Have a public constructor that sets mandatory parameters.
    2. Provide setters for optional fields, each returning the builder reference (`this`) so chaining is possible.
    3. Contain a `build()` method that returns a new Museum or Location object, using private constructors that only accept the builder as a parameter.

A key part of this logic is in the File Parsing class, where I build Location and Museum objects: first instantiating the builder, then checking if each optional field from the input is present (non-null/non-empty), and if so, setting it in the builder. In this way, I could create customized `Location` and `Museum` instances.

## Command Pattern

I used the Command pattern to abstract user actions and make execution easier.
Example actions: adding a group, adding a guide, adding a member to a group, adding a museum, searching for a guide or member, or removing a guide/member.
The user simply creates the specific command class and calls its `execute()` method.

Each specific command implements the Command interface, which declares `execute() throws Exception`.
Each concrete command provides its own implementation and may throw exceptions such as GroupNotExistsException.

Each command also has its own constructor with parameters, since different commands need access to different data (e.g., a museumCode, a timetable, possibly a Person object to add a member or guide, and a PrintWriter for output).
The `GroupNotExistsException` is thrown and handled in the file parsing classes, depending on the first argument read from each line.

## Observer Pattern
The Observer pattern was essential to correctly and efficiently handle event management.
In this design, the Subject is the Museum class, which contains a private list of observers:
`private List<Professor> observers = new ArrayList<Professor>()`

The observers are the guides. When adding a guide to a group (the group being associated with a museum code), I register the guide as an observer of that museum:
`museumWithObserver.attachObserver((Professor) guide)`;

The Observer interface is implemented by each `Person`. When an event is added (parsed from the events.in file, e.g., with the `ADD EVENT` command), the corresponding museum calls `setEvent(message, pwEvent)`.
The message is the event description, and the `PrintWriter` is the output file. This output is propagated so that when an observer is notified, the "email" message is written directly to the output file.

Each observer (guide) receives the notification through `notifyObserver(observer, message, pw)`.
The observer’s update() method then writes the formatted email to the output file.

## Extra Functionality
In the Database class, I added two extra methods:

`museumAvailability()` – assumes that a museum can be visited by at most 3 groups simultaneously in the same timetable, according to the museum’s internal rules.
When executing the ADD GROUP command, this method ensures that no more than 3 groups overlap in a time slot.
If the limit is exceeded, a MuseumFullException is thrown and handled during group addition.

`displayMuseumsSortedByGroups` – sorts museums based on the number of tourist groups they have hosted, from most visited to least visited (excluding those with zero visits).
I implemented this using a HashMap to store each museum (key) and its visit count (value).
The method returns a sorted list using an anonymous comparator, and the results are printed when the parser encounters the `ANALYSE MUSEUMS` command in the input file.
