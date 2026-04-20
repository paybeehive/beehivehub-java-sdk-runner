# Beehive Hub Java SDK Runner

🚀 CLI interativo para consumir e validar o SDK [BeehiveHub Java SDK](https://github.com/paybeehive/beehivehub-java-sdk).

## 📋 Sobre o Projeto

Este é um projeto de desenvolvimento interno para consumir e validar todas as funcionalidades do SDK Beehive Hub de forma prática e interativa.

**Não é um projeto de testes automatizados** - é uma ferramenta para desenvolvedores executarem operações da API de forma manual e controlada através de um menu interativo no terminal.

## 🚀 Setup Rápido

```bash
# 0. Clonar (se obtendo do GitHub)
git clone https://github.com/paybeehive/beehivehub-java-sdk-runner.git
cd beehivehub-java-sdk-runner

# 1. Configurar .env
cp .env.example .env

# 2. Editar .env com suas credenciais
BEEHIVE_SECRET_KEY=your_secret_key_here
BEEHIVE_ENVIRONMENT=production  # ou 'sandbox'

# 3. Executar
mvn exec:java
```

### 🌐 Ambientes

O SDK suporta dois ambientes:

- **`production`** (padrão) - API de produção
- **`sandbox`** - API de sandbox/testes

Configure no `.env`:

```env
# Produção (padrão)
BEEHIVE_SECRET_KEY=sk_live_abc...
# BEEHIVE_ENVIRONMENT=production

# Sandbox
BEEHIVE_SECRET_KEY=sk_test_xyz...
BEEHIVE_ENVIRONMENT=sandbox
```

O ambiente atual é mostrado no topo do CLI.

### Windows — caracteres especiais

No CMD ou PowerShell, execute antes de rodar para evitar caracteres quebrados:

```bash
chcp 65001
```

## 💻 Como Usar

### Executar o CLI

```bash
mvn exec:java
```

Ou build um fat JAR e execute:

```bash
mvn package
java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -jar target/beehivehub-java-sdk-runner-1.0.0.jar
```

> Ao rodar o JAR diretamente, execute o comando a partir da raiz do projeto para que a pasta `payloads/` seja acessível.

Você verá um menu interativo:

```text
Beehive Hub SDK Runner
Environment: PRODUCTION

Escolha um recurso:
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

### 📁 Salvamento Automático das Respostas

**IMPORTANTE**: Todas as respostas da API são **automaticamente salvas** na pasta `output/` no formato:

```text
output/{funcionalidade}-{timestamp}.json
```

**No terminal você verá apenas um resumo**, por exemplo:

```text
[OK] Transaction created: #txn_abc123

Saved to: output/transaction-create-2026-02-19T12-30-45.json
```

O JSON completo está no arquivo, não no terminal!

### Workflow Típico

1. **Escolha o recurso** (ex: Transactions)
2. **Escolha a operação** (ex: List, Get, Create)
3. **Para GET:** Digite o ID quando solicitado
4. **Para CREATE/UPDATE:** O payload do arquivo JSON será usado
5. **Veja o resultado** formatado no terminal
6. **Volte ao menu** para nova operação

### Editar Payloads

Os payloads ficam em `payloads/*.json`:

```text
payloads/
├── transaction-create.json            # Criar transação
├── customer-create.json               # Criar cliente
├── payment-link-create.json           # Criar link de pagamento
├── payment-link-update.json           # Atualizar link de pagamento
├── recipient-create.json              # Criar recebedor
├── recipient-update.json              # Atualizar recebedor
├── bank-account-create.json           # Criar conta bancária
├── transfer-create.json               # Transferência com recipientId
├── transfer-create-with-account.json  # Transferência com conta
├── company-update.json                # Atualizar empresa
└── delivery-update.json               # Atualizar entrega
```

**Para testar diferentes cenários:**

1. Edite o arquivo JSON
2. Execute a operação no menu
3. Ajuste conforme necessário
4. Execute novamente

## 📖 Recursos Disponíveis

### 💳 Transactions

- **List Transactions** - Lista com filtros (limit, offset, createdFrom, etc.)
- **Get Transaction** - Busca por ID
- **Create Transaction** - Usa `transaction-create.json`
- **Refund Transaction** - Estorno total ou parcial
- **Update Delivery** - Usa `delivery-update.json`

### 👥 Customers

- **List Customers** - Busca por email (obrigatório; não aceita paginação)
- **Get Customer** - Busca por ID
- **Create Customer** - Usa `customer-create.json`

### 🔗 Payment Links

- **List Payment Links** - Lista todos
- **Get Payment Link** - Busca por ID
- **Create Payment Link** - Usa `payment-link-create.json`
- **Update Payment Link** - Usa `payment-link-update.json`
- **Delete Payment Link** - Remove por ID

### 🎯 Recipients

- **List Recipients** - Lista todos
- **Get Recipient** - Busca por ID
- **Create Recipient** - Usa `recipient-create.json`
- **Update Recipient** - Usa `recipient-update.json`

### 🏦 Bank Accounts

- **List Bank Accounts** - Lista por Recipient ID
- **Create Bank Account** - Usa `bank-account-create.json`

> Bank Accounts são vinculados a um Recipient. Informe o ID do recipient para listar ou criar.

### 💸 Transfers

- **Create Transfer** - Usa `transfer-create.json`
- **Get Transfer** - Busca por ID

### 🏢 Company

- **Get Company Info** - Dados da empresa
- **Update Company** - Usa `company-update.json`

### 📊 Balance

- **Get Balance** - Saldo disponível, aguardando e transferido

## 📁 Estrutura do Projeto

```text
beehivehub-java-sdk-runner/
├── src/main/java/br/com/paybeehive/runner/
│   ├── Cli.java                    # Menu principal
│   ├── Utils.java                  # Funções auxiliares
│   └── resources/                  # Handlers por recurso
│       ├── TransactionsMenu.java
│       ├── CustomersMenu.java
│       ├── PaymentLinksMenu.java
│       ├── RecipientsMenu.java
│       ├── BankAccountsMenu.java
│       ├── TransfersMenu.java
│       ├── CompanyMenu.java
│       └── BalanceMenu.java
├── payloads/                       # Payloads editáveis
│   └── *.json
├── output/                         # Respostas salvas automaticamente
├── .env                            # Credenciais (não versionar!)
├── .env.example                    # Template
├── pom.xml
└── README.md
```

## 🛠️ Stack Tecnológica

- **Java 17+** - Linguagem principal
- **Maven 3.8+** - Build e gerenciamento de dependências
- **Jackson** - Serialização/deserialização JSON
- **dotenv-java** - Gerenciamento de credenciais via `.env`
- **beehivehub-java-sdk** - SDK oficial via Maven Central

## 🔧 Atualizar o SDK

O runner usa o SDK publicado no Maven Central. Para atualizar para uma nova versão, edite a versão em `pom.xml`:

```xml
<dependency>
    <groupId>br.com.paybeehive</groupId>
    <artifactId>beehivehub-java-sdk</artifactId>
    <version>1.0.1</version>
</dependency>
```

Depois execute:

```bash
mvn package
```

## 💡 Exemplos de Uso

### Cenário 1: Criar e Consultar Cliente

1. Execute `mvn exec:java`
2. Escolha "Customers"
3. Escolha "Create Customer"
4. Veja o ID retornado no output salvo
5. Volte e escolha "Get Customer by ID"
6. Digite o ID
7. Veja os dados completos

### Cenário 2: Testar Diferentes Métodos de Pagamento

**Transação PIX:** Edite `payloads/transaction-create.json` com `"paymentMethod": "pix"` e execute "Create Transaction".

**Transação Parcelada:** Edite `payloads/transaction-create.json` com `"installments": 3` e execute "Create Transaction".

### Cenário 3: Criar Payment Link e Testar Pagamento

1. Edite `payloads/payment-link-create.json`
2. Execute "Create Payment Link"
3. Copie a `url` retornada no arquivo de output
4. Abra no navegador para testar
5. Use "Get Payment Link" para verificar status

## ⚠️ Notas Importantes

### Valores em Centavos

Todos os valores monetários são em centavos:

- R$ 10,00 = `1000`
- R$ 100,00 = `10000`
- R$ 1.000,00 = `1000000`

### Card Hash

Para criar transações com cartão, você precisa de um `card_hash` válido gerado no frontend. Use a biblioteca JavaScript da Beehive Hub.

### IDs Dinâmicos

Para operações que requerem IDs (GET, refund, update), copie os IDs retornados pelas operações de criação ou listagem — disponíveis nos arquivos salvos em `output/`.

### Ambiente

Certifique-se de usar as credenciais corretas:

- **Sandbox:** Para testes sem movimentação real
- **Production:** Para operações reais

## 🎯 Casos de Uso

### Validar Criação de Transação

1. Edite `payloads/transaction-create.json`
2. Varie: `amount`, `paymentMethod`, `installments`
3. Execute múltiplas vezes com dados diferentes
4. Valide os resultados

### Testar Fluxo Completo

1. Crie um cliente → Copie o ID
2. Crie um payment link → Copie a URL
3. Verifique o saldo
4. Liste transações recentes

### Validar Recebedores

1. Crie um recipient → Copie o ID
2. Liste recipients
3. Atualize configurações
4. Crie transfer usando o `recipientId`

## 📝 Convenções

- **Emails únicos:** Varie o email em cada criação
- **IDs válidos:** Use IDs reais obtidos da API
- **Payloads válidos:** Siga a estrutura da documentação oficial

## 🔍 Debug

Se encontrar erros:

1. **Verifique o payload JSON** - Sintaxe correta?
2. **Confira as credenciais** - API key válida?
3. **Veja a mensagem de erro** - API retorna detalhes
4. **Consulte a documentação** - [docs.beehivehub.io](https://docs.beehivehub.io)

## 📞 Suporte

- 📧 Email: [support@paybeehive.com.br](mailto:support@paybeehive.com.br)
- 📚 Docs: [docs.beehivehub.io](https://docs.beehivehub.io)
- 🐛 Issues: [github.com/paybeehive/beehivehub-java-sdk/issues](https://github.com/paybeehive/beehivehub-java-sdk/issues)
- 📦 SDK: [Maven Central](https://central.sonatype.com/artifact/br.com.paybeehive/beehivehub-java-sdk)

---

✨ Desenvolvido com ❤️ pela equipe Beehive Hub
