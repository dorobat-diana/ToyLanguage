package Domain.Value;

import Domain.Type.RefType;
import Domain.Type.Type;

public class RefValue implements Value {
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value deepCopy() {
        return new RefValue(address,locationType);
    }

    @Override
    public Object getvalue() {
        return address;
    }
    @Override
    public String toString(){
        String s="";
        s="("+address+","+locationType.toString()+")";
        return s;
    }
}
