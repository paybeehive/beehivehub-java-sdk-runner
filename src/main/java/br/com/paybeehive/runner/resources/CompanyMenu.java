package br.com.paybeehive.runner.resources;

import br.com.paybeehive.runner.Utils;
import br.com.paybeehive.sdk.BeehiveHubClient;
import br.com.paybeehive.sdk.models.Company;
import br.com.paybeehive.sdk.requests.UpdateCompanyRequest;

import java.util.Scanner;

public class CompanyMenu {

    private final BeehiveHubClient beehive;
    private final Scanner scanner;
    private final String environment;

    public CompanyMenu(BeehiveHubClient beehive, Scanner scanner, String environment) {
        this.beehive = beehive;
        this.scanner = scanner;
        this.environment = environment;
    }

    public void run() {
        System.out.println("🏢 Company");
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
                    Utils.printResultWithFile("company-get", result,
                            new Utils.SdkInfo("company", "get", environment));
                    Utils.printSuccess("Company #" + result.getId() + " — " + result.getEmail());
                }
                case "2" -> {
                    UpdateCompanyRequest payload = Utils.loadPayload("company-update.json", UpdateCompanyRequest.class);
                    Company result = beehive.company.update(payload);
                    Utils.printResultWithFile("company-update", result,
                            new Utils.SdkInfo("company", "update", environment));
                    Utils.printSuccess("Company #" + result.getId() + " updated");
                }
                case "0" -> {}
                default -> Utils.printError("Invalid option.");
            }
        } catch (Exception e) {
            Utils.printError(e.getMessage());
        }
    }
}
