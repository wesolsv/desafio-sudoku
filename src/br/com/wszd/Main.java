package br.com.wszd;

import br.com.wszd.model.Board;
import br.com.wszd.model.Space;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);

    private static Board board;

    private final static int BOARD_SIZE = 9;

    private static final double FIXED_PROBABILITY = 0.4;

    public static void main(String[] args) {

        final var positions = generatePositions(new Random())
                .collect(Collectors.toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));

        var option = -1;

        while (option != 0 ){
            System.out.println("Seja bem vindo(a), escolha uma opção para iniciar: ");
            System.out.println("1 - Novo Jogo");
            System.out.println("2 - Incluir um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar o jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - Limpar o jogo");
            System.out.println("7 - Finalizar o jogo");
            System.out.println("0 - Sair");

            option = scanner.nextInt();

            switch (option){
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrectGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 0 -> System.exit(0);
                default -> System.out.println("Opção inválida, selecione uma das opções no menu");
            }
        }

    }

    private static void startGame(Map<String, String> positions) {
        if(nonNull(board)){
            System.out.println("O jogo já foi iniciado");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();

        for (int i = 0; i < BOARD_SIZE; i++){
            spaces.add(new ArrayList<>());
            for(int j = 0; j < BOARD_SIZE; j++){
                var positionConfig = positions.get("%s,%s").formatted(i, j);
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("O jogo está pronto para iniciar");
    }

    private static void inputNumber() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o número será inserido");
        var col = runUntilGetValueNumber(0, 8);

        System.out.println("Informe a linha em que o número será inserido");
        var row = runUntilGetValueNumber(0, 8);

        System.out.printf("Informe o número que vai entrar na posição[%s, %s]\n", col, row);
        var value = runUntilGetValueNumber(1, 9);

        if(!board.changeValue(col, row, value)){
            System.out.printf("A posição [%s, %s] tem um valor fixo \n", col, row);
        }
    }

    private static void removeNumber() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o número será inserido");
        var col = runUntilGetValueNumber(0, 8);

        System.out.println("Informe a linha em que o número será inserido");
        var row = runUntilGetValueNumber(0, 8);

        if(!board.clearValue(col, row)){
            System.out.printf("A posição [%s, %s] tem um valor fixo \n", col, row);
        }
    }

    private static void showCurrectGame() {
    }

    private static void showGameStatus() {
    }

    private static void clearGame() {
    }

    private static void finishGame() {
    }


    private static Stream<String> generatePositions(Random random) {
        return Stream.of(IntStream.range(0, BOARD_SIZE * BOARD_SIZE)
                .mapToObj(i -> {
                    int row = i / BOARD_SIZE;
                    int col = i % BOARD_SIZE;
                    int num = random.nextInt(BOARD_SIZE) + 1;
                    boolean isFixed = random.nextDouble() < FIXED_PROBABILITY;
                    return col + "," + row + ";" + num + "," + isFixed;
                }).toArray(String[]::new)
        );
    }

    private static int runUntilGetValueNumber(final int min, final int max){
        var current = scanner.nextInt();
        while(current < min || current > max){
            System.out.printf("Informe um número entre %s e %s\n", min , max);
            current = scanner.nextInt();
        }

        return current;
    }
}