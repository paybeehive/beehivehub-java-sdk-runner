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
    private final String environment;

    public RecipientsMenu(BeehiveHubClient beehive, Scanner scanner, String environment) {
        this.beehive = beehive;
        this.scanner = scanner;
        this.environment = environment;
    }

    public void run() {
        System.out.println("🎯 Recipients");
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
                    Utils.printResultWithFile("recipients-list", result,
                            new Utils.SdkInfo("recipients", "list", environment));
                    Utils.printSuccess("Listed " + result.size() + " recipient(s)");
                }
                case "2" -> {
                    System.out.print("  Recipient ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    Recipient result = beehive.recipients.get(id);
                    Utils.printResultWithFile("recipient-get", result,
                            new Utils.SdkInfo("recipients", "get", environment));
                    Utils.printSuccess("Recipient #" + result.getId() + " — " + result.getLegalName());
                }
                case "3" -> {
                    CreateRecipientRequest payload = Utils.loadPayload("recipient-create.json", CreateRecipientRequest.class);
                    Recipient result = beehive.recipients.create(payload);
                    Utils.printResultWithFile("recipient-create", result,
                            new Utils.SdkInfo("recipients", "create", environment));
                    Utils.printSuccess("Recipient created: #" + result.getId() + " — " + result.getLegalName());
                }
                case "4" -> {
                    System.out.print("  Recipient ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    UpdateRecipientRequest payload = Utils.loadPayload("recipient-update.json", UpdateRecipientRequest.class);
                    Recipient result = beehive.recipients.update(id, payload);
                    Utils.printResultWithFile("recipient-update", result,
                            new Utils.SdkInfo("recipients", "update", environment));
                    Utils.printSuccess("Recipient #" + id + " updated");
                }
                case "0" -> {}
                default -> Utils.printError("Invalid option.");
            }
        } catch (Exception e) {
            Utils.printError(e.getMessage());
        }
    }
}
