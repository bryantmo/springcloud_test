package com.bryant.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Test006 {
    public String convert(String s, int numRows) {

        List<StringBuilder> res = new ArrayList<>();

        // 初始化
        for(int i = 0; i<numRows; i++) {
            res.add(new StringBuilder());
        }

        //填充
        int i = 0;
        for(char c : s.toCharArray()) {
            res.get(i++).append(c);
            if(i == 0) {
                i++;
            }

            if(i == numRows) {
                i--;
            }

        }

        // 拼接
        StringBuilder r = new StringBuilder();
        for(int j = 0; j < numRows; j++) {
            r.append(res.get(j).toString());
        }

        return r.toString();
    }
}