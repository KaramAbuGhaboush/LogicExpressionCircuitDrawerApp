package LogicExrressions;

public class AndOperation implements Operation{
    
    @Override
    public boolean getResult(boolean operand1, boolean operand2) {
        return operand1 && operand2;
    }
}
