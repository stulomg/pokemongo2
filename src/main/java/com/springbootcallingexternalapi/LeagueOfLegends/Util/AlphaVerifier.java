package com.springbootcallingexternalapi.LeagueOfLegends.Util;

import java.util.regex.Pattern;

public class AlphaVerifier {

    private static Pattern p = Pattern.compile("^[ a-zA-Z0-9]*$");

    public static boolean isAlpha(String s) {
        return p.matcher(s).find();
    }
}
