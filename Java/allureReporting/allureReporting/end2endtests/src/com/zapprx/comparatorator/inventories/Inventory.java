package com.zapprx.comparatorator.inventories;

import java.util.Set;

public interface Inventory {

    public abstract FormField get(String path);

    public abstract boolean contains(String path);

    public abstract Set<String> keys();

}