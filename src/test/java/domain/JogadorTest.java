package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JogadorTest {

    @Test
    void testValidConstructor() {
        Jogador jogador = new Jogador("Alice", 'O');
        Assertions.assertEquals("Alice", jogador.getNome());
        Assertions.assertEquals('O', jogador.getSimbolo());
    }

    @Test
    public void testEqualsAndHashCode() {
        Jogador jogador1 = new Jogador("Alice", 'X');
        Jogador jogador2 = new Jogador("Bob", 'O');
        Jogador jogador3 = new Jogador("Alice", 'X');

        Assertions.assertEquals(jogador1, jogador1);
        Assertions.assertNotEquals(jogador1, jogador2);
        Assertions.assertEquals(jogador1, jogador3);

        Assertions.assertEquals(jogador1.hashCode(), jogador1.hashCode());
        Assertions.assertEquals(jogador1.hashCode(), jogador3.hashCode());
        Assertions.assertNotEquals(jogador1.hashCode(), jogador2.hashCode());
    }

}