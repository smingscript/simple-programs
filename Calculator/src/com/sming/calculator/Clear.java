package com.sming.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JTextField;

public class Clear implements ActionListener {
    private JTextField valueField;
    private LinkedList<String> equation;

    public Clear(JTextField valueField, LinkedList<String> equation){
        this.valueField=valueField;
        this.equation = equation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        equation.clear();
        valueField.setText("");


    }

}
