package com.jparseopt.completion;

import com.jparseopt.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.util.Collection;
import java.util.Map;

/**
 * Generate bash shell completion script
 */
public class Bash 
    extends Shell {

    public void writeCompletion(String cmd, Map <String, Option> options) {
        String func = "_" + cmd;
        System.out.printf("%s() { \n", func);

        System.out.printf("\tlocal cur prev opts \n");
        System.out.printf("\tCOMPREPLY=() \n");
        System.out.printf("\tcur=\"${COMP_WORDS[COMP_CWORD]}\" \n");
        System.out.printf("\tprev=\"${COMP_WORDS[COMP_CWORD-1]}\" \n");
        System.out.printf("\topts=\"%s\" \n\n", createOpts(options.values()));

        System.out.printf("\tcase \"${prev}\" in \n");
        for (Option o : options.values()) {
            if (o.getOptMap().size() == 0)
                continue;

            String pattern = createPattern(o.getArgs());
            String children = createOpts(o.getOptMap().values());
            System.out.printf("\t\t%s) \n", pattern);
            System.out.printf("\t\t\tlocal v=\"%s\" \n ", children);
            System.out.printf("\t\t\tCOMPREPLY=( $(compgen -W \"${v}\" -- ${cur}) ) \n ");
            System.out.printf("\t\t\treturn 0 \n ");
            System.out.printf("\t\t\t;; \n");
        }
        System.out.print("\tesac \n ");

        System.out.printf("\tCOMPREPLY=( $(compgen -W \"${opts}\" -- ${cur}) ) \n");
        System.out.printf("\treturn 0 \n");
        System.out.printf("} \n");
        System.out.printf("complete -F %s %s\n", func, cmd);
    }

    private String createPattern(String[] args) {
        String delim = " | ";
        StringBuffer buf = new StringBuffer("");
        for (String a : args) {
            buf.append("\"").append(a).append("\"").append(delim);
        }
        return buf.toString().replaceAll(" \\| $", "");
    }

    private String createOpts(Collection <Option> os) {
        StringBuffer buf = new StringBuffer("");
        for (Option o : os) {
            for (String a : o.getArgs()) {
                buf.append(a).append(" ");
            }
        }
        return buf.toString();
    }
}
