package cn.cofco.cpmp.utils;

import java.util.ArrayList;
import java.util.List;

public class CharacterProcessTool {

	// 通用分割字符串
    public static String[] split(String source, char ch) {
        List<String> values = new ArrayList<String>();
        StringBuffer temp = new StringBuffer();
        int count = 0;
        for (int i = 0; i < source.length(); i++) {
        	char currentChar = source.charAt(i);
        	if (currentChar == ch) {
        		if (count == 0) {
        			if (i == 0) {
                        continue;
                    }
        			values.add(temp.toString());
        			temp = new StringBuffer();
        		} else {
        			temp.append(currentChar);
        		}
            } else if (currentChar == '"') {
            	if (count == 0) {
            		count++;
            	} else {
            		count--;
            	}
            	temp.append(currentChar);
            } else {
            	temp.append(currentChar);
            }
        }
        // if the last string is not blank, trim it and add this value to result
        if (temp.toString().trim().length() != 0) {
        	values.add(temp.toString().trim());
        }
       
        String[] result = new String[values.size()];
       
        for (int i = 0; i < values.size(); i++) {
        	result[i] = values.get(i);
        }
        return result;
    }
    
}
