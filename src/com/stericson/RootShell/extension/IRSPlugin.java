package com.stericson.RootShell.extension;

/**
 * The basic interface for any plugin.
 * If you do not wish to implement all of it, simply subclass AbsRSPlugin.
 *
 * Note that Android, using an older version of Java, does not support static methods in
 * interface definitions.
 * That's too bad as now we cannot offer plugin writers a choice between static and instantiated.
 *
 * Created by Chris on 1/17/15.
 */
public interface IRSPlugin {
    public String[] register();

    public void preprocess(String token);
    public Object process(String token, Object... arg);
    public void postprocess(String token);

    public void bringup();
    public void bringdown();

    public boolean isSandboxed();
}
