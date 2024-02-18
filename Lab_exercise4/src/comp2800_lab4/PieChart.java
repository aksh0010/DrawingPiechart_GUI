package comp2800_lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;

public class PieChart {
    private JTextField[] inputFields; // Text fields for user input
    private JButton draw_btn; // Button to trigger drawing the chart
    private JButton rotate_btn; // Button to trigger rotating the chart
    private JPanel chart_panel; // Panel to display the pie chart
    private JFrame mainframe; // Main frame for the application
    private double[] values; // Array to store the values of the pie chart segments
    private Color[] colors; // Array to store the colors of the pie chart segments

    // Constructor to initialize the pie chart
    public PieChart() {
        initialize();
    }

    // Method to initialize the GUI components
    public void initialize() {
        mainframe = new JFrame(); // Initialize mainframe
        mainframe.setTitle("Pie Chart"); // Set the title of the window
        mainframe.setSize(640, 400); // Set the size of the window
        mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close operation when window is closed
        mainframe.setLayout(new BorderLayout()); // Set the layout of the main frame
        mainframe.setLocationRelativeTo(null);
        JPanel inputPanel = new JPanel(new GridLayout(5, 1)); // Panel for input components
        inputFields = new JTextField[4]; // Create text fields for input
        for (int i = 0; i < 4; i++) {
            inputFields[i] = new JTextField(10); // Initialize each text field
            inputPanel.add(inputFields[i]); // Add text field to input panel
        }

        draw_btn = new JButton("Draw"); // Button to draw the pie chart
        draw_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Action listener to handle drawing the pie chart
                double[] newValues = new double[4];
                boolean validInput = true; // Flag to check if all inputs are valid integers
                for (int i = 0; i < 4; i++) {
                    try {
                        // Attempt to parse the input as an integer
                        int intValue = Integer.parseInt(inputFields[i].getText());
                        newValues[i] = intValue; // Assign the parsed integer to the values array
                    } catch (NumberFormatException ex) {
                        // If parsing fails, set the flag to false and break the loop
                        validInput = false;
                        break;
                    }
                }

                if (validInput) {
                    // If all inputs are valid integers, proceed with updating the chart
                    setValues(newValues);
                    repaintChart();
                } else {
                    // If any input is invalid, display an error message
                    JOptionPane.showMessageDialog(mainframe, "Please enter valid integer values only.");
                }
            }
        });

        rotate_btn = new JButton("Rotate"); // Button to rotate the pie chart
        rotate_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Action listener to handle rotating the pie chart
                rotate();
                repaintChart();
            }
        });
        inputPanel.add(draw_btn); // Add draw button to input panel
        inputPanel.add(rotate_btn); // Add rotate button to input panel

        chart_panel = new JPanel() {
            // Panel to display the pie chart
            protected void paintComponent(Graphics g) {
                // Override the paintComponent method to draw the pie chart
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                double total = 0;
                for (int i = 0; i < values.length; i++) {
                    total += values[i];
                }

                double startAngle = 0;
                for (int i = 0; i < values.length; i++) {
                    double extent = 360 * (values[i] / total);
                    g2d.setColor(colors[i]);
                    g2d.fill(new Arc2D.Double(100, 100, 200, 200, startAngle, extent, Arc2D.PIE));
                    startAngle += extent;
                }
            }
        };

        mainframe.add(inputPanel, BorderLayout.WEST); // Add input panel to the left side of the main frame
        mainframe.add(chart_panel, BorderLayout.CENTER); // Add chart panel to the center of the main frame

        // Initialize values and colors
        values = new double[]{0, 0, 0, 0}; // Initial values for the pie chart segments
        colors = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE}; // Initial colors for the pie chart segments
    }

    // Method to display the main frame
    public void show() {
        mainframe.setVisible(true);
    }

    // Method to set new values for the pie chart
    public void setValues(double[] newValues) {
        values = newValues;
    }

    // Method to rotate the pie chart
    public void rotate() {
        double tempValue = values[0];
        Color tempColor = colors[0];
        
        for (int i = 0; i < values.length - 1; i++) {
            values[i] = values[i + 1];
            colors[i] = colors[i + 1];
        }
        
        values[values.length - 1] = tempValue;
        colors[colors.length - 1] = tempColor;
    }

    // Method to repaint the chart panel
    public void repaintChart() {
        chart_panel.repaint();
    }

}
