package Domain.Type;

import Domain.Value.Value;
import Domain.Value.StringValue;
public class StringType implements Type{
    @Override
    public Value defaultValue() {
        String value = "";
        return new StringValue(value);
    }
    @Override
    public String toString(){
        return "string";
    }
    @Override
    public boolean equals(Object another){
        return another instanceof StringType;
    }
}
