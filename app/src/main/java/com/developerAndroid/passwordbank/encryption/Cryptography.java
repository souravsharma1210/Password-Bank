package com.developerAndroid.passwordbank.encryption;

/**
 * Created by sourav sharma on 28-02-2018.
 */

public class Cryptography {
    private static final int KEY=3;
    public static String encrypt(String s)
    {
        String encrytedString="";
        for(int i=0;i<s.length();i++)
        {
            int c=s.charAt(i);
            encrytedString+=Character.toString((char)(c+KEY));
        }
        return encrytedString;
    }
    public static String decrypt(String s)
    {
        String encrytedString="";
        for(int i=0;i<s.length();i++)
        {
            int c=s.charAt(i);
            encrytedString+=Character.toString((char)(c-KEY));
        }
        return encrytedString;
    }
}

