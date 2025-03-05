package br.com.wszd.ui.custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CheckGameStatusButton extends JButton {

    public CheckGameStatusButton(final ActionListener actionListenner){
        this.setText("Verificar Jogo");
        this.addActionListener(actionListenner);
    }
}
