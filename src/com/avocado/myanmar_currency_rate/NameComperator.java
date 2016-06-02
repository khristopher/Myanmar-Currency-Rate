package com.avocado.myanmar_currency_rate;

import java.util.Comparator;

/**
 * Created by kyawmyintcho on 5/11/14.
 */
public class NameComperator implements Comparator<Currency> {

        @Override
        public int compare(Currency lhs, Currency rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }

}
