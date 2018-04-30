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
         * calculate remain numbers and operators
         */
        if(!equation.isEmpty()){
            lastInput = valueField.getText();
            preInput = numbers.pop();
            operator = operators.pop();

            left = Integer.parseInt(preInput);
            right = Integer.parseInt(lastInput);
            result = operation.calculate(operator, left, right);

            if(!operators.isEmpty()) {
                numbers.push(Integer.toString(result));

                //calculate remain numbers in stack
                while(true) {
                    operator = operators.pop();

                    right = Integer.parseInt(numbers.pop());
                    updateEquationField();
                    left = Integer.parseInt(numbers.pop());
                    result = calculate(operator, left, right);

                    numbers.push(Integer.toString(result));

                    if(operators.isEmpty()) {
                        break;
                    }
                }
            }

            //reset
            String equations = updateEquationField();
            OperBttnAction.numbers.clear();
            OperBttnAction.operators.clear();
            equation.clear();
            valueField.setText("");

            //update valueField and equationField to print result
            operation.updateValueField(Integer.toString(result));
            equationField.setText(equations);
        }
    }

    private String updateEquationField() {
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

