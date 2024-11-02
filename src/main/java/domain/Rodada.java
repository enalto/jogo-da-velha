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

    /**
     * Inicializa a rodada
     */
    public void start() {
        if (jogadores.size() < 2)
            throw new RuntimeException("Precisamos de pelo menos dois jogadores para iniciar.");
        embaralharListaInicial(this.jogadores);
        montarQueueDejogadores();
    }

    /**
     * Retorna o jogador vencedor
     * @return
     */
    public Optional<Jogador> getJogadorVencedor() {
        return Optional.ofNullable(jogadorVencedor);
    }

    /**
     * Mostra instruções para o jogador
     */
    public void showInstruction() {
        tabuleiro.showInstruction();
    }

    /**
     * Verifica se é fim de jogo
     * @return
     */
    public boolean gameOver() {
        return tabuleiro.isGameOver();
    }

    /**
     * Metodo retorna o tabuleiro da rodada
     * @return
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    /**
     * Metodo retorna se o jogo esta empatado
     * @return
     */
    public boolean isRodadaEmpatada() {
        return empate;
    }

    /**
     * Monta a fila de jogadores
     */
    public void montarQueueDejogadores() {
        if (!jogadoresQueue.isEmpty())
            throw new RuntimeException("Fila de jogadores não está vazia");
        jogadoresQueue.addAll(this.jogadores);
    }


    /**
     * Joga numa celula que esteja livre
     * @param jogador
     * @param pair
     */
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

    /**
     * Verifica se a posição existe no tabuleiro
     * @param s
     * @return
     */
    public boolean hasPosition(String s) {
        return tabuleiro.hasPosition(s);
    }

    /**
     * Atualiza o estado do tabuleiro
     * Se já tem vencedor
     * se o tabuleiro já está cheio
     */
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

    /**
     * Retorna o jogador da vez
     * @return
     */
    public Optional<Jogador> getJogadorDaVez() {
        return Optional.ofNullable(jogadoresQueue.peek());
    }

    /**
     * Metodo set para o jogador vencedor
     * @param jogadorVencedor
     */
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

    /**
     * Builder do objeto Rodada
     */
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
