package com.glodio.util;

public class HexUtilty {
    /** 
     * 16进制字符串转换为字符串 
     *  
     * @param s 
     * @return 
     */  
    public static String hexStringToString(String s) {  
        if (s == null || s.equals("")) {  
            return null;  
        }  
        s = s.replace(" ", "");  
        byte[] byteArray = new byte[s.length() / 2];  
        for (int i = 0; i < byteArray.length; i++) {  
            try {  
/*            	if(i== 0) {
            		System.out.println(Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            		System.out.println((byte)(0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16)));
            	}*/
            	byteArray[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        try {  
            s = new String(byteArray, "utf-8");  
            new String();  
        } catch (Exception e1) {  
            e1.printStackTrace();  
        }  
        return s;  
    } 
    
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
    
    
    public static String BytestoHexString(byte[] b){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i){
            buffer.append(byteToHexString(b[i]));
        }
        return buffer.toString();
    }
    
    
    public static String byteToHexString(byte b){
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1){
            return "0" + s;
        }else{
            return s;
        }
    }
    
    public static byte[] hexString2Bytes(String s) {  
        if (s == null || s.equals("")) {  
            return null;  
        }  
        s = s.replace(" ", "");  
        byte[] byteArray = new byte[s.length() / 2];  
        for (int i = 0; i < byteArray.length; i++) {  
            try {  
            	byteArray[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }   
        return byteArray;  
    } 
    
}
