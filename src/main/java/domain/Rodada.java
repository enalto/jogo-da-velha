package domain;

import java.util.*;

public class Rodada {
    private List<Jogador> jogadores;
    private final Tabuleiro tabuleiro = new Tabuleiro();
    private Jogador jogadorVencedor;
    private boolean empate = false;
    private boolean rodadaEncerrada = false;
    private Jogador jogadorInicial;
    private Jogador jogadorAtual;


    private Rodada() {
    }

    private Rodada(BuilderRodada builder) {
        this.jogadores = builder.jogadores;
        start();
    }

    public void start() {
        sortearJogadorInicial();
    }

    public Optional<Jogador> getJogadorVencedor() {
        return Optional.ofNullable(jogadorVencedor);
    }

    public void showInstruction() {
        tabuleiro.showInstruction();
    }

    public void setJogadorAtual(Jogador jogadorAtual) {
        this.jogadorAtual = jogadorAtual;
    }

    public void jogar(Jogador jogador, Pair pair) {

        Objects.requireNonNull(jogador);
        Objects.requireNonNull(pair);

        if (rodadaEncerrada)
            throw new RuntimeException("Rodada encerrada!");
        try {
            tabuleiro.jogar(pair, jogador);
            jogadorAtual = jogador;
            updateState();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao jogar o jogador!", e);
        }
    }

    public boolean hasPosition(String s) {
        return tabuleiro.hasPosition(s);
    }

    private void updateState() {
        if (tabuleiro.boardHasWinner())
            tabuleiro.getChampionPlayer().ifPresent(this::setJogadorVencedor);
        if (tabuleiro.isFullBoard())
            rodadaEncerrada = tabuleiro.isFullBoard();
    }

    public Optional<Jogador> getJogadorAtual() {
        return Optional.ofNullable(jogadorAtual);
    }

    public Optional<Jogador> getJogadorInicial() {
        return Optional.ofNullable(jogadorInicial);
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    private void setJogadorVencedor(Jogador jogadorVencedor) {
        this.jogadorVencedor = jogadorVencedor;
    }

    private void sortearJogadorInicial() {
        if (jogadores.size() < 2) {
            throw new IllegalArgumentException("Necessita de dois jogadores para uma rodada!");
        }
        int i = getAnInt(0, 1);
        this.jogadorInicial = jogadores.get(i);
        jogadorAtual = jogadorInicial;
    }

    private int getAnInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static class BuilderRodada {
        private List<Jogador> jogadores = new ArrayList<>(2);

        public BuilderRodada addJogador(Jogador jogador) {
            if (jogadores.size() == 2) {
                throw new IllegalArgumentException("Só são permitidos dois jogadores");
            }
            jogadores.add(jogador);
            return this;
        }

        public Rodada build() {
            if (jogadores.size() != 2) {
                throw new IllegalArgumentException("São necessários exatamente dois jogadores");
            }
            return new Rodada(this);
        }
    }

    private void verificarFimDeJogo() {
        if (tabuleiro.isGameOver()) {
            rodadaEncerrada = true;

            if (tabuleiro.boardHasWinner())
                setJogadorVencedor(tabuleiro.getChampionPlayer()
                        .orElseThrow(() -> new RuntimeException("Game Over sem vencedor!!!")));

        } else if (tabuleiro.isGameEmpatado()) {
            empate = true;
            jogadorVencedor = null;
        }
    }
}
