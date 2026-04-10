# BeehiveHub Java SDK Runner

Interactive CLI tool for testing and validating the [BeehiveHub Java SDK](https://github.com/paybeehive/beehivehub-java-sdk).

## Prerequisites

- Java 17+
- Maven 3.8+
- BeehiveHub Java SDK installed in your local Maven repository

## Setup

### 1. Install the SDK locally

```bash
cd ../beehivehub-java-sdk
mvn install -DskipTests
cd ../beehivehub-java-sdk-runner
```

### 2. Configure your API key

```bash
cp .env.example .env
```

Edit `.env` and set your credentials:

```
BEEHIVE_SECRET_KEY=your_secret_key_here
BEEHIVE_ENVIRONMENT=production  # or sandbox
```

### 3. Run

```bash
mvn exec:java
```

Or build a fat JAR and run:

```bash
mvn package
java -jar target/beehivehub-java-sdk-runner-1.0.0.jar
```

> When running the JAR directly, make sure to execute the command from the project root so the `payloads/` directory is accessible.

## Usage

The runner provides an interactive menu:

```
=== BeehiveHub Java SDK Runner ===

Select a resource:
  1. Transactions
  2. Customers
  3. Payment Links
  4. Recipients
  5. Bank Accounts
  6. Transfers
  7. Company
  8. Balance
  0. Exit
```

Select a resource, then choose an operation. Responses are saved to the `output/` directory as timestamped JSON files.

## Payloads

Edit the files in `payloads/` to customize the data sent to the API. See [payloads/README.md](payloads/README.md) for details.

> All monetary values are in **cents** (e.g., R$ 100,00 = `10000`).

## Environments

| Environment | API |
|-------------|-----|
| `production` | `https://api.conta.paybeehive.com.br/v1` |
| `sandbox` | `https://api.sandbox.hopysplit.com.br/v1` |
