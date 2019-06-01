package com.example.root.jiocollect;

public class RemoveNoise {
    // function to remove special characters from selected values
    public String cleanValue(String value) {
        value = value.toLowerCase();
        value = value.replaceAll("[-+.*^:;,_/]","");
        value = value.replaceAll(" ", "");
        return value;
    }
}
