# Flight-Ticket-Booking
The Flight Ticket Booking System is a Java program that allows users to book flight tickets for multiple flights with options for Business and Economy class seating, meal ordering, and cancellations. This program is designed to handle basic ticket booking operations for an airline.

## Assumptions
1.	Flights are identified by unique names like "Flight-A112-Chennai-Mumbai" and are stored as separate files in the "flight_details" folder.
2.	Flight seats are categorized into Economy and Business Class, with the number of seating arrangements and seats specified in each file.
3.	Base prices for Economy Class and Business Class tickets are INR 1000 and INR 2000, respectively.
4.	Aisle and Window seats cost INR 100 more.
5.	A unique auto-generated BookingID is assigned to each booking.
6.	After each successful booking, the ticket price increases by INR 100 for Economy Class and INR 200 for Business Class on successive bookings.
7.	Passengers have the option to book meals at an additional cost of INR 200 per passenger.
8.	Cancellation is allowed for any given BookingID for a flat cancellation fee of INR 200 per seat.

# Setup Instructions
## Prerequisites
•	 Java JDK (version 8 or higher) is installed on your system.

•	 Set up the "flight_details" folder:

•	 Create a new folder named "flight_details" at the root directory of the project.

•	 Inside the "flight_details" folder, create individual files for each flight with the following format:

Source: Chennai

Destination: Mumbai

Business: {2, 3, 2}, 12

Economy: {3, 4, 4}, 20

•	The flight details are read from individual files in the "flight_details" folder. Ensure that you have set up the folder and files correctly for the program to work properly.


## How to Compile
1.	Download the project from the repository.
2.	Open a terminal or command prompt and navigate to the project directory.

## Compiling the Code
Run the following command to compile the Java code:
javac FlightTicketBookingSystem.java 

## How to Run
After compiling the code, run the FlightTicketBookingSystem class using the following command:
java FlightTicketBookingSystem 

## Usage
1.	Once the program is running, follow the on-screen menu options to interact with the Flight Ticket Booking System.
2.	Choose options to list flight details, search flights, handle booking, handle cancellation, print available seats, print seats with meals ordered, and print booking summaries.
3.	The program will read flight details from the "flight_details" folder and display information accordingly.

