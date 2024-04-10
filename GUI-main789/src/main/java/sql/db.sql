-- Table for users
CREATE TABLE Users (
                       UserNameDB VARCHAR(255) NOT NULL PRIMARY KEY,
                       UserEmailDB VARCHAR(255) NOT NULL,
);

-- Table for Responsible users
CREATE TABLE Responsible (
                             UserNameDB VARCHAR(255) NOT NULL PRIMARY KEY,
                             UserEmailDB VARCHAR(255) NOT NULL,
                             Finance DECIMAL(10, 2) NOT NULL,
                             FOREIGN KEY (UserNameDB) REFERENCES Users(UserNameDB)
);

-- Table for Coach users
CREATE TABLE Coach (
                       UserNameDB VARCHAR(255) NOT NULL PRIMARY KEY,
                       UserEmailDB VARCHAR(255) NOT NULL,
                       Salary DECIMAL(10, 2) NOT NULL,
                       Sport VARCHAR(100) NOT NULL,
                       Schedule DATETIME NOT NULL,
                       FOREIGN KEY (UserNameDB) REFERENCES Users(UserNameDB)
);

-- Table for Member (to store attributes specific to members)
CREATE TABLE Member (
                        UserNameDB VARCHAR(255) NOT NULL PRIMARY KEY,
                        UserEmailDB VARCHAR(255) NOT NULL,
                        Membership VARCHAR(100) NOT NULL,
                        Age INT NOT NULL,
                        Height DECIMAL(5, 2) NOT NULL,
                        Weight DECIMAL(5, 2) NOT NULL,
                        Schedule DATETIME NOT NULL,
                        FOREIGN KEY (UserNameDB) REFERENCES Users(UserNameDB)
);

-- Table for Performance (to store performance data)
CREATE TABLE Performance (
                             PerformanceID INT PRIMARY KEY AUTO_INCREMENT,
                             UserNameDB VARCHAR(255) NOT NULL PRIMARY KEY,
                             UserEmailDB VARCHAR(255) NOT NULL,
                             RunsScored DECIMAL(10, 2) NOT NULL,
                             BallsFaced DECIMAL(10, 2) NOT NULL,
                             Fours DECIMAL(10, 2) NOT NULL,
                             Sixes DECIMAL(10, 2) NOT NULL,
                             WicketsTaken DECIMAL(10, 2) NOT NULL,
                             BallsBowled DECIMAL(10, 2) NOT NULL,
                             RunsGave DECIMAL(10, 2) NOT NULL,
                             FOREIGN KEY (UserNameDB) REFERENCES Users(UserNameDB)
);

-- Create the training_sessions table
CREATE TABLE training_sessions (
                                   session_id INT PRIMARY KEY,
                                   session_name VARCHAR(50),
                                   session_date DATE,
                                   trainer_name VARCHAR(50),
                                   duration_minutes INT,
                                   participants INT
);

-- Insert sample data into training_sessions table
INSERT INTO training_sessions (session_id, session_name, session_date, trainer_name, duration_minutes, participants)
VALUES
    (1, 'Strength Training', '2024-04-01', 'John Doe', 60, 10),
    (2, 'Yoga Class', '2024-04-02', 'Jane Smith', 90, 15),
    (3, 'Cardio Workout', '2024-04-03', 'Mike Johnson', 45, 12),
    (4, 'CrossFit', '2024-04-04', 'Emily Brown', 75, 8);

-- Create the finance table
CREATE TABLE finance (
                         finance_id INT PRIMARY KEY,
                         transaction_date DATE,
                         amount DECIMAL(10, 2),
                         description VARCHAR(100)
);

-- Insert sample data into finance table
INSERT INTO finance (finance_id, transaction_date, amount, description)
VALUES
    (1, '2024-04-01', 500.00, 'Membership fees'),
    (2, '2024-04-02', 200.00, 'Personal training session'),
    (3, '2024-04-03', 100.00, 'Smoothie bar sales'),
    (4, '2024-04-04', 300.00, 'Merchandise sales');


-- Insert more sample data into training_sessions table
INSERT INTO training_sessions (session_id, session_name, session_date, trainer_name, duration_minutes, participants)
VALUES
    (5, 'Pilates', '2024-04-05', 'Sarah Adams', 60, 10),
    (6, 'Zumba', '2024-04-06', 'Michael White', 45, 20),
    (7, 'HIIT', '2024-04-07', 'Jessica Lee', 60, 15),
    (8, 'Spin Class', '2024-04-08', 'Alex Johnson', 45, 18);

