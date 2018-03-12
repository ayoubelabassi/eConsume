package com.elab.consume.tools;

import java.util.List;

/**
 * Created by AYOUB on 12/03/2018.
 */

public class Operator {
    public static String [] stringsConvertor(List<String> list){
        if(list!=null){
            String sList[]=new String[list.size()];
            int i=0;
            for (String s:list) {
                sList[i]=s;
                i++;
            }
            return sList;
        }else {
            return null;
        }
    }
}
