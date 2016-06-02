package com.avocado.myanmar_currency_rate;

/**
 * Created by kyawmyintcho on 5/11/14.
 */
public class CurrencyNameTable {
    public static java.util.HashMap<String,String> nameMap = new java.util.HashMap<String,String>();

    public CurrencyNameTable()
    {
        init();
    }

    public String get(String name){
        return nameMap.get(name);
    }
    public static void init(){
        nameMap.put("USD" , "U.S Dollar");
        nameMap.put("EUR","Euro");
        nameMap.put("SGD","Singapore Dollar");
        nameMap.put("GBP","Pound Sterling");
        nameMap.put("CHF","Swiss Franc");
        nameMap.put("JPY","Japanese Yen");
        nameMap.put("AUD","Australian Dollar");
        nameMap.put("BDT","Bangladesh Taka");
        nameMap.put("BND","Brunei Dollar");
        nameMap.put("KHR","Cambodian Riel");
        nameMap.put("CAD","Canadian Dollar");
        nameMap.put("CNY","Chinese Yuan");
        nameMap.put("HKD","Hong Kong Dollar");
        nameMap.put("INR","Indian Rupee");
        nameMap.put("IDR","Indonesian Rupiah");
        nameMap.put("KRW","Korean Won");
        nameMap.put("LAK","Lao Kip");
        nameMap.put("MYR","Malaysian Ringgit");
        nameMap.put("NZD","New Zealand Dollar");
        nameMap.put("PKR","Pakistani Rupee");
        nameMap.put("PHP","Philippines Peso");
        nameMap.put("LKR","Sri Lankan Rupee");
        nameMap.put("THB","Thai Baht");
        nameMap.put("VND","Vietnamese Dong");
        nameMap.put("BRL","Brazilian Real");
        nameMap.put("CZK","Czech Koruna");
        nameMap.put("DKK","Danish Krone");
        nameMap.put("EGP","Egyptian Pound");
        nameMap.put("ILS","Israeli Shekel");
        nameMap.put("KES","Kenya Shilling");
        nameMap.put("KWD","Kuwaiti Dinar");
        nameMap.put("NPR","Nepalese Rupee");
        nameMap.put("NOK","Norwegian Kroner");
        nameMap.put("RUB","Russian Rouble");
        nameMap.put("SAR","Saudi Arabian Riyal");
        nameMap.put("RSD","Serbian Dinar");
        nameMap.put("ZAR","South Africa Rand");
        nameMap.put("SEK","Swedish Krona");
    }
}