-- Insert more sample data into finance table
INSERT INTO finance (finance_id, transaction_date, amount, description)
VALUES
    (5, '2024-04-05', 150.00, 'Membership fees'),
    (6, '2024-04-06', 80.00, 'Smoothie bar sales'),
    (7, '2024-04-07', 250.00, 'Personal training session'),
    (8, '2024-04-08', 400.00, 'Merchandise sales');


CREATE TABLE training_schedule (
                                   event_id INT PRIMARY KEY,
                                   event_name VARCHAR(100),
                                   event_date DATE,
                                   start_time TIME,
                                   end_time TIME,
                                   location VARCHAR(100)
);

-- Insert sample data into training_schedule table
INSERT INTO training_schedule (event_id, event_name, event_date, start_time, end_time, location)
VALUES
    (1, 'Strength Training', '2024-04-10', '10:00:00', '11:00:00', 'Gym Room A'),
    (2, 'Yoga Class', '2024-04-11', '09:00:00', '10:30:00', 'Yoga Studio'),
    (3, 'Cardio Workout', '2024-04-12', '14:00:00', '15:00:00', 'Gym Room B');


-- Insert more sample data into training_schedule table
INSERT INTO training_schedule (event_id, event_name, event_date, start_time, end_time, location)
VALUES
    (4, 'Pilates', '2024-04-15', '08:00:00', '09:00:00', 'Studio A'),
    (5, 'CrossFit', '2024-04-16', '16:00:00', '17:00:00', 'Gym Room B'),
    (6, 'Yoga Class', '2024-04-17', '09:30:00', '10:30:00', 'Yoga Studio'),
    (7, 'Spin Class', '2024-04-18', '18:00:00', '19:00:00', 'Spin Room'),
    (8, 'HIIT', '2024-04-19', '17:00:00', '18:00:00', 'Gym Room A'),
    (9, 'Zumba', '2024-04-20', '10:00:00', '11:00:00', 'Main Hall'),
    (10, 'Strength Training', '2024-04-21', '14:00:00', '15:00:00', 'Gym Room C'),
    (11, 'Yoga Class', '2024-04-22', '07:30:00', '08:30:00', 'Yoga Studio'),
    (12, 'Pilates', '2024-04-23', '17:30:00', '18:30:00', 'Studio A'),
    (13, 'CrossFit', '2024-04-24', '16:30:00', '17:30:00', 'Gym Room B'),
    (14, 'Zumba', '2024-04-25', '10:30:00', '11:30:00', 'Main Hall'),
    (15, 'HIIT', '2024-04-26', '18:30:00', '19:30:00', 'Gym Room A'),
    (16, 'Strength Training', '2024-04-27', '14:30:00', '15:30:00', 'Gym Room C'),
    (17, 'Yoga Class', '2024-04-28', '07:00:00', '08:00:00', 'Yoga Studio'),
    (18, 'Spin Class', '2024-04-29', '19:00:00', '20:00:00', 'Spin Room'),
    (19, 'Zumba', '2024-04-30', '11:00:00', '12:00:00', 'Main Hall'),
    (20, 'Pilates', '2024-05-01', '08:30:00', '09:30:00', 'Studio A'),
    (21, 'CrossFit', '2024-05-02', '17:30:00', '18:30:00', 'Gym Room B'),
    (22, 'Yoga Class', '2024-05-03', '09:00:00', '10:00:00', 'Yoga Studio'),
    (23, 'Spin Class', '2024-05-04', '18:00:00', '19:00:00', 'Spin Room'),
    (24, 'HIIT', '2024-05-05', '17:00:00', '18:00:00', 'Gym Room A'),
    (25, 'Strength Training', '2024-05-06', '14:00:00', '15:00:00', 'Gym Room C'),
    (26, 'Yoga Class', '2024-05-07', '08:00:00', '09:00:00', 'Yoga Studio'),
    (27, 'Pilates', '2024-05-08', '17:00:00', '18:00:00', 'Studio A'),
    (28, 'CrossFit', '2024-05-09', '16:00:00', '17:00:00', 'Gym Room B'),
    (29, 'Zumba', '2024-05-10', '10:00:00', '11:00:00', 'Main Hall'),
    (30, 'HIIT', '2024-05-11', '18:00:00', '19:00:00', 'Gym Room A'),
    (31, 'Strength Training', '2024-05-12', '15:00:00', '16:00:00', 'Gym Room C'),
    (32, 'Yoga Class', '2024-05-13', '07:30:00', '08:30:00', 'Yoga Studio'),
    (33, 'Spin Class', '2024-05-14', '19:00:00', '20:00:00', 'Spin Room'),
    (34, 'Zumba', '2024-05-15', '11:00:00', '12:00:00', 'Main Hall'),
    (35, 'Pilates', '2024-05-16', '08:30:00', '09:30:00', 'Studio A');


