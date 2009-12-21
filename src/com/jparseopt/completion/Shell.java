package com.jparseopt.completion;

import com.jparseopt.Option;

import java.util.Map;

public abstract class Shell {

    public abstract void writeCompletion(
                                String cmd, 
                                Map <String, Option> options);
}
