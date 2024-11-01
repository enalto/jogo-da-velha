import java.util.Arrays;

public class Aplication {

    public static void main(String[] args) {
        Aplication aplication = new Aplication();
        aplication.run();
    }


    private void run() {
        mostrarInstrucoes();
        Jogador jogador1 = new Jogador("Jose", 'X');
        Jogador jogador2 = new Jogador("Pedro", 'O');

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
