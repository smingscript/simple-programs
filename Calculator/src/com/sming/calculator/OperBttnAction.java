package com.sming.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JTextField;

public class OperBttnAction implements ActionListener {
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
    
    public OperBttnAction(JTextField valueField, JTextField equationField) {
        this.valueField= valueField;
        this.equationField = equationField;
    }
    
    public OperBttnAction(JTextField valueField, JTextField equationField, String oprText, LinkedList<String> equation) {
        this.valueField= valueField;
        this.equationField = equationField;
        this.currentOp = oprText;
        this.equation = equation;

        operators = new Stack<String>();
        numbers = new Stack<String>();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        //get input to calculate from valuefield and push it to numbers stack
        if(currentOp != "("){
            numbers.push(valueField.getText());
        }

        valueField.setText("");

        if(operators.isEmpty()) {
            operators.push(currentOp);
        }
        else {
            prevOp = operators.pop();

            //update operator weight and compare both
            getOperatorWeight(currentOp, prevOp);

            //currentOp > prevOp: don't calculate and stack both operators
            if(prevOp == "(" || currentWeight > previousWeight) {
                if(numbers.indexOf("0") == 0)
                    numbers.remove(0);

                operators.push(prevOp);
                operators.push(currentOp);
            }

            //currentOp < prevOp: calculate until operators become empty
            if(previousWeight != 3 && currentWeight < previousWeight) {
                while(true) {
                    right = Integer.parseInt(numbers.pop());
                    left = Integer.parseInt(numbers.pop());
                    result = calculate(prevOp, left, right);

                    numbers.push(Integer.toString(result));

                    if(operators.isEmpty() || operators.peek() == "(") {
                        if(prevOp == "(")
                            operators.push("(");
                        break;
                    }
                    prevOp = operators.pop();
                }

                operators.push(currentOp);
                updateValueField(Integer.toString(result));
            }
            //ending parenthesis
            if(currentOp == ")") {
                operators.pop();

                while(true) {
                    prevOp = operators.pop();
                    if(operators.isEmpty() || prevOp.equals("(")) {
                        updateValueField(Integer.toString(result));
                        numbers.pop();
                        break;
                    }
                    else {
                        right = Integer.parseInt(numbers.pop());
                        left = Integer.parseInt(numbers.pop());
                        result = calculate(prevOp, left, right);

                        numbers.push(Integer.toString(result));
                    }
                }
            }

            //currentOp = prevOp: calculate once
            if(currentWeight == previousWeight) {
                right = Integer.parseInt(numbers.pop());
                if(!numbers.isEmpty()) {
                    left = Integer.parseInt(numbers.pop());
                    result = calculate(prevOp, left, right);
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
    
    private void updateEquationField() {
        StringBuilder newText = new StringBuilder();

        equation.add(currentOp);
        
        for(String text: equation){
            newText.append(text);
        }
        equationField.setText(newText.toString());
    }
    
    protected void updateValueField(String result) {
        valueField.setText(result);
        NumBttnAction.isLongNum = false;
    }
}
