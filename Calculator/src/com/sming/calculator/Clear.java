package com.sming.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JTextField;

public class Clear implements ActionListener {
    private JTextField valueField;
    private JTextField equationField;
    private LinkedList<String> equation;

    public Clear(JTextField valueField, JTextField equationField, LinkedList<String> equation){
        this.valueField=valueField;
        this.equationField = equationField;
        this.equation = equation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        OperBttnAction.numbers.clear();
        OperBttnAction.operators.clear();
        equation.clear();
        valueField.setText("");
        equationField.setText("");
    }

}
