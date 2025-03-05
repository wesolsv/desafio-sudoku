package br.com.wszd.ui.custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ResetButton extends JButton {

    public ResetButton(final ActionListener actionListenner){
        this.setText("Reiniciar Jogo");
        this.addActionListener(actionListenner);
    }
}
