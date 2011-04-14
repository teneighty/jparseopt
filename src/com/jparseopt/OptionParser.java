package com.jparseopt;

import java.util.ArrayList;
import java.util.List;

public class OptionParser 
    extends Option {

    public enum OptType { REQUIRED, OPTIONAL };

    public OptionParser() { 
        super("", ""); 

        setHidden(true);
        args = new ArrayList<String>();
    }

    /**
     * Parse arguments.
     */
    public void parseArgs(String[] args) throws OptionParseException {
        Option source = null;
        for (int i = 0; i < args.length; ++i) {
            String a = args[i];
            int equalIdx = a.indexOf("=");
            if (equalIdx > -1) {
                equalIdx++;
                a = a.substring(0, equalIdx);
            }

            Option o;
            if (null == source)
                o = optsArgs.get(a);
            else
                o = source.getOptArgMap().get(a);

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
                if (o.getOptMap().size() > 0)
                    source = o;
            }
            else {
                this.args.add(a);
            }
        }
    }

    public List<String> getArgs()
    {
        return this.args;
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

    protected List<String> args;
}

