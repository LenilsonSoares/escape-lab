# Como contribuir

## Preparar o ambiente

É necessário ter o JDK 21 instalado. O Maven é fornecido pelo wrapper do projeto.

```powershell
.\mvnw.cmd clean verify
```

## Fluxo recomendado

1. Atualize a branch principal.
2. Crie uma branch com nome descritivo, como `feature/cartoes-de-acesso`.
3. Faça mudanças pequenas e acompanhadas de testes.
4. Execute `clean verify` antes do commit.
5. Use uma mensagem objetiva, como `feat: adiciona cartão de acesso`.

## Regras de código

- Dependências apontam para dentro: `presentation → application/domain` e `application → domain`.
- Classes de domínio e aplicação não importam JavaFX.
- Dependências são recebidas pelo construtor quando precisam ser substituídas em testes.
- Métodos e classes possuem uma responsabilidade clara.
- Valores inválidos falham cedo com mensagens explícitas.
- Não crie abstrações sem um caso de uso real.
- Não coloque classes em pacotes genéricos como `utils` ou `helpers`.
- Toda correção ou regra nova deve ter um teste correspondente.

Consulte [docs/architecture.md](docs/architecture.md) antes de adicionar uma nova camada ou integração.

## Antes de enviar

- O jogo inicia com `.\mvnw.cmd javafx:run`.
- WASD e setas funcionam corretamente.
- `.\mvnw.cmd clean verify` termina com `BUILD SUCCESS`.
- `target/` e arquivos da IDE não entram no commit.
- README e documentação foram atualizados quando o comportamento mudou.
