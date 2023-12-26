package Domain.Value;

import Domain.Type.BoolType;
import Domain.Type.Type;

public class BoolValue implements Value {
    boolean val;
    public BoolValue(boolean v){val=v;}
    public Object getvalue(){return val;}
    public String toString(){return Boolean.toString(val);}
    public Type getType(){return new BoolType();}

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }
    @Override
    public boolean equals(Object another){
        if(another instanceof BoolValue)
            return ((BoolValue) another).getvalue().equals(val);
        return false;
    }
}
