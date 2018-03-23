package com.sming.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JTextField;

public class ClearErr implements ActionListener {
    /*
    버튼을 누르면 equation 에서 입력의 끝만 잘라내고 valueField 에 출력한다.
     */
    private JTextField valueField;
    private LinkedList<String> equation;

    public ClearErr(JTextField valueField, LinkedList<String> equation){
        this.valueField=valueField;
        this.equation = equation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Calculator.op=' ';
        //Calculator.firstValue="";
        String newText = "";
        equation.removeLast();
        for(String text: equation){
            newText += text;
        }
        valueField.setText(newText);


    }

}