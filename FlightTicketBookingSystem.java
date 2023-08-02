import java.io.*;
import java.util.*;

class Seat {
    int row;
    char column;
    boolean isBooked;
    boolean mealOrdered;

    public Seat(int row, char column) {
        this.row = row;
        this.column = column;
        this.isBooked = false;
        this.mealOrdered = false;
    }

    public void bookSeat() {
        this.isBooked = true;
    }

    public void orderMeal() {
        this.mealOrdered = true;
    }

    public void cancelSeat() {
        this.isBooked = false;
    }

    public boolean isSeatBooked() {
        return isBooked;
    }

    public boolean isMealOrdered() {
        return mealOrdered;
    }

    public String toString() {
        return row + "_" + column;
    }
}

class Flight {
    String flightNumber;
    String source;
    String destination;
    List<List<Seat>> businessClassSeats;
    List<List<Seat>> economyClassSeats;
    int economyBasePrice = 1000;
    int businessBasePrice = 2000;
    int economySurgePrice = 100;
    int businessSurgePrice = 200;
    int totalBookings = 0;

    public Flight(String flightNumber, String source, String destination) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        businessClassSeats = new ArrayList<>();
        economyClassSeats = new ArrayList<>();
    }

    public void setBusinessClassSeats(List<List<Seat>> businessClassSeats) {
        this.businessClassSeats = businessClassSeats;
    }

    public void setEconomyClassSeats(List<List<Seat>> economyClassSeats) {
        this.economyClassSeats = economyClassSeats;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public List<List<Seat>> getBusinessClassSeats() {
        return businessClassSeats;
    }

    public List<List<Seat>> getEconomyClassSeats() {
        return economyClassSeats;
    }

    public int getTotalBookings() {
        return totalBookings;
    }

    public void incrementTotalBookings() {
        totalBookings++;
    }

    public void bookSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            seat.bookSeat();
        }
        incrementTotalBookings();
    }

    public void orderMeals(List<Seat> seats) {
        for (Seat seat : seats) {
            seat.orderMeal();
        }
    }

    public void cancelSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            seat.cancelSeat();
        }
    }

    public int getTotalPrice(List<Seat> seats) {
        int basePrice = isBusinessClass() ? businessBasePrice : economyBasePrice;
        int surgePrice = isBusinessClass() ? businessSurgePrice : economySurgePrice;
        return basePrice + surgePrice * (getTotalBookings() - 1);
    }

    public int getTotalMealCost(List<Seat> seats) {
        int mealCost = 200;
        return mealCost * seats.size();
    }

    public List<Seat> getAllAvailableSeats() {
        List<Seat> allSeats = new ArrayList<>();
        allSeats.addAll(getAvailableBusinessClassSeats());
        allSeats.addAll(getAvailableEconomyClassSeats());
        return allSeats;
    }

    public List<Seat> getAvailableBusinessClassSeats() {
        List<Seat> availableSeats = new ArrayList<>();
        for (List<Seat> row : businessClassSeats) {
            for (Seat seat : row) {
                if (!seat.isSeatBooked()) {
                    availableSeats.add(seat);
                }
            }
        }
        return availableSeats;
    }

    public List<Seat> getAvailableEconomyClassSeats() {
        List<Seat> availableSeats = new ArrayList<>();
        for (List<Seat> row : economyClassSeats) {
            for (Seat seat : row) {
                if (!seat.isSeatBooked()) {
                    availableSeats.add(seat);
                }
            }
        }
        return availableSeats;
    }

    public boolean isBusinessClass() {
        return !businessClassSeats.isEmpty();
    }
}

class Booking {
    static int bookingIdCounter = 1;
    int bookingId;
    Flight flight;
    List<Seat> bookedSeats;
    boolean isBusinessClass;
    int totalPrice;
    int totalMealCost;

    public Booking(Flight flight, List<Seat> bookedSeats, boolean isBusinessClass) {
        this.bookingId = bookingIdCounter++;
        this.flight = flight;
        this.bookedSeats = bookedSeats;
        this.isBusinessClass = isBusinessClass;
        calculateTotalPrice();
    }

    public int getBookingId() {
        return bookingId;
    }

