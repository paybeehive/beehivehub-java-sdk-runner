# Quickstart

## 1. Install the SDK in your local Maven repo

```bash
cd ../beehivehub-java-sdk
mvn install -DskipTests
```

## 2. Configure environment

```bash
cd ../beehivehub-java-sdk-runner
cp .env.example .env
# Edit .env and set BEEHIVE_SECRET_KEY
```

## 3. Run

```bash
mvn exec:java
```

## 4. Try your first operation

1. Select `8` → Balance
2. Select `1` → Get available balance
3. Check the `output/` folder for the JSON response

## Tips

- Use `BEEHIVE_ENVIRONMENT=sandbox` in `.env` to test without real money
- Edit files in `payloads/` before running create/update operations
- All amounts are in cents: R$ 100.00 = `10000`
