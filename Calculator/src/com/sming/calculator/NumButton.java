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
    private String total;

    public NumButton(JTextField valueField, String btnText, LinkedList<String> equation) {
        this.valueField= valueField;
        //valueField.setText("");
        this.btnText = btnText;
        this.equation  = equation;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) { 
    	
    	if(returnNum.length() == 0) {
    		total = "";
    	}
    	
    	System.out.println("this is tempNum before : " + tempNum);
        tempNum.append(btnText) ;
        System.out.println("this is tempNum: " + tempNum);
        //returnNum.append(valueField.getText());
        returnNum.append(tempNum);
        System.out.println("this is returnNum: " + returnNum);
        for(int i = 0; i < returnNum.length(); i++) {
        	total += returnNum.substring(i);
        }
        System.out.println("this is total: " + total);
        tempNum.deleteCharAt(0);
        System.out.println("this is tempNum: " + tempNum);
        valueField.setText(total);
        System.out.println("Hi!");
        equation.add(total);
        returnNum.deleteCharAt(0);
    }
}
