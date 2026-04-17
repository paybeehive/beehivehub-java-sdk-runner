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
    private final String environment;

    public CustomersMenu(BeehiveHubClient beehive, Scanner scanner, String environment) {
        this.beehive = beehive;
        this.scanner = scanner;
        this.environment = environment;
    }

    public void run() {
        System.out.println("Customers");
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
                    Utils.printResultWithFile("customers-list", result,
                            new Utils.SdkInfo("customers", "list", environment));
                    Utils.printSuccess("Listed " + result.size() + " customer(s)");
                }
                case "2" -> {
                    System.out.print("  Customer ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    Customer result = beehive.customers.get(id);
                    Utils.printResultWithFile("customer-get", result,
                            new Utils.SdkInfo("customers", "get", environment));
                    Utils.printSuccess("Customer #" + result.getId() + " — " + result.getName());
                }
                case "3" -> {
                    CreateCustomerRequest payload = Utils.loadPayload("customer-create.json", CreateCustomerRequest.class);
                    Customer result = beehive.customers.create(payload);
                    Utils.printResultWithFile("customer-create", result,
                            new Utils.SdkInfo("customers", "create", environment));
                    Utils.printSuccess("Customer created: #" + result.getId() + " — " + result.getName());
                }
                case "0" -> {}
                default -> Utils.printError("Invalid option.");
            }
        } catch (Exception e) {
            Utils.printError(e.getMessage());
        }
    }
}
