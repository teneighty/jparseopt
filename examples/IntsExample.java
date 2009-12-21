import com.jparseopt.OptionParser;
import com.jparseopt.Option;
import com.jparseopt.BooleanOption;
import com.jparseopt.DateOption;
import com.jparseopt.StringOption;
import com.jparseopt.IntegerOption;
import com.jparseopt.OptionParseException;
import com.jparseopt.OptionParser.OptType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Demonstrates a very simple set of opts.
 */
public class IntsExample {
    public static void main(String[] args) {
        try {
            opts.parseArgs(args);

            tryBooleanOptions(opts);
        }
        catch (OptionParseException e) {
            System.out.println(e.getMessage());
            usage(opts);
        }

        if (opts.checkRequired().size() > 0) {
            System.out.println("Missing required fields");
            usage(opts);
        }

        int x = ((IntegerOption)opts.getOpt("x")).intValue();
        int y = ((IntegerOption)opts.getOpt("y")).intValue();
        System.out.printf("%d + %d = %d\n", x, y, x + y);
    }

    public static void tryBooleanOptions(OptionParser opts) {
        BooleanOption h = (BooleanOption)opts.getOpt("help");
        BooleanOption v = (BooleanOption)opts.getOpt("version");

        if (h.boolValue()) 
            usage(opts);

        if (v.boolValue()) 
            version();
    }

    public static void usage(OptionParser opts) {
        System.out.println("Usage: ");
        System.out.println(opts.getUsage());
        System.exit(0);
    }

    public static void version() {
        System.out.println("Test.java Version 0.0.1");
        System.exit(0);
    }

    public static void printDate(Date d) {
        SimpleDateFormat out = new SimpleDateFormat("dd-MMM-yy");
        System.out.println(out.format(d));
    }

    public static OptionParser opts;

    static {
        opts = new OptionParser();
        opts.addOption(new BooleanOption("help",    "Display Usage",   "-h", "--help"));
        opts.addOption(new BooleanOption("version", "Display Version", "-v", "--version"));
        opts.addOption(new IntegerOption("x",       "Option 1",       OptType.REQUIRED, "-x", "--optionx="));
        opts.addOption(new IntegerOption("y",       "Option 2",       OptType.REQUIRED, "-y", "--optiony="));
        
        opts.addOption(
                new DateOption("date", 
                               "A Date", 
                               OptType.OPTIONAL, 
                               new SimpleDateFormat("yyyy-MM-dd"),
                               "-d", "--date="));
    }
}
