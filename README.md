# Escape Lab

Projeto acadêmico de um jogo de ação e aventura em perspectiva top-down, desenvolvido com Java e JavaFX.

## Sobre o projeto

Escape Lab se passa em um laboratório de pesquisa que sofreu uma falha em seu sistema de segurança. Após o incidente, os robôs responsáveis pela proteção do local passaram a atacar qualquer pessoa encontrada.

O jogador controla um funcionário preso no laboratório, que deverá explorar salas e corredores, encontrar cartões de acesso, evitar os robôs e desligar o sistema de segurança para escapar.

## Objetivo

Desenvolver um jogo 2D de exploração e fuga utilizando cenário baseado em tiles, perspectiva top-down e estilo visual inspirado em pixel art.

## Primeira entrega

A primeira versão executável já contém:

- [x] Janela criada com JavaFX
- [x] Canvas e GraphicsContext
- [x] Game loop utilizando AnimationTimer
- [x] Cálculo e utilização de delta time
- [x] Entidade do jogador desenhada na tela
- [x] Movimentação com WASD e setas
- [x] Separação entre input, update e render
- [x] Exibição de posição, FPS e delta time para depuração
- [x] Código organizado em várias classes

## Funcionalidades planejadas

- Exploração do laboratório
- Cenários construídos com tiles
- Cartões de acesso
- Portas e áreas bloqueadas
- Robô patrulha
- Drone de vigilância
- Robô guarda
- Detecção do jogador por visão e som
- Sistema de perseguição
- Condição de vitória e derrota

## Controles

| Tecla | Ação |
|---|---|
| W ou ↑ | Mover para cima |
| S ou ↓ | Mover para baixo |
| A ou ← | Mover para a esquerda |
| D ou → | Mover para a direita |

## Tecnologias

- Java 21
- JavaFX
- Maven
- Maven Wrapper
- JUnit 5 e ArchUnit
- Git e GitHub

## Como executar

### Requisito

Ter o JDK 21 instalado.

### Windows

```powershell
.\mvnw.cmd javafx:run
```

### Linux ou macOS

```bash
sh mvnw javafx:run
```

Na primeira execução, o Maven Wrapper baixa o Maven e as dependências do JavaFX automaticamente. É necessário estar conectado à internet.

## Como testar

No Windows:

```powershell
.\mvnw.cmd clean verify
```

Os testes verificam domínio, casos de uso, delta time, FPS, controles, limites do mundo e as próprias fronteiras da arquitetura.

## Arquitetura

O código segue uma Clean Architecture enxuta:

```text
presentation/javafx → application → domain
          └──────────────────────→ domain
```

- `domain` contém regras puras do jogo e não conhece JavaFX.
- `application` coordena a partida e define portas para os adapters.
- `presentation/javafx` contém janela, teclado, `AnimationTimer` e renderização; como camada externa, pode depender diretamente das camadas internas.
- `configuration` centraliza os parâmetros usados na composição.

As dependências são ligadas explicitamente por construtor. Testes ArchUnit impedem dependências invertidas e ciclos entre as camadas.

A explicação completa está em [docs/architecture.md](docs/architecture.md).

## Estrutura atual

```text
src/main/java/br/com/escapelab/
├── Main.java
├── configuration/
│   └── GameConfiguration.java
├── domain/model/
│   ├── Direction.java
│   ├── Player.java
│   ├── Position.java
│   └── WorldBounds.java
├── application/
│   ├── game/
│   ├── port/
│   └── time/
└── presentation/javafx/
    ├── EscapeLabApplication.java
    ├── JavaFxGameLoop.java
    ├── input/
    └── render/
```

Os materiais fornecidos pelo grupo — o PDF com a temática e a história e a imagem com os critérios da prática — estão na pasta `docs/`.

As regras para colaboração estão em [CONTRIBUTING.md](CONTRIBUTING.md).

## Enviar pelo GitHub Desktop

Depois de conferir o jogo:

1. Abra este repositório no GitHub Desktop.
2. Na caixa **Summary**, use uma mensagem como `feat: cria primeira versão executável`.
3. Clique em **Commit to main**.
4. Clique em **Push origin** para enviar o commit ao GitHub.
