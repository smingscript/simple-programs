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
    protected static Stack<String> numbers;
    protected static Stack<String> operators;
    
    private int currentWeight;
    private int previousWeight;
  
    private int left;
    private int right;
    private int result;
    
    public Operation(JTextField valueField, JTextField equationField) {
    	this.valueField= valueField;
    	this.equationField = equationField;
    }
    
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
    	System.out.println("Checking the preOp before yes i am: " + prevOp);
    	System.out.println("Checking the currentOp before yes i am : " + currentOp);
    	if(currentOp != "("){
    		System.out.println("yes i am!");
    		numbers.push(valueField.getText()); 
    	}

    	System.out.println("after yes i am" + numbers);
		System.out.println("after yes i am" + operators);

    	valueField.setText("");
    	
    	//처음 연산자가 스택에 없으면 일단 스택에 집어넣는다.
    	if(operators.isEmpty()) {
    		operators.push(currentOp);
    	}
    	//연산자 스택에서 이전 연산자를 pop해서 prevOP에 저장한다.
    	else {
    		prevOp = operators.pop();
    		
    		//연산자의 weight를 업데이트
    		getOperatorWeight(currentOp, prevOp);
    		
    		//currentOp와 prevOP를 비교한다.
    		
    		//currentOp가 prevOp보다 weight가 크면 연산하지 않고 두 연산자를 차례대로 연산자 스택에 push한다.)
    		//왼쪽 괄호가 나왔을 때, 괄호도 제일 크므로 계속 쌓아간다.
    		//왼쪽 괄호가 나온 직후, 연산자를 넣을 때 계산이 되지 않도록.
    		//[(] -> [+ - * /] || [+ -] -> [* /]
    		System.out.println("Checking the preOp before parenthesis 1 : " + prevOp);
        	if(prevOp == "(" || currentWeight > previousWeight) {
        		System.out.println("HEY!");
        		if(numbers.indexOf("0") == 0)
        			numbers.remove(0);
        		
        		operators.push(prevOp);
        		operators.push(currentOp);
        	}
        	
        	//currentOp가 prevOp보다 weight가 작으면 숫자 스택에서 두 숫자를 pop한 후에 prevOp와 연산한다.
        	//[* /] -> [+ -]
        	System.out.println("Checking the preOp before parenthesis 2 : " + prevOp);
        	if(previousWeight != 3 && currentWeight < previousWeight) {
        		operators.push(currentOp);
        		
        		//while을 이용해서 스택 끝까지 다 계산하기
        		while(true) {	
        			System.out.println(" 2 in : right: " + right + "left: " + left + "PrevOp: " + prevOp);
        			System.out.println(numbers);
            		System.out.println(operators);
            		
        			right = Integer.parseInt(numbers.pop());
            		left = Integer.parseInt(numbers.pop());            		
            		result = calculate(prevOp, left, right);
            		
            		numbers.push(Integer.toString(result));

            		prevOp = operators.pop();
            		
            		if(operators.isEmpty() || operators.peek() == "(") {
            			System.out.println(" 2 break out : right: " + right + "left: " + left + "PrevOp: " + prevOp);
            			System.out.println(numbers);
	            		System.out.println(operators);
	            		
            			if(prevOp == "(")
            				operators.push("(");
            			
            			break;
            		}
            		System.out.println(" 2 out : right: " + right + "left: " + left + "PrevOp: " + prevOp);
            			
        		}

        		operators.push(currentOp);
        		updateValueField(Integer.toString(result));
        	}
        	System.out.println("Checking the preOp before parenthesis 3 : " + prevOp);
        	//오른쪽 괄호가 나왔을 때 연산, ( 가 나오기 전까지 연산한다.
        	if(currentOp == ")") {
        		operators.pop();
        		
        		while(true) {
        			//스택이 비었는지를 먼저 확인해야한다
        			prevOp = operators.pop();
        			if(operators.isEmpty() || prevOp.equals("(")) {
        				updateValueField(Integer.toString(result));
        				numbers.pop();
        				break;
        			}
        				
        			else {
        				System.out.println("I'm In!");
        				//prevOp = operators.pop();
	    				right = Integer.parseInt(numbers.pop());
	            		left = Integer.parseInt(numbers.pop());
	            		System.out.println(" 2 : right: " + right + "left: " + left + "PrevOp: " + prevOp);
	            		result = calculate(prevOp, left, right);
	            		
	            		numbers.push(Integer.toString(result));
	            		
	            		System.out.println(numbers);
	            		System.out.println(operators);
        			}
        		}
        	}

        	//currentOp와 prevOp의 weight가 같으면 숫자 스택에서 두 숫자를 pop한 후에 prevOp와 연산한다.
        	//[+ -] -> [+ -] / [* /] -> [* /]
        	System.out.println("Checking the preOp before parenthesis 4 : " + prevOp);
        	if(currentWeight == previousWeight) {
        		System.out.println(numbers);
        		System.out.println(operators);
    			right = Integer.parseInt(numbers.pop());
        		if(!numbers.isEmpty()) {
        			left = Integer.parseInt(numbers.pop());
            		result = calculate(prevOp, left, right);
            		System.out.println(" 3 : right: " + right + "left: " + left + "PrevOp: " + prevOp);
            		numbers.push(Integer.toString(result));
            		operators.push(currentOp);
            		
            		updateValueField(Integer.toString(result));
        		}
        	}
    	}
    	updateEquationField();
    }
    
    private int defineOperatorWeight(String operator) {
    	int weight = 0;
    	
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
    protected void updateValueField(String result) {
    	//결과를 equationField와 valueField에 저장한다.
    	valueField.setText(result);
    	NumButton.isLongNum = false;
    }
}
