package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TabuleiroTest {

    private Tabuleiro tabuleiro;

    @BeforeEach
    void setUp() {
        tabuleiro = new Tabuleiro();
    }

    @Test
    void inicializarTabuleiro() {

    }

    @Test
    void getChampionPlayer() {
    }

    @Test
    void hasPosition() {
    }

    @Test
    void imprimirTabuleiro() {
    }

    @Test
    void jogar() {
    }

    @Test
    void boardHasWinner() {
    }

    @Test
    void isFullBoard() {
    }

    @Test
    void isGameEmpatado() {
    }

    @Test
    void isGameOver() {
    }

    @Test
    void testLineAllElementsEqual() {
        Tabuleiro tabuleiro = new Tabuleiro();
        // Test case 1: All elements in a row are equal
        char[][] line1 = {
                {'X', 'X', 'X'},
                {'O', 'O', '-'},
                {'-', '-', '-'}
        };
        Assertions.assertTrue(tabuleiro.lineElementsEqual(line1));
    }

    @Test
    public void testLineAllElementsDifferent() {
        char[][] charArray = {
                {'X', 'O', 'X'},
                {'O', 'X', 'O'},
                {'X', 'O', 'X'}
        };
        boolean elementsNotEqual = tabuleiro.lineElementsEqual(charArray);
        assertFalse(elementsNotEqual);
    }


    @Test
    public void testEmptyRow() {
        char[][] charArray = {
                {'-', '-', '-'},
                {'O', 'O', 'O'},
                {'X', 'X', 'X'}
        };
        boolean elementsEqual = tabuleiro.lineElementsEqual(charArray);
        assertTrue(elementsEqual);
    }


    @Test
    public void testRowWithMixedElements() {
        char[][] charArray = {
                {'X', 'O', 'X'},
                {'O', 'X', 'O'},
                {'X', 'O', 'X'}
        };
        boolean elementsMixed = tabuleiro.lineElementsEqual(charArray);
        assertFalse(elementsMixed);
    }

    @Test
    public void testRowAllElementsEmpty() {
        char[][] charArray = {
                {'-', '-', '-'},
                {'-', '-', '-'},
                {'-', '-', '-'}
        };
        boolean elementsEmpty = tabuleiro.lineElementsEqual(charArray);
        assertFalse(elementsEmpty);
    }

    @Test
    void testEmptyAllCell() {
        Tabuleiro tabuleiro = new Tabuleiro();
        char[][] array = {
                {'-', 'O', '-'},
                {'-', '-', '-'},
                {'-', 'X', 'O'}
        };
        boolean emptyCell = tabuleiro.isEmptyCell(array, new Pair(2, 0));
        Assertions.assertTrue(emptyCell);
    }

    @Test
    void testEmptyCell() {
        Tabuleiro tabuleiro = new Tabuleiro();
        char[][] array = {
                {'O', 'O', '-'},
                {'-', '-', '-'},
                {'X', 'X', 'O'}
        };
        boolean emptyCell = tabuleiro.isEmptyCell(array, new Pair(0, 0));
        Assertions.assertFalse(emptyCell);
    }

    @Test
    public void testAllElementsEqual() {
        Tabuleiro tabuleiro = new Tabuleiro();
        char[][] charArray = {
                {'X', 'X', 'X'},
                {'O', 'X', 'O'},
                {'O', 'X', 'O'}
        };
        boolean result = tabuleiro.columnElementsEqual(charArray);
        Assertions.assertTrue(result);
    }

    @Test
    public void testAllElementsDifferent() {
        char[][] charArray = {
                {'X', 'O', 'X'},
                {'O', 'X', 'O'},
                {'X', 'O', 'X'}
        };
        boolean elementsEqual = tabuleiro.columnElementsEqual(charArray);
        assertFalse(elementsEqual);
    }

    @Test
    public void testEmptyColumn() {
        char[][] charArray = {
                {'X', '-', 'X'},
                {'O', '-', 'O'},
                {'X', '-', 'X'}
        };
        boolean elementsEqual = tabuleiro.columnElementsEqual(charArray);
        assertFalse(elementsEqual);
    }

    @Test
    public void testColumnWithMixedElements() {
        char[][] charArray = {
                {'X', 'X', 'X'},
                {'O', 'X', 'O'},
                {'X', 'O', 'X'}
        };
        boolean elementsEqual = tabuleiro.columnElementsEqual(charArray);
        assertFalse(elementsEqual);
    }

    @Test
    public void testColumnAllElementsEmpty() {
        char[][] charArray = {
                {'-', '-', '-'},
                {'-', '-', '-'},
                {'-', '-', '-'}
        };
        boolean elementsEqual = tabuleiro.columnElementsEqual(charArray);
        assertFalse(elementsEqual);
    }


    @Test
    public void testAllElementsEqualSecondaryDiagonal() {
        char[][] charArray = {
                {'X', 'O', 'X'},
                {'O', 'X', 'O'},
                {'X', 'O', '-'}
        };
        boolean equalSecundaryDiagonal = tabuleiro.isEqualSecundaryDiagonal(charArray);
        assertTrue(equalSecundaryDiagonal);
    }

    @Test
    public void testAllElementsDifferentSecondaryDiagonal() {
        char[][] charArray = {
                {'X', 'O', 'O'},
                {'O', 'X', 'O'},
                {'X', 'O', 'X'}
        };
        boolean equalSecundaryDiagonal = tabuleiro.isEqualSecundaryDiagonal(charArray);

        assertFalse(equalSecundaryDiagonal);
    }

    @Test
    public void testEmptySecondaryDiagonal() {
        char[][] charArray = {
                {'X', 'O', '-'},
                {'O', 'X', 'O'},
                {'X', 'O', 'X'}
        };
        boolean equalSecundaryDiagonal = tabuleiro.isEqualSecundaryDiagonal(charArray);
        assertFalse(equalSecundaryDiagonal);
    }

    @Test
    public void testMixedElementsSecondaryDiagonal() {
        char[][] charArray = {
                {'X', 'O', 'X'},
                {'O', '-', 'O'},
                {'X', 'O', 'X'}
        };
        boolean equalSecundaryDiagonal = tabuleiro.isEqualSecundaryDiagonal(charArray);

        assertFalse(equalSecundaryDiagonal);
    }

    @Test
    public void testAllElementsEmpty() {
        char[][] charArray = {
                {'-', '-', '-'},
                {'-', '-', '-'},
                {'-', '-', '-'}
        };
        boolean equalSecundaryDiagonal = tabuleiro.isEqualSecundaryDiagonal(charArray);
        assertFalse(equalSecundaryDiagonal);
    }


}