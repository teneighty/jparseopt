package com.jparseopt;

import com.jparseopt.OptionParser.OptType;

public abstract class Option {
    public Option(String name, String desc, String... args) {
        this(name, desc, OptType.OPTIONAL, args);
    }

    public Option(String name, String desc, OptType type, String... args) {
        this.name = name;
        this.desc = desc;
        this.args = args;
        this.type = type;
        setRequiresValue(true);
        setHidden(false);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        if (OptType.REQUIRED == this.type) {
            return desc + " (Required)";
        }
        else {
            return desc;
        }
    }

    public void setArgs(String[] a) {
        this.args = a;
    }

    public String[] getArgs() {
        return args;
    }

    public void setType(OptType t) {
        this.type = t;
    }

    public OptType getType() {
        return type;
    }

    public void setValue(String v) throws OptionParseException {
        this.value = v;
    }

    public String getValue() {
        return value;
    }

    public void setRequiresValue(boolean b) {
        this.requiresValue = b;
    }

    public boolean getRequiresValue() {
        return requiresValue;
    }

    public void setHidden(boolean b) {
        this.hidden = b;
    }

    public boolean getHidden() {
        return hidden;
    }

    public String toString() {
        return name + ":" + value;
    }

    protected String name;
    protected String desc;
    protected String[] args;
    protected String value;
    protected OptType type;
    protected boolean requiresValue;
    protected boolean hidden;
    public static final String ON = "on";
}
