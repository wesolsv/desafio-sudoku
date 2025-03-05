package br.com.wszd;

import br.com.wszd.ui.custom.frame.MainFrame;
import br.com.wszd.ui.custom.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class UIMain {
    public static void main(String[] args) {
        var dimension = new Dimension(600,600);
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
