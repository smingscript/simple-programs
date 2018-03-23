package com.sming.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class Equal implements ActionListener {
    JTextField valueField;

    public Equal(JTextField valueField) {
        this.valueField= valueField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	/*
    	 * equationField에 있는 나머지 값과 연산자를 강제적으로 계산하여 결과를 낸다 
    	 */
        
        int firstValue=Integer.parseInt(Calculator.firstValue);
        int secondValue=Integer.parseInt(valueField.getText());
        switch (Calculator.op.charAt(0)) {
            case '+':
                valueField.setText( Integer.toString(firstValue+secondValue));
                break;
            case '-':
                valueField.setText( Integer.toString(firstValue-secondValue));
                break;
            case '*':
                valueField.setText( Integer.toString(firstValue*secondValue));
                break;
            default:
                valueField.setText( Integer.toString(firstValue/secondValue));
                break;
        }

        Calculator.firstValue="";
        Calculator.op=" ";

    }
}

