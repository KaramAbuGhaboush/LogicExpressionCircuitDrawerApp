package LogicExrressions;

public class Variable {
    private Character character;
    private Boolean value;

    public Variable(Character character, Boolean value) {
        this.character = character;
        this.value = value;
    }

    public Character getCharacter() {
        return character;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return "Variable{" + "character=" + character + ", value=" + value + '}';
    }
}
