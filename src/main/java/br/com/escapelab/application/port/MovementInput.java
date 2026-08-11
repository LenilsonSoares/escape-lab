package br.com.escapelab.application.port;

import br.com.escapelab.domain.model.Direction;

/**
 * Porta pela qual a aplicação consulta a intenção atual de movimento.
 */
@FunctionalInterface
public interface MovementInput {

    Direction currentDirection();
}
