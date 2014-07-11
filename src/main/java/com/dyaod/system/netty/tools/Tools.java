package com.dyaod.system.netty.tools;

/**
 * Created by dyaod on 14-7-11.
 */
public class Tools {
    /**
     * unicode 转中文
     * @param source
     * @return
     */
    public static String convert(String source){
        if (null==source || "".equals(source)){
            return source;
        }

        StringBuffer sb=new StringBuffer();
        int i=0;
        while(i<source.length()){
            if (source.charAt(i)=='\\'){
                int j=Integer.parseInt(source.substring(i + 2,i+6),16);
                sb.append((char)j);
                i+=6;
            }else{
                sb.append(source.charAt(i));
                i++;
            }
        }
        return sb.toString();
    }
}
