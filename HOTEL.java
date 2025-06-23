import java.util.*;
import java.io.*;

class Room {
    String type;
    double price;

    Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}

class Booking {
    String name;
    Room room;

    Booking(String name, Room room) {
        this.name = name;
        this.room = room;
    }

    public String getDetails() {
        return name + " booked a " + room.getType() + " room for ₹" + room.getPrice();
    }
}

public class HotelReservationSystem {
    static List<Room> rooms = Arrays.asList(
        new Room("Standard", 3000),
        new Room("Deluxe", 5000),
        new Room("Suite", 8000)
    );

    static final String FILE = "bookings.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. View Rooms\n2. Book Room\n3. Cancel Booking\n4. View Bookings\n5. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    viewRooms();
                    break;
                case 2:
                    bookRoom(sc);
                    break;
                case 3:
                    cancelBooking(sc);
                    break;
                case 4:
                    viewBookings();
                    break;
                case 5:
                    System.out.println("Thanks for using the system!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void viewRooms() {
        System.out.println("\nAvailable Rooms:");
        for (int i = 0; i < rooms.size(); i++) {
            Room r = rooms.get(i);
            System.out.println((i + 1) + ". " + r.getType() + " - ₹" + r.getPrice());
        }
    }

    static void bookRoom(Scanner sc) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        viewRooms();
        System.out.print("Select room (1-3): ");
        int roomChoice = sc.nextInt();
        sc.nextLine();  // consume newline

        if (roomChoice < 1 || roomChoice > 3) {
            System.out.println("Invalid room type.");
            return;
        }

        Room selected = rooms.get(roomChoice - 1);
        Booking booking = new Booking(name, selected);

        System.out.println("Payment simulated: ₹" + selected.getPrice() + " paid!");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE, true))) {
            writer.write(booking.getDetails());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving booking.");
        }

        System.out.println("✅ Booking successful!");
    }

    static void cancelBooking(Scanner sc) {
        System.out.print("Enter your name to cancel booking: ");
        String name = sc.nextLine();

        File input = new File(FILE);
        File temp = new File("temp.txt");
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(input));
             BufferedWriter writer = new BufferedWriter(new FileWriter(temp))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(name + " ")) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    found = true;
                }
            }

        } catch (IOException e) {
            System.out.println("Error canceling booking.");
            return;
        }

        if (!found) {
            System.out.println("No booking found under that name.");
            temp.delete();
            return;
        }

        input.delete();
        temp.renameTo(input);
        System.out.println("❌ Booking cancelled.");
    }

    static void viewBookings() {
        System.out.println("\nBooking Records:");
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            boolean any = false;
            while ((line = reader.readLine()) != null) {
                System.out.println("- " + line);
                any = true;
            }
            if (!any) System.out.println("No bookings yet.");
        } catch (IOException e) {
            System.out.println("Error reading bookings.");
        }
    }
}