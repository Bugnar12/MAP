package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value{
    private String string_value;
    private static String default_string = "";

    public StringValue()
    {
        string_value = StringValue.default_string;
    }

    public StringValue(String string_value)
    {
        this.string_value = string_value;
    }

    public String getValue(){
        return string_value;
    }

    public void setValue(String new_value)
    {
        this.string_value = new_value;
    }

    @Override
    public String toString() {
        return string_value;
    }
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof StringValue))
            return false;
        StringValue downCastObj = (StringValue) obj;
        //Since we have an object of type StringValue, we can use the methods defined in the class,
        //also the getValue which will return a String, thus the comparison will be possible
        return downCastObj.getValue() == this.string_value;
        //return downCastObj.string_value == this.string_value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }
}
