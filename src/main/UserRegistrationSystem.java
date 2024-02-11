package main;


import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
class User {
    private String username;
    private String password;
    private String email;
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    @Override
    public String toString() {
        return "Username: " + username + ", Email: " + email;
    }
}
class UserDatabase {
    private Set<User> users;
    public UserDatabase() {
        users = new HashSet<>();
    }
    public void addUser(User user) {
        users.add(user);
    }
    public void removeUser(String username) {
        User userToRemove = null;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                userToRemove = user;
                break;
            }
        }
        if (userToRemove != null) {
            users.remove(userToRemove);
        }
    }
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public Set<User> getAllUsers() {
        return users;
    }
    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("C:\\Users\\Vijap\\book_records.txt"))) {
            out.writeObject(users);
        } catch (IOException e) {
            System.err.println("Error: Unable to save user data to the file.");
        }
    }
    public void loadFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("C:\\Users\\Vijap\\book_records.txt"))) {
            Object obj = in.readObject();
            if (obj instanceof Set) {
                users = (Set<User>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error: Unable to load user data from the file.");
        }
    }
}
public class UserRegistrationSystem {
    public static void main(String[] args) {
        UserDatabase database = new UserDatabase();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nUser Registration System Menu:");
            System.out.println("1. Register a new user");
            System.out.println("2. View user details by username");
            System.out.println("3. View all registered users");
            System.out.println("4. Remove a user account");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    User user = new User(username, password, email);
                    database.addUser(user);
                    System.out.println("User registered successfully.");
                    break;
                case 2:
                    System.out.print("Enter username to view details: ");
                    String viewUsername = scanner.nextLine();
                    User viewUser = database.getUserByUsername(viewUsername);
                    if (viewUser != null) {
                        System.out.println(viewUser);
                    } else {
                        System.out.println("User not found.");
                    }
                    break;
                case 3:
                    Set<User> allUsers = database.getAllUsers();
                    if (!allUsers.isEmpty()) {
                        System.out.println("All Registered Users:");
                        for (User u : allUsers) {
                            System.out.println(u);
                        }
                    } else {
                        System.out.println("No users registered yet.");
                    }
                    break;
                case 4:
                    System.out.print("Enter username to remove: ");
                    String removeUsername = scanner.nextLine();
                    database.removeUser(removeUsername);
                    System.out.println("User removed successfully.");
                    break;
                case 5:
                    System.out.println("Thank you for using the User Registration System!");
                    database.saveToFile("user_database.ser");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
