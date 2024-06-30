package LogicExrressions;

public class OrOperation implements Operation{

    @Override
    public boolean getResult(boolean operand1, boolean operand2) {
        return operand1 || operand2;
    }
}
