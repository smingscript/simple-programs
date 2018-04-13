package com.sming.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JTextField;

public class Equal implements ActionListener {
    private JTextField valueField;
    private JTextField equationField;
    
    private Stack<String> numbers;
    private Stack<String> operators;
    private LinkedList<String> equation;
    
    private String lastInput;
    private String preInput;
    private String operator;
    
    private int left;
    private int right;
    private int result;
    
    private OperBttnAction operation;
    

    public Equal(JTextField valueField, JTextField equationField, LinkedList<String> equation) {
        this.valueField= valueField;
        this.equationField= equationField;
        this.equation = equation;
        
        numbers = OperBttnAction.numbers;
        operators = OperBttnAction.operators;
        
        operation = new OperBttnAction(valueField, equationField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	/*
    	 * equationField에 있는 나머지 값과 연산자를 강제적으로 계산하여 결과를 낸다 
    	 * 전체 식을 다 돌아보는 것으로 수정하기
    	 */
    	System.out.println(">>>Inside Equals<<<");
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
		
		/*여기까지 마지막 연산 결과*/
		System.out.println("print operators in Equals: " + operators);
    	System.out.println("print numbers in Equals: " + numbers); 
		//마지막 연산을 numbers에 push
		
		if(operators.isEmpty()) {
			System.out.println("You are in if!");
//			updateEquationField(Integer.toString(right));
		}else {
			numbers.push(Integer.toString(result));
			System.out.println("You are in else!");
			System.out.println("print operators in Equals: " + operators);
	    	System.out.println("print numbers in Equals: " + numbers); 
			//while문을 돌면서  stack에 남아있는 연산을 모두 끝낸다
			while(true) {	
				operator = operators.pop();

				System.out.println(numbers);
	    		System.out.println(operators);
	    		
				right = Integer.parseInt(numbers.pop());
				updateEquationField(Integer.toString(right));
	    		left = Integer.parseInt(numbers.pop());            		
	    		result = calculate(operator, left, right);
	    		
	    		numbers.push(Integer.toString(result));
	    		
	    		if(operators.isEmpty()) {
	    			System.out.println(numbers);
	        		System.out.println(operators);
	    			
	    			break;
	    		}
			}
		}
		
		//모든 것을 reset하기
		String equations = updateEquationField(Integer.toString(right));
		OperBttnAction.numbers.clear();
    	OperBttnAction.operators.clear();
        equation.clear();
        valueField.setText("");

		//마지막에 valueField와 equationField에 값 올리기
		operation.updateValueField(Integer.toString(result));
		equationField.setText(equations);

    }
    //여기 변경
    private String updateEquationField(String update) {
    	//결과를 equationField와 valueField에 저장한다.
    	StringBuilder newText = new StringBuilder();

        for(String text: equation){
            newText.append(text);
        }
        
        return newText.toString();
       
    }
    
    protected int calculate(String prevOp, int left, int right) {
    	int result = 0;
    	
    	switch (prevOp) {
    		case "+":
    			result = left + right;
    			break;
    		case "-":
    			result = left - right;
    			break;
    		case "*":
    			result = left * right;
    			break;
    		case "/":
    			result = left / right;
    			break;
    		default:
    			break;
      	}
    	
		return result;
    }
}

