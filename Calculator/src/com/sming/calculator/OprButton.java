package com.sming.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JTextField;

public class OprButton implements ActionListener {
    /*
    Plus, Minus, Multiply, Division
     */
    private JTextField valueField;
    private JTextField equationField;
    private String oprText;
    private LinkedList<String> equation;

    public OprButton(JTextField valueField, JTextField equationField, String oprText, LinkedList<String> equation) {
    	this.valueField= valueField;
        this.equationField = equationField;
        this.oprText = oprText;
        this.equation = equation;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    	//연산자를 바꿔 눌렀을 때 생기는 예외  처리
    	if(NumButton.returnNum.isEmpty()) {
    		NumButton.returnNum = Calculator.firstValue;
    	}else {
    		
            System.out.println(Calculator.operationList);
    	}
        Calculator.firstValue = Calculator.secondValue; 
        System.out.println("NumButton: " + Calculator.firstValue);
        Calculator.secondValue = NumButton.returnNum;
        System.out.println("What happend?: " + Calculator.secondValue);
        Calculator.mulValue = Calculator.firstValue;

        int left = Integer.parseInt(Calculator.firstValue);
        int right = Integer.parseInt(Calculator.secondValue);
        System.out.println("The very first left: " + left); System.out.println("The very first right: " + right);
        //바로 전 문자열이 연산자라면 지우고 덮어 써서 화면 표시 
        if(equation.getLast().matches(".*[+\\-*/].*")){
            equation.removeLast();
        }
        // +나 - 가 전 연산자면 바로 결과 값을 출력
        else{
            if(equation.size() < 3) {
                valueField.setText("");
            }
            //전 연산자가 +나 -인 경우
            else if(Calculator.op.matches(".*[+\\-].*")){
                System.out.println("I'm in!");
                System.out.println(left); System.out.println(right); System.out.println(Calculator.op);
                //앞에 + - 가 오고, 이번 연산자가 *이나 /인 경우
                if(oprText.matches(".*[*/].*")){
                    System.out.println("I'm in +- */");
                    valueField.setText(Calculator.secondValue);
                    //Calculator.operationList.addFirst(NumButton.returnNum);
                    Calculator.operationList.addFirst(Calculator.firstValue);
                	Calculator.operationList.add(Calculator.op);
                	System.out.println("the first list: " + Calculator.operationList);
                }
                
                else if(Calculator.op.equals("+")){
                    System.out.println("I'm in +- +");
                    System.out.println(Calculator.operationList);
                    if(!(Calculator.operationList == null)) {
                    	Calculator.operationList.clear();
                    }
                    System.out.println(left); System.out.println(right);
                    Calculator.secondValue = Integer.toString(left + right);
                    System.out.println("OprButton: " + Calculator.secondValue);
                    valueField.setText(Integer.toString(left + right));
                    Calculator.operationList.add(Calculator.secondValue);
                }
                else if(Calculator.op.equals("-")){
                    System.out.println("I'm in! +- -");
                    Calculator.secondValue = Integer.toString(left - right);
                    System.out.println("OprButton: " + Calculator.secondValue);
                    valueField.setText(Integer.toString(left - right));
                }
            }
            //전 연산자가 *나 /인 경우
            else if(Calculator.op.matches(".*[*/].*")){
                //곱하기 나눗셈 연산만 따로 저장
                if(oprText.matches(".*[*/].*")){
                    System.out.println("I'm in */ */");
                    
                    System.out.println("See firstValue: " + Calculator.firstValue);
                    System.out.println("See secondValue: " + Calculator.secondValue);
                    System.out.println("See input malValue: " + Calculator.mulValue);
                    Calculator.multiply = Integer.parseInt(Calculator.mulValue) * Integer.parseInt(NumButton.returnNum);
                    Calculator.mulValue = Integer.toString(Calculator.multiply);
                    System.out.println("See malValue 2: " + Calculator.mulValue);
                    valueField.setText(Calculator.secondValue);
                    Calculator.secondValue = Calculator.mulValue;
                    //
                    Calculator.mulValue = valueField.getText();
                    System.out.println("See output malValue: " + Calculator.mulValue);
                }
                //1 + (2 * 2 * 2 + 1 ) - 1
                else if(oprText.equals("+")){
                    System.out.println("I'm in */ +");
                    //리스트 이용해서 계산하기
                  //에러부분: 중간에 곱하기가 오면 어떻게??????
            		
                    int i = 1;
                    while(Calculator.operationList.size() > 0) {
                    	//곱하기 연산의 핵심 
                    	if(i > 0) {
                    		System.out.println("before removal: " + Calculator.operationList);
                    		//Calculator.multiply *= Integer.parseInt(Calculator.operationList.pollFirst());
                    		Calculator.multiply *= Integer.parseInt(NumButton.returnNum);
                        	//Calculator.operationList.removeLast();
                        	System.out.println("what happened: " + Calculator.multiply);
                        	Calculator.priorResult = Calculator.multiply;
                        	System.out.println("what happened?: " + Calculator.operationList);
                        	System.out.println("multiply: " + Calculator.multiply);
                        	i--;
                    	}
                    	                    	
                    	String op = Calculator.operationList.pollLast();
                    	System.out.println(op);
                    	System.out.println(Calculator.operationList);
                    	int nextNum = Integer.parseInt(Calculator.operationList.pollFirst());
                    	System.out.println("Next number: " + nextNum);
                    	switch (op.charAt(0)) {
                        case '+':
                        	System.out.println("multiply2: " + Calculator.multiply);
                        	Calculator.priorResult += nextNum;
                        	System.out.println("priory result: " + Calculator.priorResult);
                            valueField.setText( Integer.toString(Calculator.priorResult));

                            break;
                        case '-':
                        	System.out.println("multiply2: " + Calculator.multiply);
                            valueField.setText( Integer.toString(Calculator.multiply - nextNum));

                            break;
                    	}
                    }
                    Calculator.multiply = Integer.parseInt(NumButton.returnNum);
                    Calculator.secondValue = Integer.toString(Calculator.priorResult);
                    System.out.println("Out of while:");
                }
            }
        }

        //*나 / 연산이 오면 잠시 대기하고 +나 -가 오면 연산을 실행하기
        //*와 /가 연결되면 그 부분만 미리 계산한다.

        String newText = "";
        NumButton.returnNum = "";
        Calculator.op = oprText;
        equation.add(oprText);
        
        for(String text: equation){
            newText += text;
        }
        equationField.setText(newText);

        //Equal을 실행해서 valudField에 보낸다.


        //연산이 실행되면 결과값을 출력하기
        //valueField.setText("");
    }
}
