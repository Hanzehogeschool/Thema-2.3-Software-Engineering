package gui.views;

import multiformat.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorView extends JPanel implements ActionListener {

    JPanel container = new JPanel(new BorderLayout());
    JPanel baseContainer = new JPanel(new GridLayout(1, 0));
    JPanel operationContainer = new JPanel(new GridLayout(0, 1));
    JPanel formatContainer = new JPanel(new GridLayout(1, 0));
    JPanel baseFormatContainer = new JPanel(new GridLayout(0, 1));
    JPanel butContainer = new JPanel(new GridLayout(0, 3));
    JPanel topContainer = new JPanel(new GridLayout(1, 0));
    private String baseDigits;
    private Calculator calculator;
    private String inputNumber = "";
    private JTextField firstOperandField;
    private JTextField secondOperandField;
    private JTextField outputField;
    private JButton[] buttons = new JButton[20];
    private JButton enter;
    private JButton point;
    private String fontFamily = "Arial";
    private JButton[] baseButtons = {
            new JButton("Binary"),
            new JButton("Decimal"),
            new JButton("Octal"),
            new JButton("Hexadecimal")
    };
    private JButton[] operationButtons = {
            new JButton("+"),
            new JButton("-"),
            new JButton("*"),
            new JButton("/"),
    };
    private JButton[] formatButtons = {
            new JButton("Fixed"),
            new JButton("Float"),
            new JButton("Rational")
    };

    public CalculatorView(Calculator calculator) {
        this.calculator = calculator;
        this.setLayout(new BorderLayout());

        outputField = new JTextField("Enter number...");
        outputField.setPreferredSize(new Dimension(250, 100));
        outputField.setEditable(false);
        outputField.setFont(new Font(fontFamily, Font.BOLD, 20));
        topContainer.add(outputField);

        JPanel operandPanel = new JPanel(new GridLayout(0, 1));
        firstOperandField = new JTextField(this.calculator.firstOperand());
        firstOperandField.setEditable(false);
        firstOperandField.setFont(new Font(fontFamily, Font.BOLD, 20));
        operandPanel.add(firstOperandField);

        secondOperandField = new JTextField(this.calculator.secondOperand());
        secondOperandField.setEditable(false);
        secondOperandField.setFont(new Font(fontFamily, Font.BOLD, 20));
        operandPanel.add(secondOperandField);

        topContainer.add(operandPanel);
        this.add(topContainer, BorderLayout.NORTH);

        setButtons();
        container.add(butContainer, BorderLayout.CENTER);

        for (int b = 0; b < baseButtons.length; b++) {
            baseButtons[b].addActionListener(this);
            baseContainer.add(baseButtons[b]);
        }

        baseContainer.setPreferredSize(new Dimension(500, 50));
        baseFormatContainer.add(baseContainer);

        for (int a = 0; a < formatButtons.length; a++) {
            formatButtons[a].addActionListener(this);
            formatContainer.add(formatButtons[a]);
        }

        baseFormatContainer.add(formatContainer);
        container.add(baseFormatContainer, BorderLayout.NORTH);

        for (int i = 0; i < operationButtons.length; i++) {
            operationButtons[i].setFont(new Font(fontFamily, Font.BOLD, 20));
            operationButtons[i].addActionListener(this);
            operationContainer.add(operationButtons[i]);
        }

        operationContainer.setPreferredSize(new Dimension(75, butContainer.HEIGHT));
        container.add(operationContainer, BorderLayout.EAST);

        this.add(container, BorderLayout.CENTER);
    }

    private void setButtons() {
        butContainer.removeAll();
        baseDigits = calculator.getBase().getDigits();
        buttons = new JButton[20];

        for (int i = 0; i < baseDigits.length(); i++) {
            buttons[i] = new JButton(Character.toString(baseDigits.charAt(i)));
            buttons[i].setFont(new Font(fontFamily, Font.BOLD, 20));
            buttons[i].addActionListener(this);
            butContainer.add(buttons[i]);
        }

        point = new JButton(".");
        point.setFont(new Font(fontFamily, Font.BOLD, 20));
        point.addActionListener(this);
        butContainer.add(point);

        enter = new JButton("Enter");
        enter.setFont(new Font(fontFamily, Font.BOLD, 20));
        enter.addActionListener(this);
        butContainer.add(enter);

        butContainer.repaint();
        butContainer.revalidate();
    }

    private void updateAllText() {
        firstOperandField.setText(calculator.firstOperand());
        secondOperandField.setText(calculator.secondOperand());
        outputField.setText(inputNumber);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton but = (JButton) e.getSource();

        if (inputNumber == "[Incorrect entry]")
            inputNumber = "";

        if (but == enter && inputNumber != "") {
            try {
                calculator.addOperand(inputNumber);
                inputNumber = "";
            } catch (FormatException e1) {
                inputNumber = "[Incorrect entry]";
            }
        } else if (baseDigits.indexOf(but.getText()) >= 0) {
            inputNumber += but.getText();
        } else if (but == point) {
            inputNumber += ".";
        }

        for (JButton operationButton : operationButtons) {
            if (but == operationButton) {
                String format = operationButton.getText();

                if (format.equals("+")) {
                    if (inputNumber == "") calculator.add();
                    else inputNumber += "+";
                } else if (format.equals("-")) {
                    if (inputNumber == "") calculator.subtract();
                    else inputNumber += "-";
                } else if (format.equals("/")) {
                    if (inputNumber == "") calculator.divide();
                    else inputNumber += "/";
                } else if (format.equals("*")) {
                    if (inputNumber == "") calculator.multiply();
                    else inputNumber += "*";
                }
            }
        }

        for (JButton baseButton : baseButtons) {
            if (but == baseButton) {
                String format = baseButton.getText().toLowerCase();

                if (format.equals("bin")) {
                    calculator.setBase(new BinaryBase());
                } else if (format.equals("dec")) {
                    calculator.setBase(new DecimalBase());
                } else if (format.equals("oct")) {
                    calculator.setBase(new OctalBase());
                } else if (format.equals("hex")) {
                    calculator.setBase(new HexBase());
                }

                setButtons();
            }
        }

        for (JButton formatButton : formatButtons) {
            if (but == formatButton) {
                String format = formatButton.getText().toLowerCase();

                if (format.equals("fixed")) {
                    calculator.setFormat(new FixedPointFormat());
                } else if (format.equals("float")) {
                    calculator.setFormat(new FloatingPointFormat());
                } else if (format.equals("rational")) {
                    calculator.setFormat(new RationalFormat());
                }
            }
        }

        updateAllText();
    }

}
