package LogicExrressions;

import java.util.List;
import java.util.Stack;

public class Expression {
    private String expression;
    private List<Variable> variables;
    private Stack<Character> postfix = new Stack<>();

    public Expression(String expression, List<Variable> variables) {
        this.expression = expression;
        this.variables = variables;
    }

    private void convertToPostfix() {
        Stack<Character> operators = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isLetter(c)) {  
                postfix.push(c);
            } else if (c == '~') {  
                while (!operators.isEmpty() && operators.peek() == '~') {
                    postfix.push(operators.pop());
                }
                operators.push(c);
            } else if (c == '+' || c == '.') {
                while (!operators.isEmpty() && (operators.peek() == '~' || (operators.peek() == '.' && c == '.') || (operators.peek() == '+' && c == '+'))) {
                    postfix.push(operators.pop());
                }
                operators.push(c);
            }
        }
        while (!operators.isEmpty()) {
            postfix.push(operators.pop());
        }
        System.out.println(postfix);
    }
    

    public boolean evaluate() {
        convertToPostfix();
        Stack<Boolean> operands = new Stack<>();
        for (int i = 0; i < postfix.size(); i++) {
            char c = postfix.get(i);
            if (c == '.') {
                if (operands.size() < 2) {
                    throw new IllegalArgumentException("And operation requires exactly 2 operands");
                }
                boolean operand1 = operands.pop();
                boolean operand2 = operands.pop();
                AndOperation andOperation = new AndOperation();
                operands.push(andOperation.getResult(operand1, operand2));
            } else if (c == '+') {
                if (operands.size() < 2) {
                    throw new IllegalArgumentException("Or operation requires exactly 2 operands");
                }
                boolean operand1 = operands.pop();
                boolean operand2 = operands.pop();
                OrOperation orOperation = new OrOperation();
                operands.push(orOperation.getResult(operand1, operand2));
            } else if (c == '~') {
                if (operands.isEmpty()) {
                    throw new IllegalArgumentException("Not operation requires exactly 1 operand");
                }
                boolean operand = operands.pop();
                NotOperation notOperation = new NotOperation();
                operands.push(notOperation.getResult(operand, false));
            } else {
                for (Variable variable : variables) {
                    if (variable.getCharacter() == c) {
                        operands.push(variable.getValue());
                    }
                }
            }
        }
        if (operands.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }
        System.out.println(operands.peek());
        return operands.pop();
    }

    public TreeNode buildExpressionTree() {
        convertToPostfix();
        Stack<TreeNode> stack = new Stack<>();
        for (char c : postfix) {
            if (c == '~' || c == '+' || c == '.') {
                TreeNode node = new TreeNode(c);
                if (c != '~') {
                    node.right = stack.pop();
                }
                node.left = stack.pop();
                stack.push(node);
            } else {
                stack.push(new TreeNode(c));
            }
        }
        return stack.pop();
    }
    
    // Setters and Getters
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }
}
