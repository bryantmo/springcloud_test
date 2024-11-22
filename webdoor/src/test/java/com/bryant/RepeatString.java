package com.bryant;


import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class RepeatString {

    public static void main(String[] args) {

//        String str = "abcabcbb";
//        String str = "bbbbb";
        String str = "pwwkew";

        int res = searchRepeat(str);

        log.info("res = {}", res);
    }

    private static int searchRepeat(String str) {
        int maxLen = 0;
        for(int i = 0; i < str.length(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(str.charAt(i));

            Set set = new HashSet<Character>();
            set.add(str.charAt(i));

            for (int j = i + 1; j < str.length(); j++) {
                if (!set.contains(str.charAt(j))) {
                    set.add(str.charAt(j));
                    sb.append(str.charAt(j));
                } else {
                    maxLen = Math.max(sb.toString().length(), maxLen);
                    break;
                }
            }
        }
        return maxLen;
    }

}
