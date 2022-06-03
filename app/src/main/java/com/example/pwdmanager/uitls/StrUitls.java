package com.example.pwdmanager.uitls;

public class StrUitls {

    //
    public String base64encoder(String str){
        String EncodeStr = null;
        EncodeStr = java.util.Base64.getEncoder().encodeToString(str.getBytes());
//        System.out.println(EncodeStr);
        return EncodeStr;
    }

    public String base64decoder(String str){
        byte[] decodeBytes = java.util.Base64.getDecoder().decode(str);
        String DecodeStr = new String(decodeBytes);
        return DecodeStr;
    }
}
