package com.sming.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JTextField;

public class Equal implements ActionListener {
    private JTextField valueField;
    private JTextField equationField;
    
    private Stack<String> numbers;
    private Stack<String> operators;
    
    private String lastInput;
    private String preInput;
    private String operator;
    
    private int left;
    private int right;
    private int result;
    
    private Operation operation;
    

    public Equal(JTextField valueField, JTextField equationField) {
        this.valueField= valueField;
        this.equationField= equationField;
        
        numbers = Operation.numbers;
        operators = Operation.operators;
        
        operation = new Operation(valueField, equationField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	/*
    	 * equationField에 있는 나머지 값과 연산자를 강제적으로 계산하여 결과를 낸다 
    	 * 전체 식을 다 돌아보는 것으로 수정하기
    	 */
    	System.out.println("print operators in Equals: " + operators);
    	System.out.println("print numbers in Equals: " + numbers);
    	System.out.println(equationField.getText());
    	lastInput = valueField.getText();
    	preInput = numbers.pop();
    	operator = operators.pop();

    	System.out.println("lastInput: " + lastInput);
    	System.out.println("preInput: " + preInput);
    	System.out.println("operator: " + operator);
    	
    	left = Integer.parseInt(preInput);
    	right = Integer.parseInt(lastInput);
		result = operation.calculate(operator, left, right);
		
		
		operation.updateValueField(Integer.toString(result));
		updateEquationField();

    }
    //여기 변경
    private void updateEquationField() {
    	//결과를 equationField와 valueField에 저장한다.
    	String newText = "";

//        operator.(operator);
//        operator.Add(lastInput);
//        
//        for(String text: equation){
//            newText += text;
//        }
        equationField.setText(newText);
    }
}

