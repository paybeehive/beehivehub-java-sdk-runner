# 🚀 Quick Start Guide

## Setup em 2 passos

### 1️⃣ Configurar .env

Crie o arquivo `.env` na raiz:

```env
BEEHIVE_SECRET_KEY=sk_test_abc123xyz...
```

### 2️⃣ Executar

```bash
mvn exec:java
```

## 💡 Primeiro Uso

### Exemplo: Consultar Saldo

1. Execute `mvn exec:java`
2. Escolha "📊 Balance"
3. Veja o saldo disponível

### Exemplo: Listar Clientes

1. Execute `mvn exec:java`
2. Escolha "👥 Customers"
3. Escolha "List Customers"
4. Digite o email para buscar (ex: `cliente@example.com`)

### Exemplo: Criar Cliente

1. Edite `payloads/customer-create.json`:

```json
{
  "name": "Seu Nome",
  "email": "seuemail@example.com",
  "documents": [{ "type": "cpf", "number": "12345678900" }],
  "phone_numbers": ["+5511999999999"]
}
```

1. Execute `mvn exec:java`
2. Escolha "👥 Customers"
3. Escolha "Create Customer"
4. Copie o ID retornado

## 🎯 Testar Diferentes Cenários

### Variação 1: Mudar valores

Edite `payloads/transaction-create.json`:

- Mude `amount` de `10000` para `50000`
- Execute e veja a diferença

### Variação 2: Usar exemplos prontos

Copie um exemplo para o payload principal:

```bash
cp payloads/examples/transaction-pix.json payloads/transaction-create.json
```

Execute "Create Transaction" e terá uma transação PIX.

### Variação 3: Criar Payment Link

Edite `payloads/payment-link-create.json` com os dados desejados e execute "Create Payment Link" no menu.

## 🔄 Workflow Recomendado

### Para validar CRUD completo

1. **Create** - Crie um recurso e copie o ID
2. **Get** - Busque pelo ID para confirmar
3. **List** - Liste para ver na coleção
4. **Update** (se disponível) - Modifique e confirme
5. **Delete** (se disponível) - Remova se necessário

## ⚠️ Dicas

- **Ctrl+C** sai do CLI a qualquer momento
- **IDs copiados** podem ser colados diretamente
- **Erros da API** são mostrados formatados
- **Payloads inválidos** mostram o erro específico
- Use `BEEHIVE_ENVIRONMENT=sandbox` no `.env` para testar sem dinheiro real

## 📚 Documentação Completa

Veja o [README.md](README.md) principal para mais detalhes.

---

💡 **Pronto para começar? Execute `mvn exec:java`!**
