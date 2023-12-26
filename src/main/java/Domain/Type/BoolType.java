package Domain.Type;

import Domain.Value.BoolValue;
import Domain.Value.Value;

public class BoolType implements Type {
    public boolean equals(Object another){
        return another instanceof BoolType;
    }
    public String toString(){return "bool";}

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
