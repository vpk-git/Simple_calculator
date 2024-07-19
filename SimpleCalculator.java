import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame implements ActionListener 
{
    private JTextField display;
    private JPanel panel, topPanel;
    private double num1, result;
    private String operator;
    private boolean isOperatorPressed;

    public SimpleCalculator() 
    {
        // Frame setup
        setTitle("Simple Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display setup
        display = new JTextField();
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.DARK_GRAY);
        display.setForeground(Color.CYAN); 
        display.setPreferredSize(new Dimension(400, 50)); // Adjust the display size
        add(display, BorderLayout.NORTH);

        // Top panel setup for AC and DE buttons
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2, 10, 10));
        topPanel.setBackground(Color.BLACK); 
        add(topPanel, BorderLayout.AFTER_LAST_LINE);

        String[] topButtonLabels = {"AC", "DE"};

        for (String label : topButtonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            if (label.equals("AC")) {
                button.setBackground(Color.ORANGE); 
            } else if (label.equals("DE")) {
                button.setBackground(Color.DARK_GRAY); 
                button.setForeground(Color.CYAN); 
            }
            button.addActionListener(this);
            topPanel.add(button);
        }

        // Panel setup for other buttons
        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4, 10, 10));
        panel.setBackground(Color.BLACK); 
        add(panel, BorderLayout.CENTER);

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "sin", "cos", "tan", "sqrt"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.setBackground(Color.DARK_GRAY); 
            button.setForeground(Color.CYAN); 
            button.addActionListener(this);
            panel.add(button);
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("\\d") || command.equals(".")) {
            display.setText(display.getText() + command);
        } else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/")) {
            if (!isOperatorPressed) {
                num1 = Double.parseDouble(display.getText());
                operator = command;
                display.setText(display.getText() + " " + operator + " ");
                isOperatorPressed = true;
            }
        } else if (command.equals("=")) {
            try {
                String[] parts = display.getText().split(" ");
                if (parts.length == 3) {
                    num1 = Double.parseDouble(parts[0]);
                    operator = parts[1];
                    double num2 = Double.parseDouble(parts[2]);
                    switch (operator) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            if (num2 != 0) {
                                result = num1 / num2;
                            } else {
                                display.setText("Error");
                                return;
                            }
                            break;
                    }
                    display.setText(String.valueOf(result));
                } else if (parts.length == 2) {
                    operator = parts[0];
                    num1 = Double.parseDouble(parts[1]);
                    switch (operator) {
                 
                case "sin":
                    result = Math.sin(Math.toRadians(num1));
                    break;
                case "cos":
                    // Handling the special case of cos(90°)
                    if (num1 == 90) 
                    {
                        result = 0;
                    } else {
                        result = Math.cos(Math.toRadians(num1));
                    }
                    break;
                case "tan":
                    // Handle the special case of tan(90°)
                    if (num1 == 90) 
                    {
                        display.setText("Error");
                        return;
                    } else {
                        result = Math.tan(Math.toRadians(num1));
                    }
                    break;

                        case "sqrt":
                            result = Math.sqrt(num1);
                            break;
                    }
                    display.setText(String.valueOf(result));
                }
                num1 = result = 0;
                operator = "";
                isOperatorPressed = false;
            } catch (Exception ex) {
                display.setText("Error");
            }
            
        } else if (command.equals("AC")) {
            display.setText("");
            num1 = result = 0;
            operator = "";
            isOperatorPressed = false;
        } else if (command.equals("DE")) {
            String currentText = display.getText();
            if (!currentText.isEmpty()) {
                display.setText(currentText.substring(0, currentText.length() - 1));
            }
        } else if (command.equals("sin") || command.equals("cos") || command.equals("tan") || command.equals("sqrt")) {
            if (!isOperatorPressed) {
                operator = command;
                display.setText(operator + " ");
                isOperatorPressed = true;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleCalculator());
    }
}