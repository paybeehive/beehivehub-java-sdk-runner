package br.com.paybeehive.runner;

import br.com.paybeehive.runner.resources.*;
import br.com.paybeehive.sdk.BeehiveHubClient;

import java.util.Scanner;

public class Cli {

    private final BeehiveHubClient beehive;
    private final Scanner scanner;

    public Cli() {
        this.beehive = SdkFactory.create();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("\n=== BeehiveHub Java SDK Runner ===\n");

        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1" -> new TransactionsMenu(beehive, scanner).run();
                case "2" -> new CustomersMenu(beehive, scanner).run();
                case "3" -> new PaymentLinksMenu(beehive, scanner).run();
                case "4" -> new RecipientsMenu(beehive, scanner).run();
                case "5" -> new BankAccountsMenu(beehive, scanner).run();
                case "6" -> new TransfersMenu(beehive, scanner).run();
                case "7" -> new CompanyMenu(beehive, scanner).run();
                case "8" -> new BalanceMenu(beehive, scanner).run();
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("  Invalid option. Try again.");
            }
            System.out.println();
        }
    }

    private void printMainMenu() {
        System.out.println("Select a resource:");
        System.out.println("  1. Transactions");
        System.out.println("  2. Customers");
        System.out.println("  3. Payment Links");
        System.out.println("  4. Recipients");
        System.out.println("  5. Bank Accounts");
        System.out.println("  6. Transfers");
        System.out.println("  7. Company");
        System.out.println("  8. Balance");
        System.out.println("  0. Exit");
        System.out.print("\nChoice: ");
    }
}
