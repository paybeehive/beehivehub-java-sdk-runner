package br.com.paybeehive.runner;

import br.com.paybeehive.sdk.BeehiveHubClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Smoke test: verifica que o SDK é importado e instanciado corretamente.
 * Não faz chamadas à API — apenas valida a integração local.
 */
class SdkIntegrationTest {

    @Test
    void shouldInstantiateClientWithDummyKey() {
        BeehiveHubClient client = new BeehiveHubClient("sk_test_dummy", "sandbox");
        assertNotNull(client);
    }

    @Test
    void shouldExposeAllResources() {
        BeehiveHubClient client = new BeehiveHubClient("sk_test_dummy", "sandbox");
        assertNotNull(client.transactions,  "client.transactions should be defined");
        assertNotNull(client.customers,     "client.customers should be defined");
        assertNotNull(client.recipients,    "client.recipients should be defined");
        assertNotNull(client.bankAccounts,  "client.bankAccounts should be defined");
        assertNotNull(client.transfers,     "client.transfers should be defined");
        assertNotNull(client.company,       "client.company should be defined");
        assertNotNull(client.balance,       "client.balance should be defined");
        assertNotNull(client.paymentLinks,  "client.paymentLinks should be defined");
    }
}
