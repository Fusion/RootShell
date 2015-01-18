package com.stericson.RootShell.extension;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.stericson.RootShell.RootShell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by Chris on 1/17/15.
 */
public class PM {
    private static HashMap<String, ArrayList<IRSPlugin>> sPlugins =
            new HashMap<String, ArrayList<IRSPlugin>>();

    static public void init(Context context) {
        String pluginStr = null;

        try {
            pluginStr = (String)context
                    .getPackageManager()
                    .getApplicationInfo(
                            context.getPackageName(), PackageManager.GET_META_DATA)
                    .metaData
                    .get("rsplugins");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(null == pluginStr) {
            return;
        }

        String[] pluginNames = pluginStr.replaceAll("\\s","").split(",");
        for(String name:pluginNames) {
            RootShell.log("Loading plugin: " + name);

            IRSPlugin instance = null;
            try {
                Class pluginClass = Class.forName(name);
                instance = (IRSPlugin)pluginClass.newInstance();
            } catch (ClassNotFoundException e) {
                RootShell.log("Plugin '" + name + "' was not found. Did you link all your libraries?");
                e.printStackTrace();
            } catch (InstantiationException e) {
                RootShell.log("Plugin '" + name + "' threw an exception while being instantiated.");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                RootShell.log("Plugin '" + name + "' is not accessible.");
                e.printStackTrace();
            }

            if(null == instance) {
                continue;
            }

            String [] services = instance.register();

            for(String service:services) {
                ArrayList<IRSPlugin> pluginList;
                if(!sPlugins.containsKey(service)) {
                    pluginList = new ArrayList<IRSPlugin>();
                    sPlugins.put(service, pluginList);
                }
                else {
                    pluginList = sPlugins.get(service);
                }

                pluginList.add(instance);
            }
        }
    }

    static public void pr(String token) {

    }

    static public void po(String token) {

    }

    static public R p(String token, Object... arg) {
        R r = new R();
        ArrayList<IRSPlugin> plugins = sPlugins.get(token);
        if(null != plugins) {
            Object newArg = arg;
            for(IRSPlugin plugin:plugins) {
                newArg = plugin.process(token, newArg);
            }
            r.p = true;
            r.r = newArg;
        }
        return r;
    }

    static public class R {
        public boolean p = false;
        public Object r = null;
    }
}
