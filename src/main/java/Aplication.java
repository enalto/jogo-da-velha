import java.util.Arrays;

public class Aplication {

    public static void main(String[] args) {

        Jogador jogador1 = new Jogador("Jose", "X");
        Jogador jogador2 = new Jogador("Pedro", "O");

        Tabuleiro tabuleiro = new Tabuleiro(Arrays.asList(jogador1, jogador2));
        tabuleiro.mostraTabuleiro();
        boolean gameOver = (tabuleiro.isGameOver());
        System.out.println("Vencedor= "+tabuleiro.getVencedorDaRodada());
        System.out.println(gameOver);
    }
}
