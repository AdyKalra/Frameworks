package com.zapprx.comparatorator.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrivalArgParser {
    public static final String STRING_LIST[] = {};
    private final ArrayList<String> argList = new ArrayList<String>();
    private final Map<String, Flag> flagmap = new HashMap<String, Flag>();

    public static class Flag {
        private final String name;
        private final boolean state;
        private final String parameter;

        protected Flag(String name, boolean state, String parameter) {
            super();
            this.name = name;
            this.state = state;
            this.parameter = parameter;
        }

        public boolean isState() {
            return state;
        }

        public String getParameter() {
            return parameter;
        }

        public String getName() {
            return name;
        }
    }

    public TrivalArgParser(String args[]) {
        for (final String arg : args) {
            final Flag flag = parseFlag(arg);
            if (flag != null) {
                flagmap.put(flag.getName(), flag);
            } else {
                argList.add(arg);
            }
        }
    }

    private Flag parseFlag(String arg) {
        if (arg.startsWith("-")) {
            final String name = arg.startsWith("--") ? arg.substring(2) : arg
                    .substring(1);
            return new Flag(name, true, null);
        }
        return null;
    }

    public boolean hasFlag(String flag) {
        if (flagmap.containsKey(flag)) {
            return flagmap.get(flag).state;
        }
        return false;
    }

    public String[] getArgs() {
        return argList.toArray(STRING_LIST);
    }

    public Map<String, Flag> getFlagmap() {
        return flagmap;
    }
}
