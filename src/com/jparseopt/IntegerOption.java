package com.jparseopt;

import com.jparseopt.OptionParser.OptType;

public class IntegerOption 
    extends Option {

    public IntegerOption(String name, String desc, OptType type, String... args) {
        super(name, desc, type, args);
    }

    public void setValue(String s) throws OptionParseException {
        try {
            ivalue = new Integer(s).intValue();
        }
        catch (NumberFormatException e) {
            throw new IncorrectFormatException(s + " is not an integer");
        }
    }

    public String getValue() {
        if (ivalue == null)
            return null;
        else
            return String.valueOf(ivalue);
    }

    public int intValue() {
        return ivalue.intValue();
    }

    private Integer ivalue;
}
