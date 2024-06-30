package LogicExrressions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {
    private static JTextField exprField;
    private static JTextField varsField;
    private static JTextField resultField;
    private static JPanel treePanel;

    public static void main(String[] args) {
        List<Variable> variables = new ArrayList<>();
        JFrame frame = new JFrame("Expression Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        treePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTree(g, getWidth() / 2, 30, buildTreeFromInput(variables), getWidth() / 4);
            }
        };
        treePanel.setPreferredSize(new Dimension(600, 600));

        JPanel controlPanel = new JPanel(new GridLayout(4, 1));
        exprField = new JTextField("~p+q.~p+q+~c.r+d.r");
        varsField = new JTextField("p=true, q=false, r=true, c=false, d=true");
        JButton evaluateButton = new JButton("Evaluate");
        resultField = new JTextField("Result");

        controlPanel.add(exprField);
        controlPanel.add(varsField);
        controlPanel.add(evaluateButton);
        controlPanel.add(resultField);

        frame.add(treePanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.WEST);

        evaluateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                treePanel.repaint();
                boolean result = new Expression(exprField.getText(), variables).evaluate();
                resultField.setText("Result:" + String.valueOf(result));
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private static TreeNode buildTreeFromInput(List<Variable> variables) {
        String expr = exprField.getText();
        String vars = varsField.getText();

        variables.clear(); 
        String[] varsArray = vars.split(", ");

        for (String var : varsArray) {
            String[] varArray = var.split("=");
            variables.add(new Variable(varArray[0].charAt(0), Boolean.parseBoolean(varArray[1])));
        }
        for (Variable variable : variables) {
            System.out.println(variable);
        }
        
        Expression expression = new Expression(expr, variables);
        return expression.buildExpressionTree();
    }

    private static void drawTree(Graphics g, int x, int y, TreeNode node, int xOffset) {
        if (node == null) {
            return;
        }

        if (Character.isLetter(node.value)) {
            String label = String.valueOf(node.value);
            int fontSize = 20;
            g.setFont(new Font("Arial", Font.BOLD, fontSize));
            int labelWidth = g.getFontMetrics().stringWidth(label);
            int labelY = y + 25;
            g.drawString(label, x - labelWidth / 2, labelY);
        } else {
            BufferedImage image = null;
            try {
                String imageName = "images/" + node.value + ".png";
                image = ImageIO.read(new File(imageName));
                int imgWidth = 25;
                int imgHeight = 25;
                Image img = image.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
                g.drawImage(img, x - imgWidth / 2, y - imgHeight / 2, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (node.left != null) {
            g.drawLine(x, y + 25 / 2, x - xOffset, y + 50);
            drawTree(g, x - xOffset, y + 50, node.left, xOffset / 2);
        }
        if (node.right != null) {
            g.drawLine(x, y + 25 / 2, x + xOffset, y + 50);
            drawTree(g, x + xOffset, y + 50, node.right, xOffset / 2);
        }
    }
}
