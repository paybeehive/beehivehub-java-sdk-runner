package br.com.paybeehive.runner.resources;

import br.com.paybeehive.runner.Utils;
import br.com.paybeehive.sdk.BeehiveHubClient;
import br.com.paybeehive.sdk.models.Balance;

import java.util.Scanner;

public class BalanceMenu {

    private final BeehiveHubClient beehive;
    private final Scanner scanner;

    public BalanceMenu(BeehiveHubClient beehive, Scanner scanner) {
        this.beehive = beehive;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("=== Balance ===");
        System.out.println("  1. Get available balance");
        System.out.println("  0. Back");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine().trim();
        System.out.println();

        try {
            switch (choice) {
                case "1" -> {
                    Balance result = beehive.balance.get();
                    Utils.saveOutput("balance-get", result);
                    System.out.println("  Available: " + Utils.formatCurrency(result.getAmount()));
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
