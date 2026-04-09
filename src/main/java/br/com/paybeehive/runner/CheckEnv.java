package br.com.paybeehive.runner;

import io.github.cdimascio.dotenv.Dotenv;

public class CheckEnv {

    public static void validate() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        String key = dotenv.get("BEEHIVE_SECRET_KEY");
        if (key == null || key.isBlank() || key.equals("your_secret_key_here")) {
            System.err.println("Error: BEEHIVE_SECRET_KEY is not configured.");
            System.err.println("  Copy .env.example to .env and set your API key.");
            System.exit(1);
        }
    }
}
