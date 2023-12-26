package Domain.Type;

import Domain.Value.RefValue;
import Domain.Value.Value;

public class RefType implements Type {
    Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }
    public RefType(){inner=new IntType();}
    public Type getInner() {
        return inner;
    }
    @Override
    public boolean equals(Object another){
        if(another instanceof RefType)
            return true;
        else
            return false;
    }
    public String toString(){return "Ref("+inner.toString()+")";}
    @Override
    public Value defaultValue() {
        return new RefValue(0,inner);
    }
}
