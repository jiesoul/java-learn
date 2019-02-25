package com.jiesoul.jcip;

import net.jcip.annotations.Immutable;

import java.util.HashSet;
import java.util.Set;

@Immutable
public class ThreeStooges {
    private final Set<String> stooges = new HashSet<>();

    public ThreeStooges () {
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Cucly");
    }

    public boolean isStooge(String name) {
        return stooges.contains(name);
    }
}
