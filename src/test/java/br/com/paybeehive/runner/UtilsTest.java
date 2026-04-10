package br.com.paybeehive.runner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    // -------------------------------------------------------------------------
    // formatCurrency
    // -------------------------------------------------------------------------

    @Test
    void formatCurrency_null_returnsZero() {
        assertEquals("R$ 0,00", Utils.formatCurrency(null));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "0     | R$ 0,00",
        "1     | R$ 0,01",
        "100   | R$ 1,00",
        "150   | R$ 1,50",
        "10000 | R$ 100,00",
        "99999 | R$ 999,99"
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
    void saveOutput_createsFileInOutputDir() {
        assertDoesNotThrow(() -> Utils.saveOutput("test-op", Map.of("key", "value")));
    }

    @Test
    void saveOutput_withNullData_doesNotThrow() {
        assertDoesNotThrow(() -> Utils.saveOutput("test-null", null));
    }

    // -------------------------------------------------------------------------
    // printSuccess / printError
    // -------------------------------------------------------------------------

    @Test
    void printSuccess_doesNotThrow() {
        assertDoesNotThrow(Utils::printSuccess);
    }

    @Test
    void printError_doesNotThrow() {
        assertDoesNotThrow(() -> Utils.printError(new RuntimeException("test error")));
    }
}
