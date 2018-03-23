package com.sming.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class Back implements ActionListener {
    JTextField valueField;

    public Back(JTextField valueField){
        this.valueField=valueField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String temp = valueField.getText();
        if(temp.length() <= 1){
            valueField.setText("0");
        }else{
            valueField.setText(temp.substring(0, temp.length()-1));
        }
    }

}
