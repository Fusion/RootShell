package com.stericson.RootShell.extension;

/**
 * Abstract implementation of the IRSPlugin interface.
 * This class tells us it is not to run in a sandboxed environment (not implemented yet anyway)
 * It is convenient to write a simple plugin to replace a default process() method.
 *
 * Created by Chris on 1/17/15.
 */
public abstract class AbsRSPlugin implements IRSPlugin {
    @Override
    public void preprocess(String token) {}

    @Override
    public void postprocess(String token) {}

    @Override
    public void bringup() {}

    @Override
    public void bringdown() {}

    @Override
    public boolean isSandboxed() { return false; }
}
