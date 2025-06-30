import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.Random;

// Classe responsável pela lógica do jogo da forca
class Forca {
    private String palavraOculta;
    private char[] palavraAtual;
    private Set<Character> letrasUsadas;
    private int tentativasRestantes;
    private static final int MAX_TENTATIVAS = 6;

    public Forca(String palavra) {
        this.palavraOculta = palavra.toUpperCase();
        this.palavraAtual = new char[palavra.length()];
        Arrays.fill(this.palavraAtual, '_');
        this.letrasUsadas = new HashSet<>();
        this.tentativasRestantes = MAX_TENTATIVAS;
    }

    public boolean tentarLetra(char letra) {
        letra = Character.toUpperCase(letra);
        if (letrasUsadas.contains(letra)) {
            return false; // Letra já foi usada
        }
        letrasUsadas.add(letra);

        boolean acertou = false;
        for (int i = 0; i < palavraOculta.length(); i++) {
            if (palavraOculta.charAt(i) == letra) {
                palavraAtual[i] = letra;
                acertou = true;
            }
        }
        if (!acertou) {
            tentativasRestantes--;
        }
        return acertou;
    }

    public boolean jogoVencido() {
        for (char c : palavraAtual) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    public boolean jogoTerminado() {
        return tentativasRestantes == 0 || jogoVencido();
    }

    public String getPalavraAtual() {
        StringBuilder sb = new StringBuilder();
        for (char c : palavraAtual) {
            sb.append(c).append(' ');
        }
        return sb.toString().trim();
    }

    public String getPalavraOculta() {
        return palavraOculta;
    }

    public int getTentativasRestantes() {
        return tentativasRestantes;
    }

    public Set<Character> getLetrasUsadas() {
        return letrasUsadas;
    }

    public void mostrarForca() {
        String[] forca = {
            "  _______",
            " |/      |",
            " |      " + (tentativasRestantes < 6 ? "(_)" : ""),
            " |      " + (tentativasRestantes < 4 ? "/" : " ") + (tentativasRestantes < 5 ? "|" : " ") + (tentativasRestantes < 3 ? "\\" : ""),
            " |       " + (tentativasRestantes < 2 ? "/" : ""),
            " |       " + (tentativasRestantes < 1 ? "\\" : ""),
            "_|___"
        };
        for (String linha : forca) {
            System.out.println(linha);
        }
    }
}

// Classe principal de execução
public class JogoDaForca {
    // Palavras de exemplo (pode ser expandido)
    private static final String[] PALAVRAS = {
        "java", "computador", "programacao", "objeto", "heranca", "polimorfismo", "encapsulamento"
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        exibirMenu(scanner);
        scanner.close();
    }

    private static void exibirMenu(Scanner scanner) {
        while (true) {
            System.out.println("====== JOGO DA FORCA ======");
            System.out.println("1. Jogar");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    jogar(scanner);
                    break;
                case "2":
                    System.out.println("Saindo do jogo. Até logo!");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void jogar(Scanner scanner) {
        String palavra = sortearPalavra();
        Forca forca = new Forca(palavra);

        while (!forca.jogoTerminado()) {
            System.out.println("\nPalavra: " + forca.getPalavraAtual());
            System.out.println("Tentativas restantes: " + forca.getTentativasRestantes());
            System.out.println("Letras já usadas: " + forca.getLetrasUsadas());
            forca.mostrarForca();
            System.out.print("Digite uma letra: ");
            String entrada = scanner.nextLine().trim();

            if (entrada.length() != 1 || !Character.isLetter(entrada.charAt(0))) {
                System.out.println("Por favor, digite apenas uma letra.");
                continue;
            }

            char letra = entrada.charAt(0);
            if (!forca.tentarLetra(letra)) {
                if (forca.getLetrasUsadas().contains(Character.toUpperCase(letra))) {
                    System.out.println("Você já tentou essa letra!");
                } else {
                    System.out.println("Letra incorreta!");
                }
            }
        }

        if (forca.jogoVencido()) {
            System.out.println("\nParabéns! Você venceu!");
        } else {
            System.out.println("\nQue pena! Você perdeu!");
        }
        System.out.println("A palavra era: " + forca.getPalavraOculta());
        forca.mostrarForca();
        System.out.println();
    }

    private static String sortearPalavra() {
        Random rand = new Random();
        int idx = rand.nextInt(PALAVRAS.length);
        return PALAVRAS[idx];
    }
}