INSERT INTO Users (UserNameDB, UserEmailDB) VALUES
                                                ('john_doe', 'john@example.com'),
                                                ('jane_doe', 'jane@example.com'),
                                                ('alice_smith', 'alice@example.com'),
                                                ('bob_johnson', 'bob@example.com'),
                                                ('emma_wilson', 'emma@example.com'),
                                                ('sam_robinson', 'sam@example.com'),
                                                ('sarah_carter', 'sarah@example.com'),
                                                ('michael_smith', 'michael@example.com'),
                                                ('olivia_jones', 'olivia@example.com'),
                                                ('william_taylor', 'william@example.com');


-- Insert data into the Users table
INSERT INTO Users (UserNameDB, UserEmailDB) VALUES
                                                ('coach1', 'coach1@example.com'),
                                                ('coach2', 'coach2@example.com'),
                                                ('coach3', 'coach3@example.com');

-- Insert data into the Coach table
INSERT INTO Coach (UserNameDB, UserEmailDB, Salary, Sport, Schedule) VALUES
                                                                         ('coach1', 'coach1@example.com', 5000.00, 'Football', '2024-04-08 10:00:00'),
                                                                         ('coach2', 'coach2@example.com', 4500.00, 'Basketball', '2024-04-09 11:00:00'),
                                                                         ('coach3', 'coach3@example.com', 5500.00, 'Tennis', '2024-04-10 09:00:00');

-- Add more users to the Users table
INSERT INTO Users (UserNameDB, UserEmailDB) VALUES
                                                ('laura_smith', 'laura@example.com'),
                                                ('peter_jones', 'peter@example.com'),
                                                ('natalie_clark', 'natalie@example.com'),
                                                ('alexander_white', 'alexander@example.com'),
                                                ('lily_thompson', 'lily@example.com'),
                                                ('daniel_brown', 'daniel@example.com'),
                                                ('hannah_davis', 'hannah@example.com'),
                                                ('jacob_miller', 'jacob@example.com'),
                                                ('ava_anderson', 'ava@example.com'),
                                                ('noah_wilson', 'noah@example.com');

-- Create members based on the new users in the Users table
INSERT INTO Member (UserNameDB, UserEmailDB, Membership, Age, Height, Weight, Schedule)
VALUES
    ('laura_smith', 'laura@example.com', 'Gold', 29, 175.0, 65.0, '2024-04-18 12:00:00'),
    ('peter_jones', 'peter@example.com', 'Bronze', 31, 180.0, 75.0, '2024-04-19 12:00:00'),
    ('natalie_clark', 'natalie@example.com', 'Silver', 27, 165.0, 60.0, '2024-04-20 12:00:00'),
    ('alexander_white', 'alexander@example.com', 'Platinum', 30, 170.0, 70.0, '2024-04-21 12:00:00'),
    ('lily_thompson', 'lily@example.com', 'Bronze', 32, 168.0, 62.0, '2024-04-22 12:00:00'),
    ('daniel_brown', 'daniel@example.com', 'Silver', 28, 172.0, 68.0, '2024-04-23 12:00:00'),
    ('hannah_davis', 'hannah@example.com', 'Gold', 30, 175.0, 70.0, '2024-04-24 12:00:00'),
    ('jacob_miller', 'jacob@example.com', 'Bronze', 29, 178.0, 72.0, '2024-04-25 12:00:00'),
    ('ava_anderson', 'ava@example.com', 'Silver', 31, 170.0, 65.0, '2024-04-26 12:00:00'),
    ('noah_wilson', 'noah@example.com', 'Gold', 33, 182.0, 78.0, '2024-04-27 12:00:00');



