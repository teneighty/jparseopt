import com.jparseopt.*;

public class TreeOpts {
    public static void main(String[] args) {
        try {
            opts.parseArgs(args);
        }
        catch (OptionParseException e) {
            System.out.println(e.getMessage());
            usage(opts);
        }

        if (clone.boolValue()) {
            System.out.println("Selected clone");
            if (help.boolValue()) {
                System.out.println(clone.getUsage());
            }
        }
        else if (commit.boolValue()) {
            System.out.println("Selected commit");
            if (help.boolValue()) {
                System.out.println(commit.getUsage());
            }
        }
        else if (help.boolValue())
            usage(opts);

    }

    public static void usage(OptionParser opts) {
        System.out.println("Usage: ");
        System.out.println(opts.getUsage());
        System.exit(0);
    }

    public static OptionParser opts;

    public static BooleanOption quiet;
    public static BooleanOption verbose;
    public static BooleanOption help;
    public static BooleanOption clone;
    public static BooleanOption local;
    public static BooleanOption commit;

    static {
        opts = new OptionParser();

        quiet   = new BooleanOption("quiet", "Shhh! Quiet.", "-q", "--quiet");
        verbose = new BooleanOption("verbose", "Be Verbose", "-v", "--verbose");
        help    = new BooleanOption("help", "Print Help", "-h", "--help");

        clone = new BooleanOption("clone", "Clone a repo", "clone", "--clone");
        local = new BooleanOption("local", "Local repo", "-l", "--local");

        commit  = new BooleanOption("commit",  "Comit a change", "commit");

        clone.addOptions(local, quiet, help);
        commit.addOptions(verbose, quiet, help);
        opts.addOptions(clone, commit, help);
    }
}
