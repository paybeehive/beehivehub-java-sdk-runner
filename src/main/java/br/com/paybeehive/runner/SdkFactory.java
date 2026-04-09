package br.com.paybeehive.runner;

import br.com.paybeehive.sdk.BeehiveHubClient;
import io.github.cdimascio.dotenv.Dotenv;

public class SdkFactory {

    public static BeehiveHubClient create() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String apiKey = dotenv.get("BEEHIVE_SECRET_KEY");
        String environment = dotenv.get("BEEHIVE_ENVIRONMENT", "production");
        return new BeehiveHubClient(apiKey, environment);
    }
}
