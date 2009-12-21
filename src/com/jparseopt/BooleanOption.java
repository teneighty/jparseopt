package com.jparseopt;

public class BooleanOption 
    extends Option {

    public BooleanOption(String name, String desc, String... args) {
        super(name, desc, args);
        setRequiresValue(false);
    }

    public String getValue() {
        return String.valueOf(boolValue());
    }

    public boolean boolValue() {
        return Option.ON.equals(value);
    }

    public boolean requiresValue() {
        return false;
    }
}
