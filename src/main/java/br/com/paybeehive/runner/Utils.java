package br.com.paybeehive.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static <T> T loadPayload(String filename, Class<T> type) throws IOException {
        Path path = Paths.get("payloads", filename);
        if (!path.toFile().exists()) {
            throw new IOException("Payload file not found: " + path);
        }
        return mapper.readValue(path.toFile(), type);
    }

    public static void saveOutput(String operation, Object data) {
        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            String outputDir = dotenv.get("OUTPUT_DIR", "./output");
            Files.createDirectories(Paths.get(outputDir));

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss"));
            String filename = operation + "-" + timestamp + ".json";
            File file = Paths.get(outputDir, filename).toFile();
            mapper.writeValue(file, data);
            System.out.println("  Saved to: " + file.getPath());
        } catch (IOException e) {
            System.err.println("  Warning: Could not save output: " + e.getMessage());
        }
    }

    public static String formatCurrency(Long amountInCents) {
        if (amountInCents == null) return "R$ 0,00";
        return String.format("R$ %.2f", amountInCents / 100.0).replace('.', ',');
    }

    public static void printSuccess() {
        System.out.println("  OK");
    }

    public static void printError(Exception e) {
        System.out.println("  Error: " + e.getMessage());
    }
}
