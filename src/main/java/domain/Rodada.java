package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Rodada {
    private List<Jogador> jogadores;
    private final Tabuleiro tabuleiro = new Tabuleiro();
    private Jogador jogadorVencedor;
    private boolean empate = false;
    private boolean rodadaEncerrada = false;
    private Jogador inicial;
    private Jogador jogadorAtual;


    private Rodada(Builder builder) {
        this.jogadores = builder.jogadores;
    }


    private void setVencedorDaRodada(String simbolo) {
        jogadores.stream().forEach(jogador -> {
            if (simbolo.equals(jogador.getSimbolo())) {
            }
        });
    }

    public Jogador getJogadorVencedor() {
        return jogadorVencedor;
    }

    public void jogar(Jogador jogador, Pair pair) {

        Objects.requireNonNull(jogador);
        Objects.requireNonNull(pair);

        if (rodadaEncerrada)
            throw new RuntimeException("Rodada encerrada!");

        if (!jogador.equals(jogadorAtual)) {
            jogadorAtual = jogador;
            tabuleiro.jogar(pair, jogador);
        }
        updateState();
    }

    private void updateState() {
        if (tabuleiro.boardHasWinner()) {
            setVencedorDaRodada();
        }
        if (tabuleiro.isFullBoard())
            rodadaEncerrada = tabuleiro.isFullBoard();

    }


    private static class Builder {
        private List<Jogador> jogadores = new ArrayList<Jogador>(2);

        public Builder addJogador(Jogador jogador) {
            jogadores.add(jogador);
            return this;
        }

        public Rodada builder() {
            return new Rodada(this);
        }
    }

    private void sortearJogadorInicial() {
        if (jogadores.size() < 2) {
            throw new IllegalArgumentException("Necessita de dois jogadores para uma rodada!");
        }
        Random random = new Random();
        int min = 1, max = 2;
        int i = random.nextInt(max - min + 1) + min;
        this.inicial = jogadores.get(i);
    }
}
