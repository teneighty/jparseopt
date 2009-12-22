package com.jparseopt;

import com.jparseopt.OptionParser.OptType;

import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class Option {
    public Option(String name, String desc, String... args) {
        this(name, desc, OptType.OPTIONAL, args);
    }

    public Option(String name, String desc, OptType type, String... args) {
        this.name = name;
        this.desc = desc;
        this.args = args;
        this.type = type;

        this.optsArgs  = new HashMap<String, Option>();
        this.optsNames = new LinkedHashMap<String, Option>();
        this.argMaxLen = new ArrayList<Integer>();

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

    /**
     *  Add an option.
     */
    public void addOption(Option o) {
        optsNames.put(o.getName(), o);

        int i = 0;
        for (String s : o.getArgs()) {
            updateMaxLength(i, s);
            optsArgs.put(s, o);
            i++;
        }
    }

    public void addOptions(Option... os) {
        for (Option o : os) {
            addOption(o);
        }
    }

    /**
     * Grab an Option object.
     */
    public Option getOpt(String optName) {
        return optsNames.get(optName);
    }

    public Map <String, Option> getOptMap() {
        return optsNames;
    }

    public Map <String, Option> getOptArgMap() {
        return optsArgs;
    }

    /**
     * Returns a string to display usage.
     */
    public String getUsage() {
        StringBuffer usage = new StringBuffer("");
        for (Option o : optsNames.values()) {
            if (o.getHidden())
                continue;
            for (int i = 0; i < argMaxLen.size(); ++i) {
                String s = (i < o.getArgs().length) ? o.getArgs()[i] : "";
                int width = argMaxLen.get(i).intValue() + 1;
                String f = String.format(" %-" + width + "s", s);
                usage.append(f).append(" ");
            }
            usage.append(" ").append(o.getDesc()).append("\n");
        }
        return usage.toString();
    }

    public String toString() {
        return name + ":" + value;
    }

    private void updateMaxLength(int i, String s) {
        while (argMaxLen.size() <= i)
            argMaxLen.add(0);

        Integer max = argMaxLen.get(i);
        argMaxLen.set(i, Math.max(max, new Integer(s.length())));
    }

    protected Map <String, Option> optsArgs;
    protected Map <String, Option> optsNames;
    protected List <Integer> argMaxLen;

    protected String name;
    protected String desc;
    protected String[] args;
    protected String value;
    protected OptType type;
    protected boolean requiresValue;
    protected boolean hidden;
    public static final String ON = "on";

}
