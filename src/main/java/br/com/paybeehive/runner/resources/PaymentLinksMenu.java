package br.com.paybeehive.runner.resources;

import br.com.paybeehive.runner.Utils;
import br.com.paybeehive.sdk.BeehiveHubClient;
import br.com.paybeehive.sdk.models.PaymentLink;
import br.com.paybeehive.sdk.requests.CreatePaymentLinkRequest;
import br.com.paybeehive.sdk.requests.UpdatePaymentLinkRequest;

import java.util.List;
import java.util.Scanner;

public class PaymentLinksMenu {

    private final BeehiveHubClient beehive;
    private final Scanner scanner;
    private final String environment;

    public PaymentLinksMenu(BeehiveHubClient beehive, Scanner scanner, String environment) {
        this.beehive = beehive;
        this.scanner = scanner;
        this.environment = environment;
    }

    public void run() {
        System.out.println("Payment Links");
        System.out.println("  1. List payment links");
        System.out.println("  2. Get payment link by ID");
        System.out.println("  3. Create payment link");
        System.out.println("  4. Update payment link");
        System.out.println("  5. Delete payment link");
        System.out.println("  0. Back");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine().trim();
        System.out.println();

        try {
            switch (choice) {
                case "1" -> {
                    List<PaymentLink> result = beehive.paymentLinks.list();
                    Utils.printResultWithFile("payment-links-list", result,
                            new Utils.SdkInfo("paymentLinks", "list", environment));
                    Utils.printSuccess("Listed " + result.size() + " payment link(s)");
                }
                case "2" -> {
                    System.out.print("  Payment Link ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    PaymentLink result = beehive.paymentLinks.get(id);
                    Utils.printResultWithFile("payment-link-get", result,
                            new Utils.SdkInfo("paymentLinks", "get", environment));
                    Utils.printSuccess("Payment Link #" + result.getId() + " — " + result.getUrl());
                }
                case "3" -> {
                    CreatePaymentLinkRequest payload = Utils.loadPayload("payment-link-create.json", CreatePaymentLinkRequest.class);
                    PaymentLink result = beehive.paymentLinks.create(payload);
                    Utils.printResultWithFile("payment-link-create", result,
                            new Utils.SdkInfo("paymentLinks", "create", environment));
                    Utils.printSuccess("Payment Link created: " + result.getUrl());
                }
                case "4" -> {
                    System.out.print("  Payment Link ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    UpdatePaymentLinkRequest payload = Utils.loadPayload("payment-link-update.json", UpdatePaymentLinkRequest.class);
                    PaymentLink result = beehive.paymentLinks.update(id, payload);
                    Utils.printResultWithFile("payment-link-update", result,
                            new Utils.SdkInfo("paymentLinks", "update", environment));
                    Utils.printSuccess("Payment Link #" + id + " updated");
                }
                case "5" -> {
                    System.out.print("  Payment Link ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    beehive.paymentLinks.delete(id);
                    Utils.printSuccess("Payment Link #" + id + " deleted");
                }
                case "0" -> {}
                default -> Utils.printError("Invalid option.");
            }
        } catch (Exception e) {
            Utils.printError(e.getMessage());
        }
    }
}
