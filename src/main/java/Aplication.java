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
        System.out.println("-".repeat(60));

        Scanner scanner = new Scanner(System.in);
        Optional<List<Jogador>> jogadores = Optional.empty();
        boolean continuar = false;

        while (true) {
            if (!continuar)
                jogadores = leituraDadosDosJogadores();

            if (jogadores.isEmpty())
                throw new RuntimeException("Erro fatal...");

            BuilderRodada builder = new BuilderRodada();
            Rodada rodada = builder.addJogador(jogadores.get().get(0))
                    .addJogador(jogadores.get().get(1))
                    .build();

            Optional<Jogador> jogadorDaVez = rodada.getJogadorDaVez();
            if (jogadorDaVez.isEmpty())
                throw new RuntimeException("Jogador da vez não existe.");

            System.out.println("-".repeat(60));

            String messageFormatted = String.format("Rodada será iniciado pelo jogador [%s, Simbolo= %c]",
                    jogadorDaVez.get().getNome().toUpperCase(), jogadorDaVez.get().getSimbolo());

            System.out.println(messageFormatted);

            rodada.showInstruction();
            while (!rodada.gameOver()) {

                //rodada.getTabuleiro().imprimirTabuleiro();
                jogadorDaVez = rodada.getJogadorDaVez();

                String prompt = String.format("%s =%c ,Em qual celula quer jogar?, [linha,coluna]: ",
                        jogadorDaVez.get().getNome().toUpperCase(), jogadorDaVez.get().getSimbolo());

                String position = ValidarInput.validateInput(scanner, prompt,
                        (s) -> !s.isEmpty() && rodada.hasPosition(s));

                int linha = Integer.parseInt(String.valueOf(position.charAt(0)));
                int coluna = Integer.parseInt(String.valueOf(position.charAt(1)));

                try {
                    rodada.jogar(jogadorDaVez.get(), new Pair(linha - 1, coluna - 1));
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Rodada encerrada.");
            //rodada.getTabuleiro().imprimirTabuleiro();

            if (rodada.getJogadorVencedor().isPresent()) {
                String prompt = String.format("%s=%c, Parabens você é o Jogador vencedor !!!",
                        jogadorDaVez.get().getNome().toUpperCase(), jogadorDaVez.get().getSimbolo());
                System.out.println(prompt);
            }

            if (rodada.isRodadaEmpatada()) {
                System.out.println("Jogo empatado.");
                System.out.println("Não temos vencedor!!!");
            }

            String novaRodada = ValidarInput.validateInput(scanner, "Deseja iniciar nova rodada com os mesmos jogadores? (s/n)",
                    (result) -> (result.equalsIgnoreCase("s")
                            || result.equalsIgnoreCase("n")));

            if (novaRodada.equals("s")) {
                continuar = true;
                continue;
            }
            System.out.println("Saindo...");
            break;
        }

    }


    private Optional<List<Jogador>> leituraDadosDosJogadores() {
        Scanner scanner = new Scanner(System.in);
        Optional<List<Jogador>> listResult = Optional.empty();

        String nomeA = ValidarInput
                .validateInput(scanner, "Nome do primeiro jogador1 ou [Enter=sair]: ",
                        (s) -> s.matches("[a-zA-Z0-9]{3,}") || s.isEmpty());
        if (nomeA.isEmpty())
            return listResult;

        Jogador jogadorA = new Jogador(nomeA, 'X');

        String nomeB = ValidarInput
                .validateInput(scanner, "Nome do segundo jogador2 ou [Enter=sair]: ",
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