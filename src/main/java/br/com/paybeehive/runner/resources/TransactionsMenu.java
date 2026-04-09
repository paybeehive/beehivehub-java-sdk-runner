package br.com.paybeehive.runner.resources;

import br.com.paybeehive.runner.Utils;
import br.com.paybeehive.sdk.BeehiveHubClient;
import br.com.paybeehive.sdk.models.Transaction;
import br.com.paybeehive.sdk.requests.CreateTransactionRequest;
import br.com.paybeehive.sdk.requests.ListTransactionsParams;
import br.com.paybeehive.sdk.requests.UpdateDeliveryStatusRequest;

import java.util.List;
import java.util.Scanner;

public class TransactionsMenu {

    private final BeehiveHubClient beehive;
    private final Scanner scanner;

    public TransactionsMenu(BeehiveHubClient beehive, Scanner scanner) {
        this.beehive = beehive;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("=== Transactions ===");
        System.out.println("  1. List transactions");
        System.out.println("  2. Get transaction by ID");
        System.out.println("  3. Create transaction");
        System.out.println("  4. Refund transaction");
        System.out.println("  5. Update delivery status");
        System.out.println("  0. Back");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine().trim();
        System.out.println();

        try {
            switch (choice) {
                case "1" -> {
                    List<Transaction> result = beehive.transactions.list(new ListTransactionsParams());
                    Utils.saveOutput("transactions-list", result);
                    System.out.println("  Found " + result.size() + " transaction(s)");
                    Utils.printSuccess();
                }
                case "2" -> {
                    System.out.print("  Transaction ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    Transaction result = beehive.transactions.get(id);
                    Utils.saveOutput("transaction-get", result);
                    System.out.println("  ID: " + result.getId());
                    System.out.println("  Status: " + result.getStatus());
                    System.out.println("  Amount: " + Utils.formatCurrency(result.getAmount()));
                    Utils.printSuccess();
                }
                case "3" -> {
                    CreateTransactionRequest payload = Utils.loadPayload("transaction-create.json", CreateTransactionRequest.class);
                    Transaction result = beehive.transactions.create(payload);
                    Utils.saveOutput("transaction-create", result);
                    System.out.println("  ID: " + result.getId());
                    System.out.println("  Status: " + result.getStatus());
                    Utils.printSuccess();
                }
                case "4" -> {
                    System.out.print("  Transaction ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    System.out.print("  Refund amount in cents (blank = full refund): ");
                    String amountStr = scanner.nextLine().trim();
                    Long amount = amountStr.isBlank() ? null : Long.parseLong(amountStr);
                    Transaction result = beehive.transactions.refund(id, amount);
                    Utils.saveOutput("transaction-refund", result);
                    System.out.println("  Status: " + result.getStatus());
                    Utils.printSuccess();
                }
                case "5" -> {
                    System.out.print("  Transaction ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    UpdateDeliveryStatusRequest payload = Utils.loadPayload("delivery-update.json", UpdateDeliveryStatusRequest.class);
                    Transaction result = beehive.transactions.updateDelivery(id, payload);
                    Utils.saveOutput("transaction-delivery-update", result);
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