    public List<Seat> getBookedSeats() {
        return bookedSeats;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getTotalMealCost() {
        return totalMealCost;
    }

    public void calculateTotalPrice() {
        totalPrice = flight.getTotalPrice(bookedSeats);
    }

    public void orderMeals() {
        flight.orderMeals(bookedSeats);
        totalMealCost = flight.getTotalMealCost(bookedSeats);
    }

    public void cancelSeats() {
        flight.cancelSeats(bookedSeats);
        totalPrice -= flight.getTotalPrice(bookedSeats);
        totalMealCost = 0;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Booking ID: ").append(bookingId).append("\n");
        sb.append("Flight Number: ").append(flight.getFlightNumber()).append("\n");
        sb.append("Source: ").append(flight.getSource()).append("\n");
        sb.append("Destination: ").append(flight.getDestination()).append("\n");
        sb.append("Total Price: ").append(totalPrice).append("\n");
        sb.append("Total Meal Cost: ").append(totalMealCost).append("\n");
        sb.append("Booked Seats: ");
        for (Seat seat : bookedSeats) {
            sb.append(seat.toString()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length()); // Remove the trailing comma and space
        return sb.toString();
    }
}

public class FlightTicketBookingSystem 
{
    static List<Flight> flights = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
            // Read flight details from the folder and create flight objects.
        loadFlightsFromFolder("flight_details"); 

            // Handle user interactions and perform actions based on user input.
        boolean running = true;
        while (running) {
            printMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    listFlightDetails();
                    break;
                case 2:
                    searchFlights();
                    break;
                case 3:
                    handleBooking();
                    break;
                case 4:
                    handleCancellation();
                    break;
                case 5:
                    printAllAvailableSeats();
                    break;
                case 6:
                    printSeatsWithMealsOrdered();
                    break;
                case 7:
                    printBookingSummary();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        System.out.println("Exiting. Thank you for using the Flight Ticket Booking System!");
    }

private static void loadFlightsFromFolder(String folderPath) {
    File folder = new File(folderPath);
    File[] files = folder.listFiles();
    if (files != null) {
        for (File file : files) {
            if (file.isFile()) {
                try {
                    Scanner scanner = new Scanner(file);
                    String flightNumber = file.getName().split("-")[0];
                    String source = scanner.nextLine().split(": ")[1];
                    String destination = scanner.nextLine().split(": ")[1];

                    Flight flight = new Flight(flightNumber, source, destination);

                    String businessSeatsConfig = scanner.nextLine().split(": ")[1];
                    String economySeatsConfig = scanner.nextLine().split(": ")[1];

                    String[] businessRows = businessSeatsConfig.split("\\{")[1].split("}")[0].split(", ");
                    String[] economyRows = economySeatsConfig.split("\\{")[1].split("}")[0].split(", ");

                    int businessRowsCount = Integer.parseInt(businessRows[0]);
                    int businessSeatsPerRow = Integer.parseInt(businessRows[1]);
                    int businessAisleSeats = Integer.parseInt(businessRows[2]);
                    int businessSeats = Integer.parseInt(businessRows[3]);

                    int economyRowsCount = Integer.parseInt(economyRows[0]);
                    int economySeatsPerRow = Integer.parseInt(economyRows[1]);
                    int economyAisleSeats = Integer.parseInt(economyRows[2]);
                    int economySeats = Integer.parseInt(economyRows[3]);

                    List<List<Seat>> businessSeatsList = generateSeats(businessRowsCount, businessSeatsPerRow,
                            businessAisleSeats, businessSeats);
                    List<List<Seat>> economySeatsList = generateSeats(economyRowsCount, economySeatsPerRow,
                            economyAisleSeats, economySeats);

                    flight.setBusinessClassSeats(businessSeatsList);
                    flight.setEconomyClassSeats(economySeatsList);

                    flights.add(flight);
                    scanner.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

private static List<List<Seat>> generateSeats(int rows, int seatsPerRow, int aisleSeats, int totalSeats) {
    List<List<Seat>> seats = new ArrayList<>();
    char column = 'A';

    for (int i = 1; i <= rows; i++) {
        List<Seat> rowSeats = new ArrayList<>();
        for (int j = 1; j <= seatsPerRow; j++) {
            if (j <= aisleSeats || j > seatsPerRow - aisleSeats) {
                rowSeats.add(new Seat(i, column));
            } else {
                rowSeats.add(new Seat(i, 'M'));
            }
            column++;
        }
        seats.add(rowSeats);
        column = 'A';
    }

    return seats;
}

private static void printMenu() {
    System.out.println("Menu:");
    System.out.println("1. List flight details");
    System.out.println("2. Search flights");
    System.out.println("3. Handle booking");
    System.out.println("4. Handle cancellation");
    System.out.println("5. Print all available seats for each flight");
    System.out.println("6. Print seat numbers with meals ordered for each flight");
    System.out.println("7. Print individual and flight summary for all bookings");
    System.out.println("8. Exit");
    System.out.print("Enter your choice: ");
}

private static int getUserChoice() {
    int choice = scanner.nextInt();
    scanner.nextLine(); // Consume newline character
    return choice;
}

private static void listFlightDetails() {
    System.out.println("Flight Details:");
    for (Flight flight : flights) {
        System.out.println("Flight Number: " + flight.getFlightNumber());
        System.out.println("Source: " + flight.getSource());
        System.out.println("Destination: " + flight.getDestination());
        System.out.println("Business Class Seats: " + flight.getAvailableBusinessClassSeats().size() + " available out of " +
                flight.getBusinessClassSeats().size() * flight.getBusinessClassSeats().get(0).size());
        System.out.println("Economy Class Seats: " + flight.getAvailableEconomyClassSeats().size() + " available out of " +
                flight.getEconomyClassSeats().size() * flight.getEconomyClassSeats().get(0).size());
        System.out.println("--------------------------------");
    }
}

private static void searchFlights() {
    System.out.print("Enter the source location: ");
    String source = scanner.nextLine();
    System.out.print("Enter the destination location: ");
    String destination = scanner.nextLine();

    List<Flight> filteredFlights = new ArrayList<>();
    for (Flight flight : flights) {
        if (flight.getSource().equalsIgnoreCase(source) && flight.getDestination().equalsIgnoreCase(destination)) {
            filteredFlights.add(flight);
        }
    }

    if (filteredFlights.isEmpty()) {
        System.out.println("No flights found between " + source + " and " + destination);
    } else {
        System.out.println("Available Flights:");
        for (Flight flight : filteredFlights) {
            System.out.println("Flight Number: " + flight.getFlightNumber());
            System.out.println("Business Class Seats: " + flight.getAvailableBusinessClassSeats().size() + " available out of " +
                    flight.getBusinessClassSeats().size() * flight.getBusinessClassSeats().get(0).size());
            System.out.println("Economy Class Seats: " + flight.getAvailableEconomyClassSeats().size() + " available out of " +
                    flight.getEconomyClassSeats().size() * flight.getEconomyClassSeats().get(0).size());
            System.out.println("--------------------------------");
        }
    }
}

private static void handleBooking() {
    System.out.print("Enter the flight number: ");
    String flightNumber = scanner.nextLine().trim().toUpperCase(); // Convert to uppercase and trim

    Flight flight = getFlightByNumber(flightNumber);
    if (flight == null) {
        System.out.println("Flight not found with number " + flightNumber);
        return;
    }

    System.out.println("Select Class: ");
    System.out.println("1. Business Class");
    System.out.println("2. Economy Class");
    int classChoice = getUserChoice();
    boolean isBusinessClass = (classChoice == 1);

    List<Seat> availableSeats = isBusinessClass ? flight.getAvailableBusinessClassSeats() : flight.getAvailableEconomyClassSeats();

    System.out.print("Enter the number of seats to book: ");
    int numSeatsToBook = scanner.nextInt();
    scanner.nextLine(); // Consume newline character

    if (numSeatsToBook > availableSeats.size()) {
        System.out.println("Not enough seats available.");
        return;
    }

    List<Seat> bookedSeats = new ArrayList<>();
    System.out.println("Available Seats:");
    for (int i = 0; i < numSeatsToBook; i++) {
        Seat seat = availableSeats.get(i);
        System.out.print(seat.toString() + " ");
        bookedSeats.add(seat);
    }

    System.out.println("\nDo you want to order meals? (Y/N)");
    String mealChoice = scanner.nextLine();
    if (mealChoice.equalsIgnoreCase("Y")) {
        flight.orderMeals(bookedSeats);
    }

    flight.bookSeats(bookedSeats);
    Booking booking = new Booking(flight, bookedSeats, isBusinessClass);
    bookings.add(booking);
    System.out.println("Booking successful!");
    System.out.println("Booking ID: " + booking.getBookingId());
}

private static Flight getFlightByNumber(String flightNumber) {
    for (Flight flight : flights) {
        if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
            return flight;
        }
    }
    return null;
}

private static void handleCancellation() {
    System.out.print("Enter the Booking ID: ");
    int bookingId = scanner.nextInt();
    scanner.nextLine(); // Consume newline character

    Booking booking = getBookingById(bookingId);
    if (booking == null) {
        System.out.println("Booking not found with ID " + bookingId);
        return;
    }

    booking.cancelSeats();
    bookings.remove(booking);
    System.out.println("Cancellation successful!");
}

private static Booking getBookingById(int bookingId) {
    for (Booking booking : bookings) {
        if (booking.getBookingId() == bookingId) {
            return booking;
        }
    }
    return null;
}

private static void printAllAvailableSeats() {
    for (Flight flight : flights) {
        System.out.println("Flight Number: " + flight.getFlightNumber());
        System.out.println("Available Business Class Seats: " + flight.getAvailableBusinessClassSeats());
        System.out.println("Available Economy Class Seats: " + flight.getAvailableEconomyClassSeats());
        System.out.println("--------------------------------");
    }
}

private static void printSeatsWithMealsOrdered() {
    for (Flight flight : flights) {
        System.out.println("Flight Number: " + flight.getFlightNumber());
        System.out.print("Seats with meals ordered: ");
        for (List<Seat> row : flight.getBusinessClassSeats()) {
            for (Seat seat : row) {
                if (seat.isMealOrdered()) {
                    System.out.print(seat.toString() + " ");
                }
            }
        }
        for (List<Seat> row : flight.getEconomyClassSeats()) {
            for (Seat seat : row) {
                if (seat.isMealOrdered()) {
                    System.out.print(seat.toString() + " ");
                }
            }
        }
        System.out.println("\n--------------------------------");
    }
}

private static void printBookingSummary() 
{
    System.out.print("Enter the Booking ID: ");
    int bookingId = scanner.nextInt();
    scanner.nextLine(); // Consume newline character

    Booking booking = getBookingById(bookingId);
    if (booking == null) {
        System.out.println("Booking not found with ID " + bookingId);
        return;
    }

    System.out.println(booking.toString());

}
}





