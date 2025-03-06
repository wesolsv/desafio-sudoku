package br.com.wszd.service;

import br.com.wszd.model.Board;
import br.com.wszd.model.GameStatusEnum;
import br.com.wszd.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardService {

    private final static int BOARD_SIZE = 9;

    private final Board board;

    public BoardService(final Map<String, String> gameConfig){
        this.board = new Board(initBoard(gameConfig));
    }

    public List<List<Space>> getSpaces(){
        return board.getSpaces();
    }

    public void reset(){
        board.reset();
    }

    public boolean hasErrors(){
        return board.hasErrors();
    }

    public GameStatusEnum getStatus(){
        return board.getStatus();
    }

    public boolean gameIsFinished(){
        return board.gameIsFinished();
    }

    private List<List<Space>> initBoard(Map<String, String> gameConfig) {
        List<List<Space>> spaces = new ArrayList<>();

        for (int i = 0; i < BOARD_SIZE; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_SIZE; j++) {
                String key = String.format("%d,%d", i, j);
                String positionConfig = gameConfig.get(key);

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

        return spaces;
    }


}
