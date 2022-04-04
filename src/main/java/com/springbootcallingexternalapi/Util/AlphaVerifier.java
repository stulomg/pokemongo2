package com.springbootcallingexternalapi.Util;

import java.util.regex.Pattern;

public class AlphaVerifier {

    private static Pattern p = Pattern.compile("^[ a-zA-Z]*$");

    public static boolean isAlpha(String s) {
        return p.matcher(s).find();
    }

    public static boolean isAlpha (String owner, String account){return p.matcher(owner).find();}
}
