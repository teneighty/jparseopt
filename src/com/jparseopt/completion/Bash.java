package com.jparseopt.completion;

import com.jparseopt.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

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
        System.out.printf("\topts=\"");
        for (Option o : options.values()) {
            if (o.getHidden())
                continue;

            for (String s : o.getArgs()) {
                System.out.printf("%s ", s);
            }
        }
        System.out.printf("\" \n");
        System.out.printf("\tCOMPREPLY=( $(compgen -W \"${opts}\" -- ${cur}) ) \n");
        System.out.printf("\treturn 0 \n");
        System.out.printf("} \n");
        System.out.printf("complete -F %s %s\n", func, cmd);
    }
}
