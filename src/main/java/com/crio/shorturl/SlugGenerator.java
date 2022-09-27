package com.crio.shorturl;

public class SlugGenerator {


    private static final int totalChar = 9;
    private static final String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    + "0123456789"
    + "abcdefghijklmnopqrstuvxyz";

    public String generateRandomString(){
        StringBuilder sb = new StringBuilder(totalChar);

        for(int i = 0; i < totalChar; i++){
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb + "";
    }
    
}
