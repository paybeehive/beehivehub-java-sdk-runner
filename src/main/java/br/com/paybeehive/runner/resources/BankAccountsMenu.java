package br.com.paybeehive.runner.resources;

import br.com.paybeehive.runner.Utils;
import br.com.paybeehive.sdk.BeehiveHubClient;
import br.com.paybeehive.sdk.models.BankAccount;
import br.com.paybeehive.sdk.requests.CreateBankAccountRequest;

import java.util.List;
import java.util.Scanner;

public class BankAccountsMenu {

    private final BeehiveHubClient beehive;
    private final Scanner scanner;

    public BankAccountsMenu(BeehiveHubClient beehive, Scanner scanner) {
        this.beehive = beehive;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("=== Bank Accounts ===");
        System.out.println("  1. List bank accounts");
        System.out.println("  2. Create bank account");
        System.out.println("  0. Back");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine().trim();
        System.out.println();

        try {
            switch (choice) {
                case "1" -> {
                    System.out.print("  Recipient ID: ");
                    Long recipientId = Long.parseLong(scanner.nextLine().trim());
                    List<BankAccount> result = beehive.bankAccounts.list(recipientId);
                    Utils.saveOutput("bank-accounts-list", result);
                    System.out.println("  Found " + result.size() + " bank account(s)");
                    Utils.printSuccess();
                }
                case "2" -> {
                    System.out.print("  Recipient ID: ");
                    Long recipientId = Long.parseLong(scanner.nextLine().trim());
                    CreateBankAccountRequest payload = Utils.loadPayload("bank-account-create.json", CreateBankAccountRequest.class);
                    BankAccount result = beehive.bankAccounts.create(recipientId, payload);
                    Utils.saveOutput("bank-account-create", result);
                    System.out.println("  ID: " + result.getId());
                    System.out.println("  Bank: " + result.getBankCode());
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
