CREATE DATABASE Ticket_GuruDB;

CREATE TABLE UserTable (
    userID INT IDENTITY(1,1) NOT NULL,
    userName VARCHAR(50),
    loginName VARCHAR(50),
    loginPass VARCHAR(50),
    zipCode VARCHAR(50),
    userAuth VARCHAR(255),
    PRIMARY KEY (userID),
    FOREIGN KEY (zipCode) REFERENCES CityName (zipCode)
)

CREATE TABLE CityName (
    zipCode INT UNIQUE,
    cityName VARCHAR (85),
    PRIMARY KEY (zipCode)
)

CREATE TABLE Venue (
    VenueID INT IDENTITY(1,1) NOT NULL,
    locatianName VARCHAR(255),
    StreetName VARCHAR(255),
    venueZipCode INT,
    PRIMARY KEY (VenueID),
    FOREIGN KEY (venueZipCode) REFERENCES CityName (zipCode) 
)

CREATE TABLE Events (
    EventID INT IDENTITY(1,1) NOT NULL,
    eventTitle VARCHAR (255),
    VenueID INT,
    DESCRIPTION VARCHAR(2048),
    maxSeats INT,
    beginAt DATETIME,
    endAt DATETIME,
    price DECIMAL,
    PRIMARY KEY (EventID),
    FOREIGN KEY (VenueID) REFERENCES Venue (VenueID)
)

CREATE TABLE userEvent (
    UserID_FK INT, 
    EventID_FK INT,
    FOREIGN KEY (UserID_FK) REFERENCES UserTable (userID),
    FOREIGN KEY (EventID_FK) REFERENCES Events (EventID),
    PRIMARY KEY (UserID_FK, EventID_FK)
)
