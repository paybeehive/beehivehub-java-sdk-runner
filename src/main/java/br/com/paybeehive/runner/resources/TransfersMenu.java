package br.com.paybeehive.runner.resources;

import br.com.paybeehive.runner.Utils;
import br.com.paybeehive.sdk.BeehiveHubClient;
import br.com.paybeehive.sdk.models.Transfer;
import br.com.paybeehive.sdk.requests.CreateTransferRequest;

import java.util.Scanner;

public class TransfersMenu {

    private final BeehiveHubClient beehive;
    private final Scanner scanner;
    private final String environment;

    public TransfersMenu(BeehiveHubClient beehive, Scanner scanner, String environment) {
        this.beehive = beehive;
        this.scanner = scanner;
        this.environment = environment;
    }

    public void run() {
        System.out.println("Transfers");
        System.out.println("  1. Get transfer by ID");
        System.out.println("  2. Create transfer");
        System.out.println("  3. Create transfer with bank account");
        System.out.println("  0. Back");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine().trim();
        System.out.println();

        try {
            switch (choice) {
                case "1" -> {
                    System.out.print("  Transfer ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    Transfer result = beehive.transfers.get(id);
                    Utils.printResultWithFile("transfer-get", result,
                            new Utils.SdkInfo("transfers", "get", environment));
                    Utils.printSuccess("Transfer #" + result.getId() + " — " + result.getStatus()
                            + " — " + Utils.formatCurrency(result.getAmount()));
                }
                case "2" -> {
                    CreateTransferRequest payload = Utils.loadPayload("transfer-create.json", CreateTransferRequest.class);
                    Transfer result = beehive.transfers.create(payload);
                    Utils.printResultWithFile("transfer-create", result,
                            new Utils.SdkInfo("transfers", "create", environment));
                    Utils.printSuccess("Transfer created: #" + result.getId() + " — " + result.getStatus());
                }
                case "3" -> {
                    CreateTransferRequest payload = Utils.loadPayload("transfer-create-with-account.json", CreateTransferRequest.class);
                    Transfer result = beehive.transfers.create(payload);
                    Utils.printResultWithFile("transfer-create-with-account", result,
                            new Utils.SdkInfo("transfers", "create", environment));
                    Utils.printSuccess("Transfer created: #" + result.getId() + " — " + result.getStatus());
                }
                case "0" -> {}
                default -> Utils.printError("Invalid option.");
            }
        } catch (Exception e) {
            Utils.printError(e.getMessage());
        }
    }
}
