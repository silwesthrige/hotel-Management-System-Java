import java.util.ArrayList;
import java.util.Scanner;

// Base class
abstract class Room {
    private String roomType;
    private double rate;

    // Constructor
    public Room(String roomType, double rate) {
        this.roomType = roomType;
        this.rate = rate;
    }

    // Getters
    public String getRoomType() {
        return roomType;
    }

    public double getRate() {
        return rate;
    }

    // Abstract method 
    public abstract double calBasePrice(int numberOfNights);
}

// SingleRoom class
class SingleRoom extends Room {
    public SingleRoom(String roomType, double rate) {
        super(roomType, rate);
    }

    
    public double calBasePrice(int numberOfNights) {
        return getRate() * numberOfNights;
    }
}

// DoubleRoom class
class DoubleRoom extends Room {
    public DoubleRoom(String roomType, double rate) {
        super(roomType, rate);
    }

    
    public double calBasePrice(int numberOfNights) {
        return getRate() * numberOfNights;
    }
}

// AC/Non-AC class
class AcOrNonAc {
    private static final double AC_RATE = 0.3; // 30% additional for AC
    private Room room;

    public AcOrNonAc(Room room) {
        this.room = room;
    }

    public double calAcAddition(int numberOfNights) {
        double basePrice = room.calBasePrice(numberOfNights);
        return basePrice + (basePrice * AC_RATE);
    }

    public double calNonAcPrice(int numberOfNights) {
        return room.calBasePrice(numberOfNights);
    }
}

// store booking details
class Booking {
    private String name;
    private String roomType;
    private int numberOfNights;
    private String acPreference;
    private double totalCost;

    // Constructor
    public Booking(String name, String roomType, int numberOfNights, String acPreference, double totalCost) {
        this.name = name;
        this.roomType = roomType;
        this.numberOfNights = numberOfNights;
        this.acPreference = acPreference;
        this.totalCost = totalCost;
    }

    
    public String toString() {
        return "Name: " + name + ", Room Type: " + roomType + ", Nights: " + numberOfNights + 
               ", AC: " + acPreference + ", Total Cost: " + totalCost;
    }
}

// Driver class
public class HotelSystem {
    public static void main(String[] args) {
        ArrayList<Booking> bookings = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        System.out.print("Enter Your Name: ");
        String name = sc.nextLine();

        System.out.println("\nWelcome, " + name + "!\n");

        while (!exit) {
            System.out.println("Select Your Choice:");
            System.out.println("01. Rooms Price List");
            System.out.println("02. Book a Room");
            System.out.println("03. Show Booked Room List");
            System.out.println("04. Cancel a Booking");
            System.out.println("05. Exit");
            System.out.println("-------------");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Room Price List ---");
                    System.out.println("Single Room: 3000 per night");
                    System.out.println("Double Room: 4000 per night");
                    System.out.println("-----------------------\n");
                    break;

                case 2:
                    System.out.println("\n--- Room Booking ---");
                    System.out.println("Enter Room Type (1 for Single, 2 for Double): ");
                    int roomType = sc.nextInt();

                    System.out.println("Enter Number of Nights: ");
                    int numberOfNights = sc.nextInt();

                    System.out.println("Do you want AC? (YES or NO): ");
                    String acPreference = sc.next();

                    Room room;
                    if (roomType == 1) {
                        room = new SingleRoom("Single Room", 2500.0);
                    } else if (roomType == 2) {
                        room = new DoubleRoom("Double Room", 3500.0);
                    } else {
                        System.out.println("Invalid Room Type! Returning to Main Menu.\n");
                        break;
                    }

                    AcOrNonAc bookingHandler = new AcOrNonAc(room);
                    double finalPrice;

                    if (acPreference.equalsIgnoreCase("YES")) {
                        finalPrice = bookingHandler.calAcAddition(numberOfNights);
                    } else if (acPreference.equalsIgnoreCase("NO")) {
                        finalPrice = bookingHandler.calNonAcPrice(numberOfNights);
                    } else {
                        System.out.println("Invalid AC Preference! Returning to Main Menu.\n");
                        break;
                    }

                    Booking booking = new Booking(name, room.getRoomType(), numberOfNights, acPreference, finalPrice);
                    bookings.add(booking); //arrayList add

                    System.out.println("\nBooking Summary:");
                    System.out.println(booking.toString());
                    System.out.println("\nBooking Confirmed. Returning to the main menu.\n");
                    break;

                case 3:
                    showBookedRoomList(bookings);
                    break;

                case 4:
                    cancelBooking(bookings, sc);
                    break;

                case 5:
                    exit = true;
                    System.out.println("Thank You! Have a great day!");
                    break;

                default:
                    System.out.println("Invalid Choice! Please try again.\n");
                    break;
            }
        }

        
    }

    // Show Booking Details
    public static void showBookedRoomList(ArrayList<Booking> bookings) {
        System.out.println("\n--- Booked Room List ---");
        if (bookings.isEmpty()) {
            System.out.println("No rooms have been booked yet.\n");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking.toString());
            }
        }
        System.out.println("-------------------------\n");
    }

    // Cancel Booking
    public static void cancelBooking(ArrayList<Booking> bookings, Scanner scanner) {
        System.out.println("\n--- Cancel a Booking ---");
        if (bookings.isEmpty()) {
            System.out.println("No bookings available to cancel.\n");
        } else {
            for (int i = 0; i < bookings.size(); i++) {
                System.out.println((i + 1) + ". " + bookings.get(i).toString());
            }
            System.out.println("Select the booking number to cancel: ");
            int bookingNumber = scanner.nextInt();

            if (bookingNumber > 0 && bookingNumber <= bookings.size()) {
                bookings.remove(bookingNumber - 1);
                System.out.println("Booking cancelled successfully.\n");
            } else {
                System.out.println("Invalid booking number.\n");
            }
        }
    }
}
