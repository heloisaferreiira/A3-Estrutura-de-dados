package a3.nicolas.model.enums;

/*
 * Enum que representa os estados possiveis de um chamado.
 *
 * ABERTO: chamado criado e aguardando na fila.
 * EM_ATENDIMENTO: chamado retirado da fila para atendimento.
 * FECHADO: chamado finalizado e enviado para o historico.
 */
public enum StatusChamado {
    ABERTO,
    EM_ATENDIMENTO,
    FECHADO
}
