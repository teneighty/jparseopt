package com.jparseopt;

import com.jparseopt.OptionParser.OptType;

public class RadioOption 
    extends Option {

    public RadioOption(String name, String desc, OptType type, Option... os) {
        super(name, desc, type, new String[0]);

        this.options = os;
    }

    private Option[] options;
}
