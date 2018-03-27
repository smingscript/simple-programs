package com.sming.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JTextField;

public class Operation implements ActionListener {
    /*
    Plus, Minus, Multiply, Division, Parenthesis
     */
    private JTextField valueField;
    private JTextField equationField;
    private String currentOp;
    private String prevOp;
    
    private LinkedList<String> equation;
    private static Stack<String> numbers;
    private static Stack<String> operators;
    
    
    private int currentWeight;
    private int previousWeight;
  
    private int left;
    private int right;
    private int result;

    public Operation(JTextField valueField, JTextField equationField, String oprText, LinkedList<String> equation) {
    	//화면에 단계 별 계산 결과를 출력하게 위해 필요
    	this.valueField= valueField;
    	
    	//단계 별 계산 식을 업데이트 하기 위해 필요
        this.equationField = equationField;
        
        this.currentOp = oprText;
        
        //입력되는 값을 하나씩 저장해서 equationField에 최종적으로 전달
        this.equation = equation;
    	
    	operators = new Stack<String>();
    	numbers = new Stack<String>();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    	
    	//NumButton에서 숫자를 가져온 후 스택에 push한다.
    	System.out.println("Value input: " + valueField.getText());
    	
    	numbers.push(valueField.getText());
    	
    	valueField.setText("");
    	
    	//처음 연산자가 스택에 없으면 일단 스택에 집어넣는다.
    	if(operators.isEmpty()) {
    		System.out.println("Hello, we've just started Calculator and pushed you're first input");
    		
    		operators.push(currentOp);
    		System.out.println(operators);
    	}
    	//연산자 스택에서 이전 연산자를 pop해서 prevOP에 저장한다.
    	else {
    		System.out.println("you have operators in operators stack");
    		prevOp = operators.pop();
    		
    		//연산자의 weight를 업데이트
    		getOperatorWeight(currentOp, prevOp);
    		
    		//currentOp와 prevOP를 비교한다.
    		
    		//currentOp가 prevOp보다 weight가 크면 연산하지 않고 두 연산자를 차례대로 연산자 스택에 push한다.)
    		//왼쪽 괄호가 나왔을 때, 괄호도 제일 크므로 계속 쌓아간다.
    		//왼쪽 괄호가 나온 직후, 연산자를 넣을 때 계산이 되지 않도록.
    		//[(] -> [+ - * /] || [+ -] -> [* /]
        	if(prevOp == "(" || currentWeight > previousWeight) {
        		if(numbers.indexOf("0") == 0)
        			numbers.remove(0);
        		
        		System.out.println(operators);
        		System.out.println(numbers);
        		System.out.println("currentOp: " + currentOp + " previousOp: " + prevOp );
        		
        		operators.push(prevOp);
        		operators.push(currentOp);
        	}
        	
        	//currentOp가 prevOp보다 weight가 작으면 숫자 스택에서 두 숫자를 pop한 후에 prevOp와 연산한다.
        	//[* /] -> [+ -]
        	if(previousWeight != 3 && currentWeight < previousWeight) {
        		//while을 이용해서 스택 끝까지 다 계산하기
        		while(true) {
        			
        			if(prevOp == "(")
        				break;
        			
        			System.out.println("I'm here" + prevOp);	
            		right = Integer.parseInt(numbers.pop());
            		left = Integer.parseInt(numbers.pop());            		
            		result = calculate(prevOp, left, right);
            		
            		numbers.push(Integer.toString(result));
            		System.out.println("numbers state: " + numbers);
            		System.out.println("operators state: " + operators);
            		
            		if(operators.empty())
            			break;
            		else
            			prevOp = operators.pop();
        		}
        		operators.push("(");
        		operators.push(currentOp);
        		updateValueField(Integer.toString(result));
        	}
        	
        	//괄호 이 전에 식이 있었을 때 괄호 계산이 끝난 후 나머지와 다시 연산
        	
        	
        	//오른쪽 괄호가 나왔을 때 연산, ( 가 나오기 전까지 연산한다.
        	if(currentOp == ")") {
        		System.out.println("Met last parenthesis");
        		System.out.println(operators);
        		System.out.println(numbers);
        		while(true) {
        			
        			
        			System.out.println(operators);
            		System.out.println(numbers);
            		
        			System.out.println("Calculation inside parenthesis");
        			System.out.println("currentOp: " + currentOp + " previousOp: " + prevOp );
        			
        			if(operators.pop().equals("("))
        				break;
        			else {
        				right = Integer.parseInt(numbers.pop());
                		left = Integer.parseInt(numbers.pop());
                		System.out.println("left: " + left + " right: " + right);
                		result = calculate(prevOp, left, right);
                		System.out.println(result);
                		
                		numbers.push(Integer.toString(result));
                		operators.pop();
        			}
        		}
        		System.out.println("out of while statement");
        		//operators.push(currentOp);
        		updateValueField(Integer.toString(result));
        	}
        	
        	//Same number problem
        	//currentOp와 prevOp의 weight가 같으면 숫자 스택에서 두 숫자를 pop한 후에 prevOp와 연산한다.
        	//[+ -] -> [+ -] / [* /] -> [* /]
        	if(currentWeight == previousWeight) {
        		System.out.println(numbers);
        		right = Integer.parseInt(numbers.pop());
        		left = Integer.parseInt(numbers.pop());
        		result = calculate(prevOp, left, right);
        		
        		numbers.push(Integer.toString(result));
        		operators.push(currentOp);
        		
        		updateValueField(Integer.toString(result));
        	}
    	}
    	
    	updateEquationField();
    }
    
    private int defineOperatorWeight(String operator) {
    	int weight = 0;;
    	
    	switch (operator) {
	    	case "(":
			case ")":
				weight = 3;
				break;
	    	case "*":
			case "/":
				weight = 2;
				break;
    		case "+":
    		case "-":
    			weight = 1;
    			break;
    		default:
    			try {}
    			catch(Exception e){
    				System.out.println("Invalid Operator"); 
    			}
      	}
		return weight;
    }
    
    private void getOperatorWeight(String currentOp, String prevOp) {
    	currentWeight = defineOperatorWeight(currentOp);
    	previousWeight = defineOperatorWeight(prevOp);
    }
    
    private int calculate(String prevOp, int left, int right) {
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
    
    //GUI의 사용자가 입력한 식이 표시하도록 업데이트
    private void updateEquationField() {
    	//결과를 equationField와 valueField에 저장한다.
    	String newText = "";

        equation.add(currentOp);
        
        for(String text: equation){
            newText += text;
        }
        equationField.setText(newText);
    }
    
    //결과 값을 GUI에 표시하도록 업데이트
    private void updateValueField(String result) {
    	//결과를 equationField와 valueField에 저장한다.
    	valueField.setText(result);
    	NumButton.isLongNum = false;
    }
}
