package br.com.escapelab;

import br.com.escapelab.presentation.javafx.EscapeLabApplication;

/**
 * Launcher separado da aplicação JavaFX para facilitar execução e empacotamento.
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        EscapeLabApplication.launchApplication(args);
    }
}
