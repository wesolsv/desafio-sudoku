package br.com.wszd.ui.custom.screen;

import br.com.wszd.service.BoardService;
import br.com.wszd.ui.custom.button.FinishGameButton;
import br.com.wszd.ui.custom.button.ResetButton;
import br.com.wszd.ui.custom.frame.MainFrame;
import br.com.wszd.ui.custom.panel.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;

    private JButton checkGameStatusButton;
    private JButton finishGameButton;
    private JButton resetButton;

    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
    }

    public void buildMainScreen(){
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);

        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishButton(mainPanel);

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void addFinishButton(JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e -> {
            if(boardService.gameIsFinished()){
                JOptionPane.showMessageDialog(null, "Parabéns você concluiu o jogo!");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            }else {
                JOptionPane.showMessageDialog(null, "O jogo tem inconsistências");
            }
        });
        mainPanel.add(finishGameButton);
    }

    private void addCheckGameStatusButton(JPanel mainPanel) {
        checkGameStatusButton = new FinishGameButton(e -> {
            var hasErrors = boardService.hasErrors();
            var gameStatus = boardService.getStatus();
            var message = switch (gameStatus){
                case NON_STARTED -> "O jogo não foi iniciado";
                case INCOMPLETE -> "O jogo está incompleto";
                case COMPLETE -> "O jogo está completo";
            };

            message += hasErrors ? " e contém erros" : " e não contém erros!";

            JOptionPane.showMessageDialog(null, message);
        });
        mainPanel.add(checkGameStatusButton);
    }

    private void addResetButton(JPanel mainPanel) {
        resetButton = new ResetButton(e -> {
            var dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "Quer realmente resetar o game?",
                    "Limpar o jogo",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
            );
            if(dialogResult == 0){
                boardService.reset();
            }
        });
        mainPanel.add(resetButton);
    }
}
