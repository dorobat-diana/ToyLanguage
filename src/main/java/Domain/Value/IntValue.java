package Domain.Value;

import Domain.Type.IntType;
import Domain.Type.Type;

public class IntValue implements Value {
    int val;
    public IntValue(int v){val=v;}
    public Object getvalue(){return val;}
    public String toString(){return Integer.toString(val);}
    public Type getType(){return new IntType();}

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }


    @Override
    public boolean equals(Object another){
        if(another instanceof IntValue)
            return ((IntValue) another).getvalue().equals(val);
        return false;
    }
}
