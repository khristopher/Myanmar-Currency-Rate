package com.avocado.myanmar_currency_rate;

/**
 * Created by kyawmyintcho on 5/11/14.
 */
public class Currency {
    private String name;
    private String shortName;
    private String values;

    public Currency(String fullName, String s, String v)
    {
        name = fullName;
        shortName = s;
        values = v;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String toString()
    {
        return "1 " + getName() + " = " + getValues() + " kyats";
    }



}
