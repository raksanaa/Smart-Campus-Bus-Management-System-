
Problem Statement
Campus students often face uncertainty around shuttle availability and arrival times, especially during evenings and weekends. This project aims to address that issue with a real-time shuttle system that allows students to request rides and receive ETA updates through a user-friendly app interface.

The Evening Shuttle system is a full-stack application built to simulate a real-world shuttle service on a university campus. The platform allows students to request rides, get real-time ETAs, and enables shuttle tracking, all handled via a robust backend.

What sets this project apart is its strong emphasis on object-oriented design principles and the strategic use of design patterns to improve code structure, maintainability, and scalability.

Core Features
Student Ride Requests

Students can request a ride by entering their SUID (Syracuse University ID).
The backend validates the SUID against a PostgreSQL database of registered students.
If valid, the system stores the student data and returns an estimated time of arrival (ETA).
If the system is overloaded or unavailable, it returns an service unavailable error.
Smart Shuttle Logic

A single shuttle services the system.
Idle Behavior: Waits at the campus stop when no passengers are onboard.
Boarding Behavior: If one student boards, it travels to the student’s home, drops them off, and returns.
Multiple Passengers: Maintains a queue based on boarding order, completing drop-offs accordingly.
Live Shuttle Tracking

The shuttle sends location updates every second via an HTTP request with its GPS coordinates.
This allows for future real-time mapping and dynamic ETAs.
Boarding Confirmation

Once a student boards (swipes their SUID Card), the shuttle makes a server call with their SUID and address, officially registering them on the journey.
UML Sequence Diagram
This sequence diagram illustrates the core interactions between the components of the Evening Shuttle system: the Student App, Shuttle, Server, and Database.

Press enter or click to view image in full size

Source: Author
API Endpoints
GET /requestPickup?suid=123456789Validates the SUID and returns ETA.
POST /addPassenger?suid=123456789&address="1"Registers a boarded passenger.
POST /shuttleLocation?longitude=12.3&latitude=34.2Updates the shuttle’s real-time location.
I aimed to create a complete, production-grade simulation project ready for scaling or extension.

Application Architecture
For the backend, I have adopted a Layered Architecture approach commonly used in Spring Boot applications. This includes clearly defined Controller, Service, Repository (DAO), and Model (Entities+DTO) layers to ensure modularity, separation of concerns, and maintainability.

Since the frontend is being developed as a standalone application, the backend does not include a view layer. Instead, I am building a Spring Boot RESTful API to handle all server-side operations and enable seamless communication with the frontend via HTTP requests.

Press enter or click to view image in full size
<img width="939" height="416" alt="image" src="https://github.com/user-attachments/assets/ac60c8e1-5b61-4639-b49c-2b41de7718cc" />

Source: Author
Frontend: React.js

Backend: Spring Boot, Spring Data JPA, Hibernate (ORM Tool)

Database: Supabase Cloud PostgreSQL

Deployment: The front end is deployed on Netlify, and the back end is deployed on Render

Please note: The backend is hosted on a free Render instance, which will spin down during periods of inactivity. As a result, initial requests may experience a delay of approximately 50 seconds or more.

Shuttle Operation Logic
The shuttle system is implemented using two primary data structures: a HashMap and a Queue. The logic models the shuttle’s behavior based on three distinct operational states: Idle, Boarding, and In-Transit. Each state governs how ride requests are handled and how Estimated Time of Arrival (ETA) is determined.


Enter your email
Subscribe

Remember me for faster sign in

Shuttle States and Request Handling:

Idle State
The shuttle is stationary at the campus stop with no passengers.
ETA Response: 0 seconds
Behavior: The shuttle is available to accept new ride requests.
2. Boarding State

At least one student has boarded the shuttle.
A 30-second countdown timer is initiated upon the first student boarding.
Before the 30-second timer hits zero, additional students may board the shuttle.
ETA Response: 30 - x seconds, where x is the time elapsed since the boarding process started.
Behavior: The shuttle continues to accept ride requests during this window.
3. In-Transit State

The shuttle departs to drop off passengers at their respective destinations.
ETA Response: A fixed value of 10 seconds is returned for new requests.
Behavior: The shuttle does not accept new ride requests in this state.
Once the journey ends, the shuttle returns to the Idle state.
Data Handling Strategy:

Ride Requests: When a student sends a pickup request, their SUID and related information are stored in a HashMap for efficient lookup.
Boarding Events:
When a student boards the shuttle (simulated via an HTTP request), the system checks the HashMap for a valid SUID.
If the SUID is found, the student’s data is added to aQueue, which maintains the ordered list of passenger drop-off addresses.
Timeout Handling: Students who fail to board the shuttle within the 30-second boarding window will have their request automatically cancelled and removed from the HashMap.
Design Patterns
I have used three design patterns in this application.

Singleton

In the Spring Framework, the Singleton design pattern is employed by default for objects (beans) managed by the Inversion of Control (IOC) Container. This means that Spring creates only one instance of each bean within the container, and this single instance is reused whenever the bean is required in the application. Spring manages the entire lifecycle of these beans, including their instantiation, initialization, and destruction.

By default, Spring beans are singletons, ensuring that only one instance of the bean exists within the application context.

In the application, both the shuttle service and the database connection should adhere to the Singleton design principle, ensuring that only a single instance of each is created and used throughout the application’s lifecycle.

Factory

The Factory pattern has been implemented with consideration for the future scalability of the project. Currently, the application handles ride requests exclusively from students, meaning only Student passengers are supported. However, to accommodate potential future requirements, the design allows for the easy addition of other passenger types, such as Guest, VIP, and others.

State

As we have discussed before, the shuttle has 3 states: Idle, Boarding, and In Transit. The State Pattern enables the application to manage these states and transitions in a structured and maintainable manner, allowing for flexibility and future enhancements.

Understanding the application UI
Press enter or click to view image in full size
<img width="891" height="452" alt="image" src="https://github.com/user-attachments/assets/c5d5add6-a523-4a92-89e6-9c09ff471523" />

Source: Author
The application interface is designed to simulate the functionality of a real-time evening shuttle service on a university campus. The interface is divided into two primary sections: Student Panel and Shuttle Control Panel.

Student Panel

This section simulates multiple students using the system concurrently. For demonstration purposes, five students are listed with their respective SUIDs. In a real-world scenario, each student would act independently.

Each student has the option to request a ride by clicking the “Request Pickup” button.
Upon clicking the button, the system displays the current ETA (Estimated Time of Arrival) and the student’s booking status.
To receive updated ETAs, the student must click the “Request Pickup” button again, even after the ride has been booked.
Future Scope:
Currently, real-time ETA updates require manual interaction. This could be improved by implementing polling, a mechanism that automatically fetches the latest status at regular intervals. This technique is already used in the Shuttle Status section to display live shuttle updates.

Shuttle Control Panel

This section allows the shuttle operator to manage and simulate shuttle operations.

The operator can enter a student’s SUID and select an address (either Stop 1 or Stop 2) from the dropdown menu.
Clicking the “Board Passenger” button registers the student as boarded onto the shuttle.
The “Start Location Simulation” button begins the simulation of the shuttle’s movement by sending its location to the server every one second, using a loop-based mechanism.
To ensure accurate ETAs during transit, please click the “Start Location Simulation” button before using the application.

Below the buttons, the current Shuttle Status, including ETA and boarding instructions, is displayed in real time.
