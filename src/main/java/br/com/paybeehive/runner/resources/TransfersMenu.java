package br.com.paybeehive.runner.resources;

import br.com.paybeehive.runner.Utils;
import br.com.paybeehive.sdk.BeehiveHubClient;
import br.com.paybeehive.sdk.models.Transfer;
import br.com.paybeehive.sdk.requests.CreateTransferRequest;

import java.util.Scanner;

public class TransfersMenu {

    private final BeehiveHubClient beehive;
    private final Scanner scanner;

    public TransfersMenu(BeehiveHubClient beehive, Scanner scanner) {
        this.beehive = beehive;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("=== Transfers ===");
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
                    Utils.saveOutput("transfer-get", result);
                    System.out.println("  ID: " + result.getId());
                    System.out.println("  Status: " + result.getStatus());
                    System.out.println("  Amount: " + Utils.formatCurrency(result.getAmount()));
                    Utils.printSuccess();
                }
                case "2" -> {
                    CreateTransferRequest payload = Utils.loadPayload("transfer-create.json", CreateTransferRequest.class);
                    Transfer result = beehive.transfers.create(payload);
                    Utils.saveOutput("transfer-create", result);
                    System.out.println("  ID: " + result.getId());
                    System.out.println("  Status: " + result.getStatus());
                    Utils.printSuccess();
                }
                case "3" -> {
                    CreateTransferRequest payload = Utils.loadPayload("transfer-create-with-account.json", CreateTransferRequest.class);
                    Transfer result = beehive.transfers.create(payload);
                    Utils.saveOutput("transfer-create-with-account", result);
                    System.out.println("  ID: " + result.getId());
                    System.out.println("  Status: " + result.getStatus());
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
