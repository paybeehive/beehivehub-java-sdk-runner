package br.com.paybeehive.runner.resources;

import br.com.paybeehive.runner.Utils;
import br.com.paybeehive.sdk.BeehiveHubClient;
import br.com.paybeehive.sdk.models.Customer;
import br.com.paybeehive.sdk.requests.CreateCustomerRequest;

import java.util.List;
import java.util.Scanner;

public class CustomersMenu {

    private final BeehiveHubClient beehive;
    private final Scanner scanner;

    public CustomersMenu(BeehiveHubClient beehive, Scanner scanner) {
        this.beehive = beehive;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("=== Customers ===");
        System.out.println("  1. List customers by email");
        System.out.println("  2. Get customer by ID");
        System.out.println("  3. Create customer");
        System.out.println("  0. Back");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine().trim();
        System.out.println();

        try {
            switch (choice) {
                case "1" -> {
                    System.out.print("  Email: ");
                    String email = scanner.nextLine().trim();
                    List<Customer> result = beehive.customers.list(email);
                    Utils.saveOutput("customers-list", result);
                    System.out.println("  Found " + result.size() + " customer(s)");
                    Utils.printSuccess();
                }
                case "2" -> {
                    System.out.print("  Customer ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    Customer result = beehive.customers.get(id);
                    Utils.saveOutput("customer-get", result);
                    System.out.println("  ID: " + result.getId());
                    System.out.println("  Name: " + result.getName());
                    System.out.println("  Email: " + result.getEmail());
                    Utils.printSuccess();
                }
                case "3" -> {
                    CreateCustomerRequest payload = Utils.loadPayload("customer-create.json", CreateCustomerRequest.class);
                    Customer result = beehive.customers.create(payload);
                    Utils.saveOutput("customer-create", result);
                    System.out.println("  ID: " + result.getId());
                    System.out.println("  Name: " + result.getName());
                    Utils.printSuccess();
                }
                case "0" -> {}
                default -> System.out.println("  Invalid option.");
            }
        } catch (Exception e) {
            Utils.printError(e);
        }
    }
}
