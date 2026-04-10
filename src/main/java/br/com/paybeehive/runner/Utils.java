package br.com.paybeehive.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public record SdkInfo(String resource, String method, String environment) {}

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static <T> T loadPayload(String filename, Class<T> type) throws IOException {
        var path = Paths.get("payloads", filename);
        if (!path.toFile().exists()) {
            throw new IOException("Payload file not found: " + path);
        }
        return mapper.readValue(path.toFile(), type);
    }

    public static String saveOutput(String operation, Object data, SdkInfo sdkInfo) {
        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            String outputDir = dotenv.get("OUTPUT_DIR", "./output");
            Files.createDirectories(Paths.get(outputDir));

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss"));
            String filename = operation + "-" + timestamp + ".json";
            File file = Paths.get(outputDir, filename).toFile();

            Map<String, Object> output = new LinkedHashMap<>();
            if (sdkInfo != null) {
                Map<String, String> sdkCall = new LinkedHashMap<>();
                sdkCall.put("resource", sdkInfo.resource());
                sdkCall.put("method", sdkInfo.method());
                sdkCall.put("environment", sdkInfo.environment());
                output.put("sdk_call", sdkCall);
            }
            output.put("response", data);

            mapper.writeValue(file, output);
            return filename;
        } catch (IOException e) {
            System.err.println("\n❌ Error saving output: " + e.getMessage());
            return "";
        }
    }

    public static String saveOutput(String operation, Object data) {
        return saveOutput(operation, data, null);
    }

    public static String formatCurrency(Long amountInCents) {
        if (amountInCents == null) return "R$ 0.00";
        return String.format("R$ %.2f", amountInCents / 100.0);
    }

    public static void printSuccess(String message) {
        System.out.println("\n✅ " + message);
    }

    public static void printError(String message) {
        System.err.println("\n❌ " + message);
    }

    public static void printResult(Object data) {
        if (data instanceof List<?> list) {
            System.out.println("\n📊 Array with " + list.size() + " items");
            if (!list.isEmpty() && list.get(0) instanceof Map<?, ?> first) {
                Object id = first.get("id");
                System.out.println("   First item ID: " + (id != null ? id : "N/A"));
            }
        } else if (data instanceof Map<?, ?> map) {
            System.out.println("\n📊 Object with " + map.size() + " properties");
            if (map.containsKey("id")) {
                System.out.println("   ID: " + map.get("id"));
            }
        } else {
            System.out.println("\n📊 Result: " + data);
        }
    }

    public static void printResultWithFile(String operation, Object data, SdkInfo sdkInfo) {
        String filename = saveOutput(operation, data, sdkInfo);
        if (!filename.isEmpty()) {
            System.out.println("📁 Full result saved to: output/" + filename);
        }
    }
}
