package br.com.paybeehive.runner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    // -------------------------------------------------------------------------
    // formatCurrency
    // -------------------------------------------------------------------------

    @Test
    void formatCurrency_null_returnsZero() {
        assertEquals("R$ 0.00", Utils.formatCurrency(null));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "0     | R$ 0.00",
        "1     | R$ 0.01",
        "100   | R$ 1.00",
        "150   | R$ 1.50",
        "10000 | R$ 100.00",
        "99999 | R$ 999.99"
    })
    void formatCurrency_variousAmounts(long cents, String expected) {
        assertEquals(expected, Utils.formatCurrency(cents));
    }

    // -------------------------------------------------------------------------
    // loadPayload
    // -------------------------------------------------------------------------

    @Test
    void loadPayload_existingFile_returnsObject() throws IOException {
        Object result = Utils.loadPayload("transaction-create.json", Object.class);
        assertNotNull(result);
    }

    @Test
    void loadPayload_missingFile_throwsIOException() {
        assertThrows(IOException.class, () ->
            Utils.loadPayload("nonexistent-payload.json", Object.class));
    }

    @Test
    void loadPayload_allPayloadFilesAreValid() {
        String[] payloads = {
            "transaction-create.json",
            "customer-create.json",
            "recipient-create.json",
            "recipient-update.json",
            "bank-account-create.json",
            "transfer-create.json",
            "transfer-create-with-account.json",
            "payment-link-create.json",
            "payment-link-update.json",
            "company-update.json",
            "delivery-update.json"
        };

        for (String payload : payloads) {
            assertDoesNotThrow(
                () -> Utils.loadPayload(payload, Object.class),
                "Failed to load payload: " + payload
            );
        }
    }

    // -------------------------------------------------------------------------
    // saveOutput
    // -------------------------------------------------------------------------

    @Test
    void saveOutput_returnsFilename() {
        String filename = Utils.saveOutput("test-op", Map.of("key", "value"));
        assertFalse(filename.isEmpty());
        assertTrue(filename.startsWith("test-op-"));
        assertTrue(filename.endsWith(".json"));
    }

    @Test
    void saveOutput_withSdkInfo_returnsFilename() {
        Utils.SdkInfo sdkInfo = new Utils.SdkInfo("transactions", "list", "sandbox");
        String filename = Utils.saveOutput("test-sdk-info", Map.of("id", 1), sdkInfo);
        assertFalse(filename.isEmpty());
    }

    @Test
    void saveOutput_withNullData_doesNotThrow() {
        assertDoesNotThrow(() -> Utils.saveOutput("test-null", null));
    }

    // -------------------------------------------------------------------------
    // printSuccess / printError / printResult / printResultWithFile
    // -------------------------------------------------------------------------

    @Test
    void printSuccess_doesNotThrow() {
        assertDoesNotThrow(() -> Utils.printSuccess("Operation completed"));
    }

    @Test
    void printError_doesNotThrow() {
        assertDoesNotThrow(() -> Utils.printError("Something went wrong"));
    }

    @Test
    void printResult_withList_doesNotThrow() {
        assertDoesNotThrow(() -> Utils.printResult(java.util.List.of(Map.of("id", 1))));
    }

    @Test
    void printResult_withObject_doesNotThrow() {
        assertDoesNotThrow(() -> Utils.printResult(Map.of("id", 42, "status", "active")));
    }

    @Test
    void printResultWithFile_doesNotThrow() {
        Utils.SdkInfo sdkInfo = new Utils.SdkInfo("balance", "get", "production");
        assertDoesNotThrow(() ->
            Utils.printResultWithFile("test-result", Map.of("amount", 10000), sdkInfo));
    }
}
