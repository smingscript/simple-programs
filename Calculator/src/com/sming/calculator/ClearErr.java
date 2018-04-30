package com.sming.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JTextField;

public class ClearErr implements ActionListener {
    /*
    clear whole texts from a input
    */
    JTextField valueField;
    LinkedList<String> equation;

    public ClearErr(JTextField valueField, LinkedList<String> equation){
        this.valueField=valueField;
        this.equation = equation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!equation.isEmpty()){
            if(!valueField.getText().matches("0")){
                int errDigits = valueField.getText().length();
                int index = equation.size();
                equation.subList(index - errDigits, index).clear();
            }
            valueField.setText("0");
            NumBttnAction.isLongNum = false;
        }
    }
}