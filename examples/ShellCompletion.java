import com.jparseopt.*;
import com.jparseopt.completion.Bash;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Demonstrates a very simple set of opts.
 */
public class ShellCompletion {
    public static void main(String[] args) {
        OptionParser opts = new OptionParser();
        BooleanOption help = 
            new BooleanOption("help", "Display Usage", "-h", "--help");
        BooleanOption version = 
            new BooleanOption("version", "Display Version", "-v", "--version");
        BooleanOption bash = 
            new BooleanOption("bash", "Generate bash completion", "-b");
        /* setHidden prevents option from showing in usage */
        bash.setHidden(true);
        opts.addOptions(help, version, bash);

        try {
            opts.parseArgs(args);
        }
        catch (OptionParseException e) {
            System.out.println(e.getMessage());
            usage(opts);
        }

        if (help.boolValue()) {
            usage(opts);
        }
        else if (version.boolValue()) {
            version();
        }
        else if (bash.boolValue()) {
            Bash b = new Bash();
            b.writeCompletion("shellcompletion", opts.getOptMap());
        }
    }

    public static void usage(OptionParser opts) {
        System.out.println("Usage: ");
        System.out.println(opts.getUsage());
        System.exit(0);
    }

    public static void version() {
        System.out.println("ShellCompletion.java Version 0.0.1");
        System.exit(0);
    }
}
