package Domain.Value;

import Domain.Type.Type;

public interface Value {
    Type getType();
    //BoolValue equals(Value another);
    Value deepCopy();

    Object getvalue();


}
