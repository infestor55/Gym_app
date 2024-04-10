# Sports Club Management System

## Overview
This project is a Sports Club Management System designed to manage users, coaches, members, and their performance data. It provides CRUD (Create, Read, Update, Delete) operations for each entity and allows for efficient management of the club's resources.

## Features
- **Users Management:** Add, delete, update, and view users of the system.
- **Coaches Management:** Manage coaches, including their salary, sport, and schedule.
- **Members Management:** Manage club members, including membership details, age, height, weight, and schedule.
- **Performance Tracking:** Track the performance of members, including runs scored, balls faced, fours, sixes, wickets taken, balls bowled, and runs given.
- **Database Integration:** Data is stored in a MySQL database, allowing for persistent storage and retrieval of information.

## Technologies Used
- Java
- MySQL
- JDBC

## Installation
1. Clone the repository: `git clone <[repository-url](https://github.com/Larousse2001/GUI)>`
2. Set up the MySQL database by running the provided SQL scripts.
3. Import the project into your preferred Java IDE.
4. Update the database connection settings in the service classes (`ServiceUsers`, `ServiceCoach`, `ServiceMember`, `ServicePerformance`, `ServiceResponsible`).
5. Build and run the project.

## Usage
- Use the provided controller classes (`UserController`, `CoachController`, `MemberController`, `PerformanceController`, `ResponsibleController`) to interact with the system and perform CRUD operations on users, coaches, members, and performance data.
- Modify the models and services as needed to customize the system for your specific requirements.

## Contributors
- Larousse2001 (https://github.com/Larousse2001)

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
