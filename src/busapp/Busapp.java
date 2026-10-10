package busapp;

import java.util.Scanner;

// ---------- Passenger Class ----------
class Passenger {
    private int ticketId;
    private String name;
    private String destination;

    public Passenger(int ticketId, String name, String destination) {
        this.ticketId = ticketId;
        this.name = name;
        this.destination = destination;
    }

    public int getTicketId() { return ticketId; }
    public String getName() { return name; }
    public String getDestination() { return destination; }

    @Override
    public String toString() {
        return String.format("Ticket ID: %-5d | Name: %-15s | Destination: %-15s",
                ticketId, name, destination);
    }
}

// ---------- Queue Implementation (from scratch) ----------
class PassengerQueue {
    private Passenger[] queue;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public PassengerQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new Passenger[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public boolean isEmpty() { return size == 0; }
    public boolean isFull() { return size == capacity; }
    public int getSize() { return size; }

    // Enqueue: add passenger to the rear
    public boolean enqueue(Passenger p) {
        if (isFull()) {
            System.out.println("Queue is full! Cannot add more passengers.");
            return false;
        }
        rear = (rear + 1) % capacity;
        queue[rear] = p;
        size++;
        return true;
    }

    // Dequeue: remove passenger from the front (boarding)
    public Passenger dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty! No passengers to board.");
            return null;
        }
        Passenger p = queue[front];
        front = (front + 1) % capacity;
        size--;
        return p;
    }

    // Peek: view the front passenger without removing
    public Passenger peek() {
        if (isEmpty()) return null;
        return queue[front];
    }

    // Get passenger at a specific index (0 = front)
    public Passenger getAt(int index) {
        if (index < 0 || index >= size) return null;
        return queue[(front + index) % capacity];
    }

    // Display all passengers in queue order
    public void display() {
        if (isEmpty()) {
            System.out.println("The queue is empty.");
            return;
        }
        System.out.println("\n--- Current Passenger Queue ---");
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + ". " + getAt(i));
        }
        System.out.println("Total passengers: " + size);
    }

    // Linear Search by ticket ID
    public int linearSearchByTicketId(int ticketId) {
        for (int i = 0; i < size; i++) {
            if (getAt(i).getTicketId() == ticketId) {
                return i;
            }
        }
        return -1;
    }

    // Linear Search by name (case-insensitive)
    public int linearSearchByName(String name) {
        for (int i = 0; i < size; i++) {
            if (getAt(i).getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    // Bubble Sort by ticket ID (ascending)
    public void bubbleSortByTicketId() {
        if (size <= 1) return;

        Passenger[] temp = new Passenger[size];
        for (int i = 0; i < size; i++) {
            temp[i] = getAt(i);
        }

        for (int i = 0; i < size - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < size - i - 1; j++) {
                if (temp[j].getTicketId() > temp[j + 1].getTicketId()) {
                    Passenger t = temp[j];
                    temp[j] = temp[j + 1];
                    temp[j + 1] = t;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }

        for (int i = 0; i < size; i++) {
            queue[(front + i) % capacity] = temp[i];
        }
        System.out.println("Queue sorted by Ticket ID (ascending).");
    }

    // Bubble Sort by name (alphabetical)
    public void bubbleSortByName() {
        if (size <= 1) return;

        Passenger[] temp = new Passenger[size];
        for (int i = 0; i < size; i++) {
            temp[i] = getAt(i);
        }

        for (int i = 0; i < size - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < size - i - 1; j++) {
                if (temp[j].getName().compareToIgnoreCase(temp[j + 1].getName()) > 0) {
                    Passenger t = temp[j];
                    temp[j] = temp[j + 1];
                    temp[j + 1] = t;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }

        for (int i = 0; i < size; i++) {
            queue[(front + i) % capacity] = temp[i];
        }
        System.out.println("Queue sorted by Name (alphabetical).");
    }
}

// ---------- Main Application ----------
public class Busapp {

    private static PassengerQueue queue = new PassengerQueue(100);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("   BUS PASSENGER QUEUE MANAGEMENT SYSTEM");
        System.out.println("==============================================");

        // Pre-load some sample passengers for demonstration
        queue.enqueue(new Passenger(105, "Alice", "Downtown"));
        queue.enqueue(new Passenger(102, "Bob", "Airport"));
        queue.enqueue(new Passenger(108, "Charlie", "Mall"));
        queue.enqueue(new Passenger(101, "Diana", "University"));
        queue.enqueue(new Passenger(107, "Ethan", "Hospital"));

        int choice;
        do {
            printMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: addPassenger(); break;
                case 2: boardPassenger(); break;
                case 3: searchPassenger(); break;
                case 4: sortPassengers(); break;
                case 5: queue.display(); break;
                case 6: peekFront(); break;
                case 7: demonstrateQueue(); break;
                case 0: System.out.println("Exiting system. Goodbye!"); break;
                default: System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n---------------- MENU ----------------");
        System.out.println("1. Add Passenger (Enqueue)");
        System.out.println("2. Board Passenger (Dequeue)");
        System.out.println("3. Search Passenger");
        System.out.println("4. Sort Passengers");
        System.out.println("5. Display Queue");
        System.out.println("6. Peek Front Passenger");
        System.out.println("7. Demonstrate Queue Operations");
        System.out.println("0. Exit");
        System.out.println("--------------------------------------");
    }

    private static void addPassenger() {
        int id = readInt("Enter Ticket ID: ");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Destination: ");
        String dest = scanner.nextLine().trim();

        Passenger p = new Passenger(id, name, dest);
        if (queue.enqueue(p)) {
            System.out.println("Passenger added to queue: " + p);
        }
    }

    private static void boardPassenger() {
        Passenger p = queue.dequeue();
        if (p != null) {
            System.out.println("Passenger boarded: " + p);
        }
    }

    private static void searchPassenger() {
        System.out.println("\nSearch by:");
        System.out.println("1. Ticket ID");
        System.out.println("2. Name");
        int opt = readInt("Enter option: ");

        if (opt == 1) {
            int id = readInt("Enter Ticket ID to search: ");
            int pos = queue.linearSearchByTicketId(id);
            if (pos == -1) {
                System.out.println("Passenger with Ticket ID " + id + " not found.");
            } else {
                System.out.println("Found at queue position " + (pos + 1) + ": " + queue.getAt(pos));
            }
        } else if (opt == 2) {
            System.out.print("Enter Name to search: ");
            String name = scanner.nextLine().trim();
            int pos = queue.linearSearchByName(name);
            if (pos == -1) {
                System.out.println("Passenger with name \"" + name + "\" not found.");
            } else {
                System.out.println("Found at queue position " + (pos + 1) + ": " + queue.getAt(pos));
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    private static void sortPassengers() {
        System.out.println("\nSort by:");
        System.out.println("1. Ticket ID");
        System.out.println("2. Name");
        int opt = readInt("Enter option: ");

        if (opt == 1) {
            queue.bubbleSortByTicketId();
        } else if (opt == 2) {
            queue.bubbleSortByName();
        } else {
            System.out.println("Invalid option.");
        }
        queue.display();
    }

    private static void peekFront() {
        Passenger p = queue.peek();
        if (p == null) {
            System.out.println("Queue is empty.");
        } else {
            System.out.println("Next to board: " + p);
        }
    }

    private static void demonstrateQueue() {
        System.out.println("\n--- Queue Demonstration ---");
        System.out.println("A Queue follows FIFO (First In, First Out).");
        System.out.println("Passengers are enqueued at the rear and dequeued from the front.");
        System.out.println("Current front passenger (next to board): " + queue.peek());
        System.out.println("Current queue size: " + queue.getSize());
        System.out.println("Enqueue adds to the back; dequeue removes from the front.");
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int val = Integer.parseInt(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }
}