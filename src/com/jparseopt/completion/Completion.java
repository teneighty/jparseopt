package com.jparseopt.completion;

import com.jparseopt.*;
import com.jparseopt.OptionParser.OptType;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Completion {
    public static void main(String[] args)
    {
        try {
            opts.parseArgs(args);
        }
        catch (OptionParseException e) {
            System.out.println(e.getMessage());
            usage(opts);
        }

        if (help.boolValue())
            usage(opts);

        if (version.boolValue())
            version();

        if (opts.checkRequired().size() > 0) {
            usage(opts);
        }
        Shell shell = null;
        if (bash.boolValue())
            shell = new Bash();
        else if (zsh.boolValue())
            shell = new Zsh();

        run(clazz.getValue(), attr.getValue(), 
            path.getValue(), exe.getValue(), shell);
    }

    public static void run(String clazz, String attr, 
                           String path, String exe, Shell shell) {
        try {
            URL u = new URL("file:" + path);
            URLClassLoader classLoader = new URLClassLoader(new URL[] { u } );
            Thread.currentThread().setContextClassLoader(classLoader);
            Class c = classLoader.loadClass(clazz);
            Field f = c.getField(attr);
            Object i = c.newInstance();
            OptionParser opts = new OptionParser();
            opts = (OptionParser)f.get(i);

            if (null == shell)
                System.err.println("No shell selected");
            else if (null == opts)
                System.err.println("Unabled to load " + attr);
            else {
                shell.writeCompletion(exe, opts.getOptMap());
            }
        }
        catch (InstantiationException e) {
            System.err.println("Unabled to instantiate " + clazz);
        }
        catch (MalformedURLException e) {
            System.err.println("Unabled to find file:" + path);
        }
        catch (ClassNotFoundException e) {
            System.err.println("Unabled to load " + clazz);
        }
        catch (NoSuchFieldException e) {
            System.err.println("Unabled to load attr " + attr);
        }
        catch (IllegalAccessException e) {
            System.err.println("Illegal Access to " + attr + " in " + clazz);
        }
    }

    public static void usage(OptionParser opts) {
        System.out.println("Usage: ");
        System.out.println(opts.getUsage());
        System.exit(0);
    }

    public static void version() {
        System.out.println("JParseOpt-Completion: Version @version@");
        System.exit(0);
    }

    public static OptionParser opts;
    public static BooleanOption help;
    public static BooleanOption version;

        /* Shells */
    public static BooleanOption bash;  
    public static BooleanOption zsh;

        /* OptionParse Location */
    public static StringOption exe;
    public static StringOption path;
    public static StringOption clazz;
    public static StringOption attr;

    static { 
        opts = new OptionParser();
        help    = new BooleanOption("help",    "Display Usage",   "-h", "--help");
        version = new BooleanOption("version", "Display Version", "-v", "--version");

        /* Shells */
        bash = new BooleanOption("bash",     "Create Bash Completion", "-b", "--bash"); 
        zsh  = new BooleanOption("zsh",      "Create Zsh Completion",  "-z", "--zsh");

        /* OptionParse Location */
        exe   = new StringOption("exe",      "Executable Name", OptType.REQUIRED, "-e", "--executable");
        path  = new StringOption("path",     "Path to Class",   OptType.REQUIRED, "-p", "--path");
        clazz = new StringOption("class",    "Class",           OptType.REQUIRED, "-c", "--class");
        attr  = new StringOption("opts",     "Attribute",       OptType.REQUIRED, "-a", "--attribute");

        opts.addOptions(help, version, bash, path, exe, clazz, attr);
    }
}
