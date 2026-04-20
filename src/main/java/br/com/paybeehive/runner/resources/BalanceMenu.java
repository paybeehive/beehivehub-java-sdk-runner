package br.com.paybeehive.runner.resources;

import br.com.paybeehive.runner.Utils;
import br.com.paybeehive.sdk.BeehiveHubClient;
import br.com.paybeehive.sdk.models.Balance;

import java.util.Scanner;

public class BalanceMenu {

    private final BeehiveHubClient beehive;
    private final Scanner scanner;
    private final String environment;

    public BalanceMenu(BeehiveHubClient beehive, Scanner scanner, String environment) {
        this.beehive = beehive;
        this.scanner = scanner;
        this.environment = environment;
    }

    public void run() {
        System.out.println("Balance");
        System.out.println("  1. Get available balance");
        System.out.println("  0. Back");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine().trim();
        System.out.println();

        try {
            switch (choice) {
                case "1" -> {
                    Balance result = beehive.balance.get();
                    Utils.printResultWithFile("balance-get", result,
                            new Utils.SdkInfo("balance", "get", environment));
                    Utils.printSuccess("Available balance: " + Utils.formatCurrency(result.getAmount()));
                }
                case "0" -> {}
                default -> Utils.printError("Invalid option.");
            }
        } catch (Exception e) {
            Utils.printError(e.getMessage());
        }
    }
}
