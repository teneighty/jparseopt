JParseOpt
==================================
JParseOpt provides a simple interface for parsing command line options.
It will confirm that command line arguments are properly formatted, and that
all the required arguments are included. 

JParseOpt can generate shell completion scripts as well.

jparseopt-completion
----------------------------------
JParseOpt includes a utility which can generate shell completion scripts from
the java code. It uses Java's reflection API to load the OptionParser object
and then writes to stdout the shell completion of choice.

You do not need to use jparseopt-completion to create shell completion
scripts. Look at examples/ShellCompletion.java.

    cd script
    ./jparseopt-completion --help

You can generate completion script for jparseopt-completion.
    mkdir ~/.completions
    ./jparseopt-completion \
        --path ../jparseopt.jar \
        --class com.jparseopt.completion.Completion \
        --attribute opts \
        --executable jparseopt-completion \
        --bash > ~/.completions/_jparseopt-completion

Then `source ~/.completions/_jparseopt`

Create a shell completion script for `./examples/intsexample`:

    ./jparseopt-completion \
        --path ../examples/ \
        --class IntsExample \
        --attribute opts \
        --executable intsexample \
        --bash > ../examples/_intsexample

    source ../examples/_intsexample


TODO
----------------------------------
* Hierarchical Options Example
* Improve Bash completion
* Add Zsh completion
