package Domain.Type;

import Domain.Value.IntValue;
import Domain.Value.Value;

public class IntType implements Type {
    public boolean equals(Object another){
        return another instanceof IntType;
    }
    public String toString(){return "int";}

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }
}
