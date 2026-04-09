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

    public PaymentLinksMenu(BeehiveHubClient beehive, Scanner scanner) {
        this.beehive = beehive;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("=== Payment Links ===");
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
                    Utils.saveOutput("payment-links-list", result);
                    System.out.println("  Found " + result.size() + " payment link(s)");
                    Utils.printSuccess();
                }
                case "2" -> {
                    System.out.print("  Payment Link ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    PaymentLink result = beehive.paymentLinks.get(id);
                    Utils.saveOutput("payment-link-get", result);
                    System.out.println("  ID: " + result.getId());
                    System.out.println("  Alias: " + result.getAlias());
                    System.out.println("  URL: " + result.getUrl());
                    System.out.println("  Amount: " + Utils.formatCurrency(result.getAmount()));
                    Utils.printSuccess();
                }
                case "3" -> {
                    CreatePaymentLinkRequest payload = Utils.loadPayload("payment-link-create.json", CreatePaymentLinkRequest.class);
                    PaymentLink result = beehive.paymentLinks.create(payload);
                    Utils.saveOutput("payment-link-create", result);
                    System.out.println("  ID: " + result.getId());
                    System.out.println("  URL: " + result.getUrl());
                    Utils.printSuccess();
                }
                case "4" -> {
                    System.out.print("  Payment Link ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    UpdatePaymentLinkRequest payload = Utils.loadPayload("payment-link-update.json", UpdatePaymentLinkRequest.class);
                    PaymentLink result = beehive.paymentLinks.update(id, payload);
                    Utils.saveOutput("payment-link-update", result);
                    System.out.println("  ID: " + result.getId());
                    Utils.printSuccess();
                }
                case "5" -> {
                    System.out.print("  Payment Link ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    beehive.paymentLinks.delete(id);
                    System.out.println("  Payment link " + id + " deleted.");
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
