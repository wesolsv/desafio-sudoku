package br.com.wszd;

import br.com.wszd.model.Board;
import br.com.wszd.model.Space;
import br.com.wszd.util.BoardTemplate;

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

    private static final double FIXED_PROBABILITY = 0.45;

    public static void main(String[] args) {

        final var positions = generatePositions(new Random())
                .collect(Collectors.toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));

        var option = -1;

        while (true){
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

        for (int i = 0; i < BOARD_SIZE; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_SIZE; j++) {
                String key = String.format("%d,%d", i, j); // Formata a chave corretamente
                String positionConfig = positions.get(key); // Busca no mapa

                if (positionConfig == null) {
                    throw new IllegalArgumentException("Posição não encontrada no mapa: " + key);
                }

                String[] parts = positionConfig.split(",");
                int expected = Integer.parseInt(parts[0]);
                boolean fixed = Boolean.parseBoolean(parts[1]);

                Space currentSpace = new Space(expected, fixed);
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
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < BOARD_SIZE; i++){
            for (var col: board.getSpaces()){
                args[argPos ++] = " " + ((isNull(col.get(i).getActual())) ? " " : col.get(i).getActual());
            }
        }
        System.out.println("Seu jogo se enconrta da seguinte forma");
        System.out.printf((BoardTemplate.BOARD_TEMPLATE) + "\n", args);

    }

    private static void showGameStatus() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.printf("O jogo atualmente se encontra no status %s\n", board.getStatus().getLabel());
        if(board.hasErrors()){
            System.out.println("O jogo tem erros");
        }else {
            System.out.println("O jogo não contém erros");
        }
    }

    private static void clearGame() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Você deseja realmente limpar o tabuleiro do jogo? S/N");
        System.out.println("PERDERÁ TODO O SEU PROGRESSO!!!");
        var confirm = scanner.next();
        while (!confirm.equalsIgnoreCase("S") && !confirm.equalsIgnoreCase("N")){
            System.out.println("Opção inválida, digite S ou N para confirmar!");
            confirm = scanner.next();
        }
        if(confirm.equalsIgnoreCase("S")){
            board.reset();
        }
    }

    private static void finishGame() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        if(board.gameIsFinished()){
            System.out.println("Parabéns você finalizou o jogo");
            showCurrectGame();
            board = null;
        }else if(board.hasErrors()){
            System.out.println("Seu jogo contém erros, verifique o board e ajuste-o");
        }else {
            System.out.println("Você ainda precisa preencher alguns espaços");
        }
    }


    private static Stream<String> generatePositions(Random random) {
        int[][] boardPositions = new int[BOARD_SIZE][BOARD_SIZE];

        return Stream.of(IntStream.range(0, BOARD_SIZE * BOARD_SIZE)
                .mapToObj(i -> {
                    int row = i / BOARD_SIZE;
                    int col = i % BOARD_SIZE;

                    Set<Integer> validNumbers = new HashSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));

                    for (int j = 0; j < BOARD_SIZE; j++) {
                        validNumbers.remove(boardPositions[row][j]);
                    }

                    for (int j = 0; j < BOARD_SIZE; j++) {
                        validNumbers.remove(boardPositions[j][col]);
                    }

                    int startRow = (row / 3) * 3;
                    int startCol = (col / 3) * 3;
                    for (int r = startRow; r < startRow + 3; r++) {
                        for (int c = startCol; c < startCol + 3; c++) {
                            validNumbers.remove(boardPositions[r][c]);
                        }
                    }

                    int num = validNumbers.isEmpty() ? 0 : new ArrayList<>(validNumbers).get(random.nextInt(validNumbers.size()));
                    boolean isFixed = random.nextDouble() < FIXED_PROBABILITY;
                    boardPositions[row][col] = num;

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