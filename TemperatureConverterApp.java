package com.example.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TemperatureConverterApp extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;
    private JComboBox<String> fromScale, toScale;

    public TemperatureConverterApp() {
        setTitle("Temperature Converter");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Konversi Suhu", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input & Skala"));
        inputPanel.add(new JLabel("Masukkan Suhu:"));
        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        inputPanel.add(inputField);

        inputPanel.add(new JLabel("Dari Skala:"));
        fromScale = new JComboBox<>(new String[]{"Celcius", "Fahrenheit", "Reamur", "Kelvin"});
        fromScale.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputPanel.add(fromScale);

        inputPanel.add(new JLabel("Ke Skala:"));
        toScale = new JComboBox<>(new String[]{"Celcius", "Fahrenheit", "Reamur", "Kelvin"});
        toScale.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputPanel.add(toScale);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel resultPanel = new JPanel(new BorderLayout());
        JButton convertButton = new JButton("Konversi");
        convertButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        resultLabel = new JLabel("Hasil akan ditampilkan di sini.", JLabel.CENTER);
        resultLabel.setFont(new Font("Serif", Font.ITALIC, 18));
        resultLabel.setForeground(Color.BLUE);
        resultPanel.add(convertButton, BorderLayout.NORTH);
        resultPanel.add(resultLabel, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        add(mainPanel);

        convertButton.addActionListener(e -> convertTemperature());
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '.' && e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                    JOptionPane.showMessageDialog(null, "Masukkan hanya angka!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!inputField.getText().isEmpty()) {
                    convertTemperature();
                }
            }
        });
    }

    private void convertTemperature() {
        try {
            String input = inputField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Input tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double value = Double.parseDouble(input);
            String from = (String) fromScale.getSelectedItem();
            String to = (String) toScale.getSelectedItem();
            double result = calculateConversion(value, from, to);

            resultLabel.setText(String.format("Hasil: %.2f %s", result, to));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double calculateConversion(double value, String from, String to) {
        double celcius;
        switch (from) {
            case "Fahrenheit":
                celcius = (value - 32) * 5 / 9;
                break;
            case "Reamur":
                celcius = value * 5 / 4;
                break;
            case "Kelvin":
                celcius = value - 273.15;
                break;
            default:
                celcius = value;
        }

        switch (to) {
            case "Fahrenheit":
                return celcius * 9 / 5 + 32;
            case "Reamur":
                return celcius * 4 / 5;
            case "Kelvin":
                return celcius + 273.15;
            default:
                return celcius;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TemperatureConverterApp app = new TemperatureConverterApp();
            app.setVisible(true);
        });
    }
}
