package LogicExrressions;

public class NotOperation implements Operation{
    
    @Override
    public boolean getResult(boolean operand1, boolean operand2) {
        return !operand1;
    }
}
