import domain.Jogador;
import domain.Pair;
import domain.Rodada;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static domain.Rodada.*;

public class Aplication {

    public static void main(String[] args) {
        Aplication aplication = new Aplication();
        aplication.run();
    }

    private void run() {
        mostrarInstrucoes();


        Scanner scanner = new Scanner(System.in);
        while (true) {

            Optional<List<Jogador>> jogadores = leituraDadosDosJogadores();
            if (jogadores.isEmpty())
                break;

            BuilderRodada builder = new BuilderRodada();
            Rodada rodada = builder.addJogador(jogadores.get().get(0))
                    .addJogador(jogadores.get().get(1))
                    .build();

            Jogador jogador = rodada.getJogadorInicial()
                    .orElseThrow(() -> new RuntimeException("Jogador inicial não definido."));

            System.out.println("Jogo será iniciado por: " + jogador.getNome());
            System.out.println(jogador.getNome() + " seu simbolo é= " + jogador.getSimbolo());

            rodada.setJogadorAtual(jogador);

            while (true) {
                rodada.showInstruction();

                String position = ValidarInput.validateInput(scanner, "Entre com posicao, [linha,coluna]",
                        (s) -> !s.isEmpty() && rodada.hasPosition(s));

                int linha = Integer.parseInt(String.valueOf(position.charAt(0)));
                int coluna = Integer.parseInt(String.valueOf(position.charAt(1)));

                try {
                    rodada.jogar(jogador, new Pair(linha, coluna));
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }

            }

        }


    }


}

private Optional<List<Jogador>> leituraDadosDosJogadores() {
    Scanner scanner = new Scanner(System.in);
    Optional<List<Jogador>> listResult = Optional.empty();

    String nomeA = ValidarInput
            .validateInput(scanner, "Nome do primeiro jogador: ",
                    (s) -> s.matches("[a-zA-Z0-9]{3,}") || s.isEmpty());
    if (nomeA.isEmpty())
        return listResult;

    Jogador jogadorA = new Jogador(nomeA, 'X');

    String nomeB = ValidarInput
            .validateInput(scanner, "Nome do segundo jogador: ",
                    (s) -> s.matches("[a-zA-Z0-9]{3,}") || s.isEmpty());
    if (nomeB.isEmpty())
        return listResult;

    Jogador jogadorB = new Jogador(nomeB, 'O');

    List<Jogador> jogadores = new ArrayList<>(2);
    jogadores.add(jogadorA);
    jogadores.add(jogadorB);
    return Optional.of(jogadores);
}


private void mostrarInstrucoes() {

    String init = """
            
                                                                           \s
                |                       |         .    ,     |    |        \s
                |,---.,---.,---.    ,---|,---.    |    |,---.|    |---.,---.
                ||   ||   ||   |    |   |,---|     \\  / |---'|    |   |,---|
            `---'`---'`---|`---'    `---'`---^      `'  `---'`---'`   '`---^
                      `---'                                                \s
            
            """;
    System.out.println("Bem vindo!");
    System.out.println(init);
}
}
