import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// 1. User registration and authentication
class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private ServicePlan servicePlan;

    // Constructor
    public User(String email, String password, String firstName, String lastName, String address, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public ServicePlan getServicePlan() { return servicePlan; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setServicePlan(ServicePlan servicePlan) { this.servicePlan = servicePlan; }
}

class AuthenticationService {
    public static boolean authenticateUser(String email, String password) {
        User user = UserService.getUserByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
}

// 2. User account management
class UserService {
    private static List<User> users = new ArrayList<>();

    public static User createUser(String email, String password, String firstName, String lastName, String address, String phoneNumber) {
        User user = new User(email, password, firstName, lastName, address, phoneNumber);
        users.add(user);
        return user;
    }

    public static void updateUser(User user) {
        // No action needed in memory-based model
    }

    public static void deleteUser(User user) {
        users.remove(user);
    }

    public static User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }
}

class AccountController {
    public void updateAccountDetails(String email, String firstName, String lastName, String address, String phoneNumber) {
        User user = UserService.getUserByEmail(email);
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAddress(address);
            user.setPhoneNumber(phoneNumber);
            UserService.updateUser(user);
        }
    }
}

// 3. Service plan management
class ServicePlan {
    private String name;
    private double price;
    private double dataLimit;
    private int speedLimit;

    public ServicePlan(String name, double price, double dataLimit, int speedLimit) {
        this.name = name;
        this.price = price;
        this.dataLimit = dataLimit;
        this.speedLimit = speedLimit;
    }

    // Getters and Setters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public double getDataLimit() { return dataLimit; }
    public int getSpeedLimit() { return speedLimit; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setDataLimit(double dataLimit) { this.dataLimit = dataLimit; }
    public void setSpeedLimit(int speedLimit) { this.speedLimit = speedLimit; }
}

class ServicePlanService {
    private static List<ServicePlan> plans = new ArrayList<>();

    public static void createServicePlan(String name, double price, double dataLimit, int speedLimit) {
        plans.add(new ServicePlan(name, price, dataLimit, speedLimit));
    }

    public static void updateServicePlan(ServicePlan servicePlan) {
        // No specific logic needed in in-memory example
    }

    public static void deleteServicePlan(ServicePlan servicePlan) {
        plans.remove(servicePlan);
    }

    public static List<ServicePlan> getAllPlans() {
        return plans;
    }
}

// 4. Billing and invoicing
class Invoice {
    private static int counter = 1;
    private int id;
    private User user;
    private double amount;
    private LocalDate date;

    public Invoice(User user, double amount) {
        this.id = counter++;
        this.user = user;
        this.amount = amount;
        this.date = LocalDate.now();
    }

    // Getters
    public int getId() { return id; }
    public User getUser() { return user; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }
}

class InvoiceService {
    private static List<Invoice> invoices = new ArrayList<>();

    public static void generateInvoice(User user) {
        if (user.getServicePlan() != null) {
            Invoice invoice = new Invoice(user, user.getServicePlan().getPrice());
            invoices.add(invoice);
        }
    }

    public static List<Invoice> getInvoicesForUser(User user) {
        List<Invoice> result = new ArrayList<>();
        for (Invoice invoice : invoices) {
            if (invoice.getUser().equals(user)) {
                result.add(invoice);
            }
        }
        return result;
    }
}

// 5. Customer support
class SupportTicket {
    private static int counter = 1;
    private int id;
    private User user;
    private String issue;
    private LocalDate date;
    private String status;

    public SupportTicket(User user, String issue) {
        this.id = counter++;
        this.user = user;
        this.issue = issue;
        this.date = LocalDate.now();
        this.status = "Open";
    }

    // Getters and setters
    public int getId() { return id; }
    public User getUser() { return user; }
    public String getIssue() { return issue; }
    public LocalDate getDate() { return date; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}

class SupportTicketService {
    private static List<SupportTicket> tickets = new ArrayList<>();

    public static void createSupportTicket(User user, String issue) {
        tickets.add(new SupportTicket(user, issue));
    }

    public static void updateSupportTicketStatus(SupportTicket ticket, String status) {
        ticket.setStatus(status);
    }

    public static List<SupportTicket> getSupportTicketsForUser(User user) {
        List<SupportTicket> result = new ArrayList<>();
        for (SupportTicket ticket : tickets) {
            if (ticket.getUser().equals(user)) {
                result.add(ticket);
            }
        }
        return result;
    }
}
