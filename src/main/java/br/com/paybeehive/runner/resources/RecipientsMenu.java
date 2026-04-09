package br.com.paybeehive.runner.resources;

import br.com.paybeehive.runner.Utils;
import br.com.paybeehive.sdk.BeehiveHubClient;
import br.com.paybeehive.sdk.models.Recipient;
import br.com.paybeehive.sdk.requests.CreateRecipientRequest;
import br.com.paybeehive.sdk.requests.UpdateRecipientRequest;

import java.util.List;
import java.util.Scanner;

public class RecipientsMenu {

    private final BeehiveHubClient beehive;
    private final Scanner scanner;

    public RecipientsMenu(BeehiveHubClient beehive, Scanner scanner) {
        this.beehive = beehive;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("=== Recipients ===");
        System.out.println("  1. List recipients");
        System.out.println("  2. Get recipient by ID");
        System.out.println("  3. Create recipient");
        System.out.println("  4. Update recipient");
        System.out.println("  0. Back");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine().trim();
        System.out.println();

        try {
            switch (choice) {
                case "1" -> {
                    List<Recipient> result = beehive.recipients.list();
                    Utils.saveOutput("recipients-list", result);
                    System.out.println("  Found " + result.size() + " recipient(s)");
                    Utils.printSuccess();
                }
                case "2" -> {
                    System.out.print("  Recipient ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    Recipient result = beehive.recipients.get(id);
                    Utils.saveOutput("recipient-get", result);
                    System.out.println("  ID: " + result.getId());
                    System.out.println("  Legal Name: " + result.getLegalName());
                    System.out.println("  Status: " + result.getStatus());
                    Utils.printSuccess();
                }
                case "3" -> {
                    CreateRecipientRequest payload = Utils.loadPayload("recipient-create.json", CreateRecipientRequest.class);
                    Recipient result = beehive.recipients.create(payload);
                    Utils.saveOutput("recipient-create", result);
                    System.out.println("  ID: " + result.getId());
                    System.out.println("  Legal Name: " + result.getLegalName());
                    Utils.printSuccess();
                }
                case "4" -> {
                    System.out.print("  Recipient ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    UpdateRecipientRequest payload = Utils.loadPayload("recipient-update.json", UpdateRecipientRequest.class);
                    Recipient result = beehive.recipients.update(id, payload);
                    Utils.saveOutput("recipient-update", result);
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
