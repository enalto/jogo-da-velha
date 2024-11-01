package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Tabuleiro {

    private Jogador championPlayer;
    private boolean gameTied = false;
    private Map<String, String> positionMap;

    private char[][] tabuleiro = {
            {'_', '_', '_'},
            {'_', '_', '_'},
            {'_', '_', '_'}
    };

    private String[][] instructions = {
            {"11", "12", "13"},
            {"21", "22", "23"},
            {"31", "32", "33"}
    };


    public Tabuleiro() {
        clear();
        positionMap = new HashMap<>();
    }

    public void clear() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = '_';
            }
        }
    }

    public Optional<Jogador> getChampionPlayer() {
        return Optional.ofNullable(championPlayer);
    }

    public boolean hasPosition(String s) {
        return positionMap.containsKey(s);
    }

    public void show() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(tabuleiro[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void showInstruction() {

        positionMap.put("11", "linha=1, coluna=1");
        positionMap.put("12", "linha=1, coluna=2");
        positionMap.put("13", "linha=1, coluna=3");
        positionMap.put("21", "linha=2, coluna=1");
        positionMap.put("22", "linha=2, coluna=2");
        positionMap.put("23", "linha=2, coluna=3");
        positionMap.put("31", "linha=3, coluna=1");
        positionMap.put("32", "linha=3, coluna=2");
        positionMap.put("33", "linha=3, coluna=3");
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

    public void jogar(Pair pair, Jogador jogador) {
        if (pair.i() < 0 || pair.i() >= 3 || pair.j() < 0 || pair.j() >= 3)
            throw new IndexOutOfBoundsException();

        if (boardHasWinner())
            throw new RuntimeException("Partida já tem um vencedor");

        if (isGameOver())
            throw new RuntimeException("Jogo encerrado!");

        if (isFullBoard())
            throw new RuntimeException("Tabuleiro cheio.");

        if (!isEmptyCell(pair))
            throw new RuntimeException("Posição não está livre para jogada.");

        tabuleiro[pair.i()][pair.j()] = jogador.getSimbolo();
        updateStatus(jogador);
    }

    private void updateStatus(Jogador jogador) {
        if (boardHasWinner())
            championPlayer = jogador;
        if (isGameEmpatado()) {
            gameTied = true;
        }
    }


    /**
     * Verifica se os elementos das diagonais são iguais
     *
     * @return
     */
    private boolean isEqualPrincipalDiagonal() {
        boolean equalPrincipalDiagonal = true;
        char firstElementPrincipalDiagonal = tabuleiro[0][0];

        for (int i = 1; i < 3; i++) {
            if (isEmptyCell(new Pair(i, 2 - 1))) {
                equalPrincipalDiagonal = false;
                continue;
            }
            if (tabuleiro[i][i] != firstElementPrincipalDiagonal) {
                equalPrincipalDiagonal = false;
            }
        }
        return equalPrincipalDiagonal;
    }

    private boolean isEqualSecundaryDiagonal() {
        boolean equalSecundaryDiagonal = true;
        char firstElementDiagonalSecundary = tabuleiro[0][2];

        for (int i = 1; i < 3; i++) {
            if (isEmptyCell(new Pair(i, 2 - 1))) {
                equalSecundaryDiagonal = false;
                continue;
            }
            if (tabuleiro[i][2 - i] != firstElementDiagonalSecundary) {
                equalSecundaryDiagonal = false;
            }
        }
        return equalSecundaryDiagonal;
    }

    /**
     * Verifica se os elementos de alguma das linhas e igual
     *
     * @return
     */

    private boolean isEmptyCell(Pair pair) {
        return tabuleiro[pair.i()][pair.j()] == '_';
    }

    private boolean lineElementsEqual() {
        boolean elementosIguais = true;
        for (int i = 0; i < 3; i++) {
            if (isEmptyCell(new Pair(i, 0))) {
                elementosIguais = false;
                break;
            }
            elementosIguais = true;
            char firstElement = tabuleiro[i][0];
            for (int j = 1; j < 3; j++) {
                if (tabuleiro[i][j] != firstElement) {
                    elementosIguais = false;
                    break;
                }
            }
        }
        return elementosIguais;
    }

    /**
     * Verifica se os elementos de alguma coluna são iguais
     *
     * @return
     */
    private boolean columnElementsEqual() {
        boolean elementosIguais = true;
        for (int j = 0; j < 3; j++) {
            char firstElement = tabuleiro[0][j];
            if (isEmptyCell(new Pair(0, j))) {
                elementosIguais = false;
                break;
            }
            elementosIguais = true;
            for (int i = 1; i < 3; i++) {
                if (tabuleiro[i][j] != firstElement) {
                    elementosIguais = false;
                    break;
                }
            }
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
        return (lineElementsEqual() ||
                isEqualPrincipalDiagonal() ||
                isEqualSecundaryDiagonal() ||
                columnElementsEqual());
    }

    /**
     * Verifica se o tabuleiro está cheio
     *
     * @return
     */
    public boolean isFullBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == '_') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * verifica se o jogo terminou empatado
     *
     * @return
     */
    public boolean isGameEmpatado() {
        if (!boardHasWinner() && isFullBoard()) {
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return (boardHasWinner() || isFullBoard());
    }

}
