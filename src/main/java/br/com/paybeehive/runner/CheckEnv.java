package br.com.paybeehive.runner;

import io.github.cdimascio.dotenv.Dotenv;

public class CheckEnv {

    public static void validate() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        String key = dotenv.get("BEEHIVE_SECRET_KEY");
        if (key == null || key.isBlank()) {
            System.err.println("\n❌ Error: .env file not found!");
            System.err.println("\n💡 Create .env file:");
            System.err.println("   1. Copy .env.example to .env");
            System.err.println("   2. Add your BEEHIVE_SECRET_KEY\n");
            System.exit(1);
        }

        if (key.equals("your_secret_key_here")) {
            System.err.println("\n❌ Error: BEEHIVE_SECRET_KEY not configured in .env");
            System.err.println("\n💡 Edit .env file and replace:");
            System.err.println("   BEEHIVE_SECRET_KEY=your_secret_key_here");
            System.err.println("   with your actual API key\n");
            System.exit(1);
        }

        System.out.println("✅ Configuration validated!\n");
    }
}
