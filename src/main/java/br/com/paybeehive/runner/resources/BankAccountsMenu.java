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
    private final String environment;

    public BankAccountsMenu(BeehiveHubClient beehive, Scanner scanner, String environment) {
        this.beehive = beehive;
        this.scanner = scanner;
        this.environment = environment;
    }

    public void run() {
        System.out.println("🏦 Bank Accounts");
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
                    Utils.printResultWithFile("bank-accounts-list", result,
                            new Utils.SdkInfo("bankAccounts", "list", environment));
                    Utils.printSuccess("Listed " + result.size() + " bank account(s)");
                }
                case "2" -> {
                    System.out.print("  Recipient ID: ");
                    Long recipientId = Long.parseLong(scanner.nextLine().trim());
                    CreateBankAccountRequest payload = Utils.loadPayload("bank-account-create.json", CreateBankAccountRequest.class);
                    BankAccount result = beehive.bankAccounts.create(recipientId, payload);
                    Utils.printResultWithFile("bank-account-create", result,
                            new Utils.SdkInfo("bankAccounts", "create", environment));
                    Utils.printSuccess("Bank account created: #" + result.getId() + " — bank " + result.getBankCode());
                }
                case "0" -> {}
                default -> Utils.printError("Invalid option.");
            }
        } catch (Exception e) {
            Utils.printError(e.getMessage());
        }
    }
}
