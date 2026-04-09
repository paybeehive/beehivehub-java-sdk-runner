package br.com.paybeehive.runner.resources;

import br.com.paybeehive.runner.Utils;
import br.com.paybeehive.sdk.BeehiveHubClient;
import br.com.paybeehive.sdk.models.Company;
import br.com.paybeehive.sdk.requests.UpdateCompanyRequest;

import java.util.Scanner;

public class CompanyMenu {

    private final BeehiveHubClient beehive;
    private final Scanner scanner;

    public CompanyMenu(BeehiveHubClient beehive, Scanner scanner) {
        this.beehive = beehive;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("=== Company ===");
        System.out.println("  1. Get company");
        System.out.println("  2. Update company");
        System.out.println("  0. Back");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine().trim();
        System.out.println();

        try {
            switch (choice) {
                case "1" -> {
                    Company result = beehive.company.get();
                    Utils.saveOutput("company-get", result);
                    System.out.println("  ID: " + result.getId());
                    System.out.println("  Email: " + result.getEmail());
                    Utils.printSuccess();
                }
                case "2" -> {
                    UpdateCompanyRequest payload = Utils.loadPayload("company-update.json", UpdateCompanyRequest.class);
                    Company result = beehive.company.update(payload);
                    Utils.saveOutput("company-update", result);
                    System.out.println("  ID: " + result.getId());
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
