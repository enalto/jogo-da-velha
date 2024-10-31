import java.util.List;
import java.util.Objects;

public class Tabuleiro {
    private char[][] tabuleiro = {
            {'O', '-', 'X'},
            {'-', 'O', '-'},
            {'X', '-', 'O'}
    };

    private List<Jogador> jogadores;
    private Jogador jogadorAtual;
    private Jogador vencedorDaRodada;


    public Tabuleiro(List<Jogador> jogadores) {
        Objects.requireNonNull(jogadores);
        this.jogadores = jogadores;
        this.jogadorAtual = jogadores.get(0);
    }

    public void limpaTabuleiro() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = '-';
            }
        }
    }

    public void mostraTabuleiro() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(tabuleiro[i][j] + " ");
            }
            System.out.println();
        }
    }


    /**
     * Verifica se os elementos das diagonais são iguais
     *
     * @return
     */
    private boolean diagonaisIsEquals() {
        boolean diagonalPrincipalIgual = true;
        boolean diagonalSecundariaIgual = true;
        char firstElementDiagonalPrincipal = tabuleiro[0][0];
        char firstElementDiagonalSecundaria = tabuleiro[0][2];

        for (int i = 1; i < 3; i++) {
            if (tabuleiro[i][i] != firstElementDiagonalPrincipal) {
                diagonalPrincipalIgual = false;
            }
            if (tabuleiro[i][2 - i] != firstElementDiagonalSecundaria) {
                diagonalSecundariaIgual = false;
            }
        }
        if (diagonalPrincipalIgual) {
            this.setVencedorDaRodada(String.valueOf(firstElementDiagonalPrincipal));
            System.out.println("Elementos da Diagonal principal são iguais.");
        }
        if (diagonalSecundariaIgual) {
            this.setVencedorDaRodada(String.valueOf(firstElementDiagonalSecundaria));
            System.out.println("Elementos da Diagonal secundaria são iguais.");
        }
        return (diagonalPrincipalIgual || diagonalSecundariaIgual);
    }

    /**
     * Verifica se os elementos de alguma das linhas e igual
     *
     * @return
     */
    private boolean linesIsEquals() {
        boolean elementosIguais = true;
        for (int i = 0; i < 3; i++) {
            elementosIguais = true;
            char firstElement = tabuleiro[i][0];
            for (int j = 1; j < 3; j++) {
                if (tabuleiro[i][j] != firstElement) {
                    elementosIguais = false;
                    break;
                }
            }
            if (elementosIguais) {
                setVencedorDaRodada(String.valueOf(firstElement));
                System.out.println("Elementos da linha= " + (++i) + " são iguais");
                return true;
            }
        }
        return elementosIguais;
    }

    private void setVencedorDaRodada(String simbolo){
        jogadores.stream().forEach(jogador -> {
            if(simbolo.equals(jogador.getSimbolo())){
                this.vencedorDaRodada=jogador;
            }
        });
    }

    /**
     * Verifica se os elementos de alguma coluna são iguais
     *
     * @return
     */
    private boolean columnIsEquals() {
        boolean elementosIguais = true;
        for (int j = 0; j < 3; j++) {
            char firstElement = tabuleiro[0][j];
            elementosIguais = true;
            for (int i = 1; i < 3; i++) {
                if (tabuleiro[i][j] != firstElement) {
                    elementosIguais = false;
                    break;
                }
            }
            if (elementosIguais) {
                setVencedorDaRodada(String.valueOf(firstElement));
                System.out.println("Elementos da coluna= " + (++j) + " são iguais.");
                return true;
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
    public boolean isGameOver() {
        if(!isTabuleiroCheio()) {
            return (linesIsEquals() | diagonaisIsEquals() | columnIsEquals());
        }
        return false;
    }

    /**
     * Verifica se o tabuleiro está cheio
     *
     * @return
     */
    public boolean isTabuleiroCheio() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public Jogador getVencedorDaRodada() {
        return vencedorDaRodada;
    }

    /**
     * verifica se o jogo terminou empatado
     *
     * @return
     */
    public boolean isGameEmpatado() {
        var naoTemVencedor = !isGameOver();
        var tabuleiroCheio = isTabuleiroCheio();
        if (naoTemVencedor && tabuleiroCheio) {
            return true;
        }
        return false;
    }

}
