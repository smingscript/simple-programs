package com.sming.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JTextField;

public class ClearErr implements ActionListener {
    /*
    버튼을 누르면 equation 에서 지금까지의 입력을 지워낸다
     */
	JTextField valueField;
    LinkedList<String> equation;

    public ClearErr(JTextField valueField, LinkedList<String> equation){
        this.valueField=valueField;
        this.equation = equation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	//valueField의 text의 길이를 구한다
    	int errDigits = valueField.getText().length();
    	System.out.println(errDigits);
    	int index = equation.size();
    	
    	//equation에서 그만큼 자른다
    	equation.subList(index - errDigits, index).clear();
    	
    	//valueField를 최종적으로 0으로 만든다.
    	valueField.setText("0");
    	
    	NumBttnAction.isLongNum = false;
    }

}