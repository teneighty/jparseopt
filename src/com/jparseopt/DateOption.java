package com.jparseopt;

import com.jparseopt.OptionParser.OptType;

import java.util.Calendar;
import java.util.Date;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

public class DateOption 
    extends Option {

    public DateOption(String name, String desc, OptType type, 
                      SimpleDateFormat format, String... args) {
        super(name, desc, type, args);
        this.dformat = format;
    }

    public void setValue(String s) throws OptionParseException {
        this.value = s;
        if (null == s) 
            return;
        else {
            ParsePosition p = new ParsePosition(0);
            dvalue = dformat.parse(s, p);
            if (null == dvalue) {
                throw new OptionParseException(s + " incorrect date format");
            }
        }
    }

    public Calendar calValue() {
        if (null != dvalue) {
            Calendar c = Calendar.getInstance();
            c.setTime(dvalue);
            return c;
        }
        return null;
    }

    public Date dateValue() {
        return dvalue;
    }

    private Calendar cvalue;
    private Date dvalue;
    private SimpleDateFormat dformat;
}
