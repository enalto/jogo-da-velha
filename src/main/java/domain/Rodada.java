package domain;

import java.util.*;

public class Rodada {
    private List<Jogador> jogadores;
    private Queue<Jogador> jogadoresQueue = new LinkedList<>();
    private final Tabuleiro tabuleiro = new Tabuleiro();
    private Jogador jogadorVencedor;
    private boolean empate = false;
    private boolean rodadaEncerrada = false;


    private Rodada() {
    }

    private Rodada(BuilderRodada builder) {
        this.jogadores = builder.jogadores;
        start();
    }

    public void start() {
        if (jogadores.size() < 2)
            throw new RuntimeException("Precisamos de pelo menos dois jogadores para iniciar.");
        embaralharListaInicial(this.jogadores);
        montarQueueDejogadores();
    }

    public Optional<Jogador> getJogadorVencedor() {
        return Optional.ofNullable(jogadorVencedor);
    }

    public void showInstruction() {
        tabuleiro.showInstruction();
    }

    public boolean gameOver() {
        return tabuleiro.isGameOver();
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public boolean isRodadaEmpatada() {
        return empate;
    }

    public void montarQueueDejogadores() {
        if (!jogadoresQueue.isEmpty())
            throw new RuntimeException("Fila de jogadores não está vazia");
        jogadoresQueue.addAll(this.jogadores);
    }

    public void jogar(Jogador jogador, Pair pair) {

        Objects.requireNonNull(jogador);
        Objects.requireNonNull(pair);
        if (rodadaEncerrada) {
            throw new RuntimeException("Rodada encerrada!");
        }
        try {
            tabuleiro.jogar(pair, jogador);
            jogadoresQueue.poll();
            updateState();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean hasPosition(String s) {
        return tabuleiro.hasPosition(s);
    }

    private void updateState() {
        boolean boardHasWinner = tabuleiro.boardHasWinner();
        boolean fullBoard = tabuleiro.isFullBoard();
        rodadaEncerrada = (boardHasWinner || fullBoard);
        empate = (!boardHasWinner && fullBoard);
        if (jogadoresQueue.isEmpty()) {
            jogadoresQueue.addAll(this.jogadores);
        }
        if (boardHasWinner) {
            Optional<Jogador> championPlayer = tabuleiro.getChampionPlayer();
            championPlayer.ifPresent(this::setJogadorVencedor);
        }
    }

    public Optional<Jogador> getJogadorDaVez() {
        return Optional.ofNullable(jogadoresQueue.peek());
    }

    private void setJogadorVencedor(Jogador jogadorVencedor) {
        this.jogadorVencedor = jogadorVencedor;
    }

    /**
     * Metodo que embaralha a lista para definir quem inicia o jogo
     *
     * @param List<jogadores>
     * @return
     */
    private void embaralharListaInicial(List<Jogador> jogadores) {
        if (jogadores.size() < 2) {
            throw new IllegalArgumentException("Necessita de pelo menos dois jogadores para uma rodada!");
        }
        Collections.shuffle(jogadores);
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

}
