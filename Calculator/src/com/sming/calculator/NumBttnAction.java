package com.sming.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JTextField;

public class NumBttnAction implements ActionListener{
    /*
    0 to 9 number Buttons
     */
    private JTextField valueField;
    private String btnText;
    private LinkedList<String> equation;
    private StringBuilder returnNum = new StringBuilder();
    private StringBuilder tempNum = new StringBuilder();
    
    protected static Boolean isLongNum;
    
    
    public NumBttnAction(JTextField valueField, String btnText, LinkedList<String> equation) {
        this.valueField= valueField;
        this.btnText = btnText;
        this.equation  = equation;
        isLongNum = false;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) { 
        String setValues;
        tempNum.append(btnText) ;
        returnNum.append(tempNum);
        tempNum.deleteCharAt(0);
        
        //reset valueField if retype a number
        if(isLongNum)
            setValues = valueField.getText() + returnNum.toString();
        else {
            valueField.setText("");
            setValues = valueField.getText() + returnNum.toString();
        }

        valueField.setText(setValues);   
        equation.add(returnNum.toString());
        
        returnNum.deleteCharAt(0);
        isLongNum = true;
    }
}
