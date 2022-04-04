USE [master]
GO

DROP DATABASE Ticket_GuruDB
GO

CREATE DATABASE Ticket_GuruDB;
GO

USE Ticket_GuruDB
GO

CREATE TABLE cityName (
    zipCode VARCHAR(255) UNIQUE,
    cityName VARCHAR (85),
    PRIMARY KEY (zipCode)
)

CREATE TABLE userTable (
    id INT IDENTITY(1,1) NOT NULL,
    userName VARCHAR(50),
    loginName VARCHAR(50),
    loginPass VARCHAR(50),
	email VARCHAR(50) UNIQUE,
    zipCode VARCHAR(255),
    userAuth INT,
    PRIMARY KEY (id),
    FOREIGN KEY (zipCode) REFERENCES cityName (zipCode)
)


CREATE TABLE venue (
    id INT IDENTITY(1,1) NOT NULL,
    venueName VARCHAR(255),
    streetName VARCHAR(255),
    venueZipCode VARCHAR (255),
    PRIMARY KEY (id),
    FOREIGN KEY (venueZipCode) REFERENCES cityName (zipCode) 
)

CREATE TABLE events (
    id INT IDENTITY(1,1) NOT NULL,
    eventTitle VARCHAR (255),
    venueID INT,
    description VARCHAR(2048),
    maxSeats INT,
	ticketsSold INT,
    beginAt DATETIME,
    endAt DATETIME,
	colour VARCHAR(255),
	eventimage varbinary(max),
    PRIMARY KEY (id),
    FOREIGN KEY (venueID) REFERENCES venue (id)
)

CREATE TABLE userEvent (
    userID INT, 
    eventID INT,
    FOREIGN KEY (userID) REFERENCES userTable (id),
    FOREIGN KEY (eventID) REFERENCES events (id),
    PRIMARY KEY (userID, eventID)
)

CREATE TABLE images (
	id INT IDENTITY(1,1) NOT NULL,
	imageURL VARCHAR(255) UNIQUE,
	PRIMARY KEY (id)
)

CREATE TABLE eventImages (
	eventid INT IDENTITY(1,1) NOT NULL,
	imageid INT,
	FOREIGN KEY (imageid) REFERENCES images (id),
	FOREIGN KEY (eventid) REFERENCES events (id),
	PRIMARY KEY (eventid, imageid)
)

CREATE TABLE priceEvent (
	id INT IDENTITY(1,1) NOT NULL,
	name VARCHAR(255),
	price INT,
	currency VARCHAR(255),
	eventID INT,
    FOREIGN KEY (eventID) REFERENCES events (id),
    PRIMARY KEY (id)
)