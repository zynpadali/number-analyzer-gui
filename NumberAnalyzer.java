import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// ProcessableValue Interface
interface ProcessableValue {
    double getValue();
}

// MyNumber Class
class MyNumber implements ProcessableValue {
    private double number;

    public MyNumber(double number) {
        this.number = number;
    }

    @Override
    public double getValue() {
        return number;
    }
}

// NumberCollector Class
class NumberCollector {
    private ArrayList<MyNumber> numbers;

    public NumberCollector() {
        numbers = new ArrayList<>();
    }

    public void addNumber(MyNumber num) {
        numbers.add(num);
    }

    public double calculateSum() {
        double total = 0;
        for (MyNumber num : numbers) {
            total += num.getValue();
        }
        return total;
    }

    public ArrayList<Double> getAllNumbers() {
        ArrayList<Double> values = new ArrayList<>();
        for (MyNumber num : numbers) {
            values.add(num.getValue());
        }
        return values;
    }

    public void saveNumbersToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (MyNumber num : numbers) {
                writer.write(String.valueOf(num.getValue()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving numbers to file: " + e.getMessage());
        }
    }
}

// Main Application with GUI
public class NumberAnalyzer {
    private JFrame frame;
    private JTextField inputField;
    private JLabel resultLabel;
    private NumberCollector collector;

    public NumberAnalyzer() {
        collector = new NumberCollector();
        frame = new JFrame("Simple Number Analyzer");
        inputField = new JTextField(10);
        resultLabel = new JLabel("Enter a number and click 'Add Number'.");

        JButton addButton = new JButton("Add Number");
        JButton calculateButton = new JButton("Calculate Sum & Save");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double number = Double.parseDouble(inputField.getText());
                    collector.addNumber(new MyNumber(number));
                    resultLabel.setText("Number added. Total items: " + collector.getAllNumbers().size());
                    inputField.setText("");
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Invalid number. Please enter a valid number.");
                }
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double total = collector.calculateSum();
                collector.saveNumbersToFile("numbers.txt");
                resultLabel.setText("Sum: " + total + ". Numbers saved to numbers.txt.");
            }
        });

        JPanel panel = new JPanel();
        panel.add(inputField);
        panel.add(addButton);
        panel.add(calculateButton);
        panel.add(resultLabel);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberAnalyzer::new);
    }
}