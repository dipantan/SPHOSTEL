package com.sphostel.utils;

import java.util.regex.Pattern;

/**
 * Created by Apollo on 6/9/2020.
 */

public class PatternField {
    public static final Pattern ROLL_NO
            = Pattern.compile(
            "[A-Z]{1,3}" +     //must contains characters A-Z
                    "/" +     //then a /
                    "[0-9]{1,3}" +    //then digits
                    "/" +     //then a /
                    "[0-9]{1,3}"    //then digits
    );
    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +           //  pattern starts
                    "(?=.*[0-9])" +         //  at least 1 digit
                    "(?=.*[a-z])" +         //  at least 1 lower case Letter
                    "(?=.*[A-Z])" +         //  at least 1 upper case Letter
                    "(?=.*[@#$%^&*+=])" +   //  at least 1 special character
                    "(?=\\S+$)" +           //  no white spaces
                    ".{6,}" +               //  at least 6 characters
                    "$");                   //  pattern ends
}
