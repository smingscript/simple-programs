package com.sming.calculator;

import java.util.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;


public class Calculator extends JFrame {
    private JPanel textField;
    private JTextField valueField;
    private JTextField equationField;

    private JButton zero,one,two,three,four,five,six,seven,eight,nine;
    private JButton plus,minus,mul,divide;
    private JButton equal,back,clear, clrErr;
    private JButton leftParen, rightParen;
    
    static StringBuffer op;

    private LinkedList<String> equation; //전체 계산식이 표시되는 부분
    
    static LinkedList<String> operationList; //계산을 할 때 썼던 스택용 리스트

    public Calculator(String title){
        super(title);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        design();
    }

    public void design(){
    	operationList = new LinkedList<>();
        equation = new LinkedList<>();
        
        Container ca = this.getContentPane();
        
        //계산기 디스플레이 UI 설정
        valueField = new JTextField("0");
        equationField = new JTextField();
        Font fontValue = new Font("Monospace", Font.PLAIN, 60);
        valueField.setFont(fontValue);
        valueField.setEditable(false);
        valueField.setHorizontalAlignment(JTextField.RIGHT);
        Font fontEquat = new Font("Monospace", Font.PLAIN, 40);
        equationField.setFont(fontEquat);
        equationField.setEditable(false);
        equationField.setHorizontalAlignment(JTextField.RIGHT);

        one = new JButton("1");
        two = new JButton("2");
        three = new JButton("3");
        four = new JButton("4");
        five = new JButton("5");
        six = new JButton("6");
        seven = new JButton("7");
        eight = new JButton("8");
        nine = new JButton("9");
        zero = new JButton("0");

        plus = new JButton("+");
        minus = new JButton("-");
        mul = new JButton("*");
        divide = new JButton("/");

        equal= new JButton("=");
        back= new JButton("←");
        clear= new JButton("C");
        clrErr = new JButton("CE");
        
        leftParen = new JButton("(");
        rightParen = new JButton(")");

        JButton[] numBotton = new JButton[]{zero, one, two, three, four, five, six, seven, eight, nine};

        //숫자 버튼 actionListener
        for(int i = 0; i < numBotton.length; i++){
            numBotton[i].addActionListener(new NumButton(valueField, Integer.toString(i), equation));
        }
               
        plus.addActionListener(new Operation(valueField, equationField, "+", equation));
        minus.addActionListener(new Operation(valueField, equationField, "-", equation));
        mul.addActionListener(new Operation(valueField, equationField, "*", equation));
        divide.addActionListener(new Operation(valueField, equationField, "/", equation));
        leftParen.addActionListener(new Operation(valueField, equationField, "(", equation));
        rightParen.addActionListener(new Operation(valueField, equationField, ")", equation));

        //기능 버튼 actionListener
        equal.addActionListener(new Equal(valueField, equationField));
        clear.addActionListener(new Clear(valueField, equation));
        clrErr.addActionListener(new ClearErr(valueField, equation));
        back.addActionListener(new Back(valueField));

        //배치하기
        final JButton[][] BUTTONS = {
        		{clrErr, clear, back, equal},
        		{seven, eight, nine, divide},
        		{four, five, six, mul},
        		{one, two, three, minus},
        		{leftParen, zero, rightParen, plus}
        };
        
        final Font BTN_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 24);
        
        JPanel btnPanel = new JPanel(new GridLayout(BUTTONS.length, BUTTONS[0].length));
        
        for (JButton[] jButtons : BUTTONS) {
			for (JButton jButton : jButtons) {
				jButton.setFont(BTN_FONT);
				btnPanel.add(jButton);
			}
		}
        
        textField = new JPanel();
        textField.setLayout(new BoxLayout(textField, BoxLayout.Y_AXIS));
        textField.add(equationField);
        textField.add(valueField);

        ca.add(textField,BorderLayout.NORTH);
        ca.add(btnPanel, BorderLayout.CENTER);
    }


}
