package br.com.wszd;

import br.com.wszd.ui.custom.frame.MainFrame;
import br.com.wszd.ui.custom.panel.MainPanel;
import br.com.wszd.ui.custom.screen.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.stream.Collectors;

import static br.com.wszd.util.SudokuUtilTemplateNumbers.generatePositions;


public class UIMain {
    public static void main(String[] args) {

        final var gameConfig = generatePositions(new Random())
                .collect(Collectors.toMap(k -> k.split(";")[0], v -> v.split(";")[1]));

        var mainScreen = new MainScreen(gameConfig);
        mainScreen.buildMainScreen();
    }
}
