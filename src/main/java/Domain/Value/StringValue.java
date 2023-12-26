package Domain.Value;

import Domain.Type.StringType;
import Domain.Type.Type;

public class StringValue implements Value{
    String value;
    public StringValue(String value){
        this.value = value;
    }
    public String toString(){
        return this.value;
    }
    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }

    @Override
    public Object getvalue() {
        return this.value;
    }

    @Override
    public boolean equals(Object another){
        if(another instanceof StringValue)
            return ((StringValue) another).getvalue().equals(value);
        return false;
    }

}
