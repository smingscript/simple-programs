package com.sming.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JTextField;

public class NumButton implements ActionListener{
    /*
    0 to 9 number Buttons
     */
    private JTextField valueField;
    private String btnText;
    private LinkedList<String> equation;
    private StringBuilder returnNum = new StringBuilder();
    private StringBuilder tempNum = new StringBuilder();
    static Boolean isLongNum; 
    
    
    public NumButton(JTextField valueField, String btnText, LinkedList<String> equation) {
    	System.out.println("Created Button Instance");
    	this.valueField= valueField;
        this.btnText = btnText;
        this.equation  = equation;
        isLongNum = false;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) { 
    	System.out.println(isLongNum);
    	String setValues;
        tempNum.append(btnText) ;
        returnNum.append(tempNum);
        tempNum.deleteCharAt(0);
        
        //숫자를 재입력하는 경우에 valudField가 reset
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
