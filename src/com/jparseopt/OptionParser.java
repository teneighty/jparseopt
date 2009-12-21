package com.jparseopt;

import java.util.ArrayList;
import java.util.List;

import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

public class OptionParser {

    public enum OptType { REQUIRED, OPTIONAL };

    public OptionParser() {
        this.optsArgs  = new HashMap<String, Option>();
        this.optsNames = new LinkedHashMap<String, Option>();
        this.argMaxLen = new ArrayList<Integer>();
    }

    /**
     *  Add an option.
     */
    public OptionParser addOption(Option o) {
        optsNames.put(o.getName(), o);

        int i = 0;
        for (String s : o.getArgs()) {
            updateMaxLength(i, s);
            optsArgs.put(s, o);
            i++;
        }
        return this;
    }

    /**
     * Add many options
     */
    public OptionParser addOptions(Option... os) {
        for (Option o  : os) {
            addOption(o);
        }
        return this;
    }

    /**
     * Parse arguments.
     */
    public void parseArgs(String[] args) throws OptionParseException {
        for (int i = 0; i < args.length; ++i) {
            String a = args[i];
            int equalIdx = a.indexOf("=");
            if (equalIdx > -1) {
                equalIdx++;
                a = a.substring(0, equalIdx);
            }

            Option o = optsArgs.get(a);
            if (null != o) {
                if (!o.getRequiresValue()) {
                    o.setValue(Option.ON);
                }
                else if (equalIdx > -1) {
                    String v = args[i].substring(equalIdx);
                    o.setValue(v);
                }
                else if (i + 1 < args.length) {
                    i++;
                    o.setValue(args[i]);
                }
            }
        }
    }

    /**
     * Are we missing anything that was required?
     */
    public List <Option> checkRequired() {
        List <Option> missing = new ArrayList<Option>();
        for (Option o : optsNames.values()) {
            if (o.getType() == OptType.REQUIRED && o.getValue() == null) {
                missing.add(o);
            }
        }
        return missing;
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

    /**
     * Grab an Option object.
     */
    public Option getOpt(String optName) {
        return optsNames.get(optName);
    }

    public Map <String, Option> getOptMap() {
        return optsNames;
    }

    private void updateMaxLength(int i, String s) {
        while (argMaxLen.size() <= i)
            argMaxLen.add(0);

        Integer max = argMaxLen.get(i);
        argMaxLen.set(i, Math.max(max, new Integer(s.length())));
    }

    private Map <String, Option> optsArgs;
    private Map <String, Option> optsNames;

    private List <Integer> argMaxLen;
}

