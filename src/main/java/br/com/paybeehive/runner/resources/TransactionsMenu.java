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
    private final String environment;

    public TransactionsMenu(BeehiveHubClient beehive, Scanner scanner, String environment) {
        this.beehive = beehive;
        this.scanner = scanner;
        this.environment = environment;
    }

    public void run() {
        System.out.println("Transactions");
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
                    Utils.printResultWithFile("transactions-list", result,
                            new Utils.SdkInfo("transactions", "list", environment));
                    Utils.printSuccess("Listed " + result.size() + " transaction(s)");
                }
                case "2" -> {
                    System.out.print("  Transaction ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    Transaction result = beehive.transactions.get(id);
                    Utils.printResultWithFile("transaction-get", result,
                            new Utils.SdkInfo("transactions", "get", environment));
                    Utils.printSuccess("Transaction #" + result.getId() + " — " + result.getStatus());
                }
                case "3" -> {
                    CreateTransactionRequest payload = Utils.loadPayload("transaction-create.json", CreateTransactionRequest.class);
                    Transaction result = beehive.transactions.create(payload);
                    Utils.printResultWithFile("transaction-create", result,
                            new Utils.SdkInfo("transactions", "create", environment));
                    Utils.printSuccess("Transaction created: #" + result.getId() + " — " + result.getStatus());
                }
                case "4" -> {
                    System.out.print("  Transaction ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    System.out.print("  Refund amount in cents (blank = full refund): ");
                    String amountStr = scanner.nextLine().trim();
                    Long amount = amountStr.isBlank() ? null : Long.parseLong(amountStr);
                    Transaction result = beehive.transactions.refund(id, amount);
                    Utils.printResultWithFile("transaction-refund", result,
                            new Utils.SdkInfo("transactions", "refund", environment));
                    Utils.printSuccess("Transaction #" + id + " refunded — " + result.getStatus());
                }
                case "5" -> {
                    System.out.print("  Transaction ID: ");
                    Long id = Long.parseLong(scanner.nextLine().trim());
                    UpdateDeliveryStatusRequest payload = Utils.loadPayload("delivery-update.json", UpdateDeliveryStatusRequest.class);
                    Transaction result = beehive.transactions.updateDelivery(id, payload);
                    Utils.printResultWithFile("transaction-delivery-update", result,
                            new Utils.SdkInfo("transactions", "updateDelivery", environment));
                    Utils.printSuccess("Delivery status updated for transaction #" + id);
                }
                case "0" -> {}
                default -> Utils.printError("Invalid option.");
            }
        } catch (Exception e) {
            Utils.printError(e.getMessage());
        }
    }
}
