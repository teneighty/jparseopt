package com.jparseopt;

import com.jparseopt.OptionParser.OptType;

public class StringOption 
    extends Option {

    public StringOption(String name, String desc, OptType type, String... args) {
        super(name, desc, type, args);
    }
}
