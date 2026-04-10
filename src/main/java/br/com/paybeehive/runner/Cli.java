package br.com.paybeehive.runner;

import br.com.paybeehive.runner.resources.*;
import br.com.paybeehive.sdk.BeehiveHubClient;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Scanner;

public class Cli {

    private final BeehiveHubClient beehive;
    private final Scanner scanner;
    private final String environment;

    public Cli() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.environment = dotenv.get("BEEHIVE_ENVIRONMENT", "production");
        this.beehive = SdkFactory.create();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1" -> new TransactionsMenu(beehive, scanner, environment).run();
                case "2" -> new CustomersMenu(beehive, scanner, environment).run();
                case "3" -> new PaymentLinksMenu(beehive, scanner, environment).run();
                case "4" -> new RecipientsMenu(beehive, scanner, environment).run();
                case "5" -> new BankAccountsMenu(beehive, scanner, environment).run();
                case "6" -> new TransfersMenu(beehive, scanner, environment).run();
                case "7" -> new CompanyMenu(beehive, scanner, environment).run();
                case "8" -> new BalanceMenu(beehive, scanner, environment).run();
                case "0" -> {
                    System.out.println("\n👋 Até logo!\n");
                    return;
                }
                default -> System.out.println("\n❌ Opção inválida. Tente novamente.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🐝 Beehive Hub SDK Runner");
        System.out.println("🌐 Environment: " + environment.toUpperCase());
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        System.out.println("Escolha um recurso:");
        System.out.println("  1. 💳 Transactions");
        System.out.println("  2. 👥 Customers");
        System.out.println("  3. 🔗 Payment Links");
        System.out.println("  4. 🎯 Recipients");
        System.out.println("  5. 🏦 Bank Accounts");
        System.out.println("  6. 💸 Transfers");
        System.out.println("  7. 🏢 Company");
        System.out.println("  8. 📊 Balance");
        System.out.println("  0. ❌ Exit");
        System.out.print("\nChoice: ");
    }
}
