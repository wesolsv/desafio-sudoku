package br.com.wszd.ui.custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class FinishGameButton extends JButton {

    public FinishGameButton(final ActionListener actionListenner){
        this.setText("Concluir");
        this.addActionListener(actionListenner);
    }
}
