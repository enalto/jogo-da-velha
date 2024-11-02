import domain.Jogador;
import domain.Pair;
import domain.Rodada;

import java.util.*;
import java.util.logging.Logger;

import static domain.Rodada.*;

public class Aplication {

    static {
        System.setProperty(
                "java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }

    private static final Logger logger = Logger.getLogger(Aplication.class.getName());


    public static void main(String[] args) throws InterruptedException {
        Aplication aplication = new Aplication();
        aplication.run();
    }

    private void run() throws InterruptedException {
        showTelaInicio();
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

            Jogador jogadorDaVez = rodada.getJogadorDaVez()
                    .orElseThrow(() -> new RuntimeException("Jogador da vez não existe."));

            System.out.println("-".repeat(60));

            System.out.println("Jogo iniciado.");
            String messageFormatted = String.format("Jogador sorteado para iniciar foi [%s, Simbolo= %c]",
                    jogadorDaVez.getNome().toUpperCase(), jogadorDaVez.getSimbolo());

            System.out.println(messageFormatted);

            rodada.showInstruction();
            while (!rodada.gameOver()) {

                rodada.getTabuleiro().imprimirTabuleiro();
                jogadorDaVez = rodada.getJogadorDaVez()
                        .orElseThrow(() -> new RuntimeException("Jogador da vez não existe."));

                Pair celulaAjogar = leituraCelulaAjogar(jogadorDaVez, rodada);

                try {
                    rodada.jogar(jogadorDaVez, celulaAjogar);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }

            rodada.getTabuleiro().imprimirTabuleiro();
            System.out.println("Partida finalizada.");

            if (rodada.getJogadorVencedor().isPresent()) {
                String prompt = String.format("%s=%c, Parabens você é o Jogador vencedor !!!",
                        jogadorDaVez.getNome().toUpperCase(), jogadorDaVez.getSimbolo());
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
                System.out.println("Jogo iniciado.");
                continuar = true;
                continue;
            }
            logger.info("Saindo...");
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

    private Pair leituraCelulaAjogar(Jogador jogadorDaVez, Rodada rodada) {
        Objects.requireNonNull(jogadorDaVez);
        Objects.requireNonNull(rodada);
        Scanner scanner = new Scanner(System.in);

        String prompt = String.format("%s =%c ,Em qual celula quer jogar?, [linha,coluna]: ",
                jogadorDaVez.getNome().toUpperCase(), jogadorDaVez.getSimbolo());

        String position = ValidarInput.validateInput(scanner, prompt,
                (s) -> !s.isEmpty() && rodada.hasPosition(s));

        int linha = Integer.parseInt(String.valueOf(position.charAt(0)));
        int coluna = Integer.parseInt(String.valueOf(position.charAt(1)));
        return new Pair(linha-1, coluna-1);
    }


    private void showTelaInicio() {

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