package domain;

import java.util.*;

public class Tabuleiro {

    private Jogador championPlayer;
    private boolean gameTied = false;
    private boolean gameOver = false;
    private Map<String, String> positionMap;

    private static char EMPTY_CELL = '-';
    private char[][] tabuleiro = new char[3][3];

    private String[][] instructions = {
            {"11", "12", "13"},
            {"21", "22", "23"},
            {"31", "32", "33"}
    };


    public Tabuleiro() {
        inicializarTabuleiro();
        initMapPositions();
    }

    /**
     * Inicializa o tabuleiro com celulas em branco
     * char='-'
     */
    public void inicializarTabuleiro() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = EMPTY_CELL;
            }
        }
    }

    /**
     * HasMap que contem as posições para jogar
     * Instruções que serão mostradas aos Jogadores
     */
    private void initMapPositions(){
        positionMap = new HashMap<>();
        positionMap.put("11", "linha=1, coluna=1");
        positionMap.put("12", "linha=1, coluna=2");
        positionMap.put("13", "linha=1, coluna=3");
        positionMap.put("21", "linha=2, coluna=1");
        positionMap.put("22", "linha=2, coluna=2");
        positionMap.put("23", "linha=2, coluna=3");
        positionMap.put("31", "linha=3, coluna=1");
        positionMap.put("32", "linha=3, coluna=2");
        positionMap.put("33", "linha=3, coluna=3");

    }

    /**
     *
     * Retorna o vencedor do jogo
     * @return
     */
    public Optional<Jogador> getChampionPlayer() {
        return Optional.ofNullable(championPlayer);
    }

    /**
     * Verifica se uma posição existe no tabuleiro
     * @param s
     * @return
     */
    public boolean hasPosition(String s) {
        Objects.requireNonNull(s);
        return positionMap.containsKey(s);
    }

    /**
     * Imprime o tabuleiro
     * @param charArray
     */
    public void imprimirTabuleiro(char[][] charArray) {
        if (charArray == null) {
            throw new IllegalArgumentException("Tabuleiro não pode ser nulo.");
        }
        if (charArray.length != 3) {
            throw new IllegalArgumentException("Tabuleiro deve ter 3 linhas.");
        }

        for (char[] row : charArray) {
            if (row == null || row.length != 3) {
                throw new IllegalArgumentException("Cada linha deve ter tres elementos.");
            }
        }

        System.out.println("-".repeat(13));
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(charArray[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-".repeat(13));
        }
    }

    /**
     * Imprime o tabuleiro
     */
    public void imprimirTabuleiro() {
        if (tabuleiro == null) {
            throw new IllegalArgumentException("Tabuleiro não pode ser nulo.");
        }
        if (tabuleiro.length != 3) {
            throw new IllegalArgumentException("Tabuleiro deve ter 3 linhas.");
        }

        for (char[] row : tabuleiro) {
            if (row == null || row.length != 3) {
                throw new IllegalArgumentException("Cada linha deve ter tres elementos.");
            }
        }

        System.out.println("-".repeat(13));
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(tabuleiro[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-".repeat(13));
        }
    }

    /**
     * Mostra instruções aos jogadores
     */
    public void showInstruction() {
        System.out.println("Você deve escolher uma posição dessas, que esteja livre");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(instructions[i][j] + " ");
            }
            System.out.println();
        }

        Set<Map.Entry<String, String>> entries = positionMap.entrySet();
        int count = 0;
        for (Map.Entry<String, String> entry : entries) {
            System.out.print(entry.getKey() + " ");
            System.out.print("[" + entry.getValue() + "] ");
            count++;
            if (count == 3) {
                System.out.println();
                count = 0;
            }
        }
    }

    /**
     * Joga numa determinada celula
     * @param pair
     * @param jogador
     */
    public void jogar(Pair pair, Jogador jogador) {
        Objects.requireNonNull(pair);
        Objects.requireNonNull(jogador);

        if (pair.i() < 0 || pair.i() >= 3 || pair.j() < 0 || pair.j() >= 3)
            throw new RuntimeException(String.format("%s, Escolha uma celula válida", jogador.getNome()));

        if (boardHasWinner())
            throw new RuntimeException(String.format("%s, partida já tem um vencedor", jogador.getNome()));

        if (isGameOver())
            throw new RuntimeException(String.format("%s, Jogo encerrado!", jogador.getNome()));

        if (isFullBoard())
            throw new RuntimeException(String.format("%s, Tabuleiro cheio.", jogador.getNome()));

        if (!isEmptyCell(tabuleiro, pair))
            throw new RuntimeException(String.format("%s, Posição não está livre para jogada.", jogador.getNome()));

        tabuleiro[pair.i()][pair.j()] = jogador.getSimbolo();
        updateStatus(jogador);
    }

    /**
     * Atualiza o estado da roada, se tem vencedor ou jogo empatado
     * @param jogador
     */
    private void updateStatus(Jogador jogador) {
        Objects.requireNonNull(jogador);

        if (boardHasWinner()) {
            championPlayer = jogador;
            gameOver = true;
        }
        if (isGameEmpatado()) {
            gameTied = true;
            gameOver = true;
        }

    }


    /**
     * Verifica se os elementos das diagonais são iguais
     *
     * @return
     */
    public boolean isEqualPrincipalDiagonal(char[][] charArray) {
        boolean equalPrincipalDiagonal = true;
        char firstElementPrincipalDiagonal = charArray[0][0];

        for (int i = 1; i < 3; i++) {
            if (isEmptyCell(charArray, new Pair(i, 2 - i))) {
                equalPrincipalDiagonal = false;
                break;
            }
            if (charArray[i][i] != firstElementPrincipalDiagonal) {
                equalPrincipalDiagonal = false;
            }
        }
        return equalPrincipalDiagonal;
    }

    /**
     * Verificado se os elementos da diagonal secundaria são iguais
     * @param charArray
     * @return
     */
    public boolean isEqualSecundaryDiagonal(char[][] charArray) {
        boolean equalSecundaryDiagonal = true;
        char firstElementDiagonalSecundary = charArray[0][2];

        for (int i = 1; i < 3; i++) {
            if (isEmptyCell(charArray, new Pair(i, 2 - i))) {
                equalSecundaryDiagonal = false;
                break;
            }
            if (charArray[i][2 - i] != firstElementDiagonalSecundary) {
                equalSecundaryDiagonal = false;
            }
        }
        return equalSecundaryDiagonal;
    }

    /**
     * verifica se uma determinada celula do array está em branco
     * @param array
     * @param pair
     * @return
     */
    public boolean isEmptyCell(char[][] array, Pair pair) {
        Objects.requireNonNull(pair);
        return array[pair.i()][pair.j()] == EMPTY_CELL;
    }

    /**
     * Verifica se tem alguma linha com todos os elementos iguais
     * @param charArray
     * @return
     */
    public boolean lineElementsEqual(char[][] charArray) {
        boolean elementosIguais = true;
        for (int i = 0; i < 3; i++) {

            elementosIguais = true;
            char firstElement = charArray[i][0];
            for (int j = 1; j < 3; j++) {
                if ((charArray[i][j] != firstElement) || isEmptyCell(charArray, new Pair(i, 0))) {
                    elementosIguais = false;
                    break;
                }
            }
            if (elementosIguais)
                break;
        }
        return elementosIguais;
    }

    /**
     * Verifica se os elementos de alguma coluna são iguais
     *
     * @return
     */
    public boolean columnElementsEqual(char[][] charArray) {
        boolean elementosIguais = true;
        for (int j = 0; j < 3; j++) {
            char firstElement = charArray[0][j];
            elementosIguais = true;
            for (int i = 1; i < 3; i++) {
                if ((charArray[i][j] != firstElement) || isEmptyCell(charArray, new Pair(0, j))) {
                    elementosIguais = false;
                    break;
                }
            }
            if (elementosIguais)
                break;
        }
        return elementosIguais;
    }

    /**
     * Verifica se é final de jogo
     * Linhas, diagonais e colunas.
     *
     * @return
     */
    public boolean boardHasWinner() {
        return (lineElementsEqual(this.tabuleiro) ||
                isEqualPrincipalDiagonal(this.tabuleiro) ||
                isEqualSecundaryDiagonal(this.tabuleiro) ||
                columnElementsEqual(this.tabuleiro));
    }

    /**
     * Verifica se o tabuleiro está cheio
     *
     * @return
     */
    public boolean isFullBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * verifica se o jogo terminou empatado
     * @return
     */
    public boolean isGameEmpatado() {
        if (!boardHasWinner() && isFullBoard()) {
            return true;
        }
        return false;
    }

    /**
     * Verifica se é fim de jogo
     * @return
     */
    public boolean isGameOver() {
        return (boardHasWinner() || isFullBoard());
    }

}
