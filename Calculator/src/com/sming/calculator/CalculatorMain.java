package com.sming.calculator;

import java.awt.*;

public class CalculatorMain {

    public static void main(String[] args) {
        Calculator ct = new Calculator("Calculator");
        ct.setBounds(300, 300, 300, 300);
        ct.setSize(new Dimension(600, 900));
        ct.setVisible(true);
    }

}
