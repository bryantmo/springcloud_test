package com.bryant;

public class TestCase067 {

    public static void main(String[] args) {
        String a = "11";
        String b = "1";
        System.out.println(addBinary(a, b));
    }

    public static String addBinary(String a, String b) {

        int ap  = a.length()-1;
        int bp  = b.length()-1;


        boolean m = false;

        StringBuilder sb = new StringBuilder();
        while (ap>= 0 || bp>= 0) {

            char a_char = (ap<0) ? '0' : a.charAt(ap);
            char b_char = (bp<0) ? '0' : b.charAt(bp);

            if (m) {
                if(a_char == '1' && b_char == '1') {
                    //1
                    sb.append("1");
                    m = true;
                }
                else if (a_char == '1' || b_char == '1') {
                    // 0
                    sb.append("0");
                    m = true;
                } else {
                    // 1
                    sb.append("1");
                    m = false;
                }
            } else {
                if(a_char == '1' && b_char == '1') {
                    // 0
                    sb.append("0");
                    m = true;
                } else if (a_char == '1' || b_char == '1') {
                    // 1
                    sb.append("1");
                    m = false;
                } else {
                    // 0
                    sb.append("0");
                    m = false;
                }

            }

            ap--;
            bp--;
        }

        if (m) {
            sb.append("1");
        }

        return sb.reverse().toString();
    }
}
