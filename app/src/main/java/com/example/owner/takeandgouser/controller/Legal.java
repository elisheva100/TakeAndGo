package com.example.owner.takeandgouser.controller;

/**
 * Created by Owner on 14/11/2017.
 */

public class Legal {
    /**
     * This function gets a string and checks if it can be converted into number.
     * @param value
     * @return
     */
    public static boolean tryParseDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * This function gets an ID string from the user and checks if it's valid id in Israel.
     * @param s
     * @return if the id is legal
     */
    public static boolean legalId(String s)
    {
        if (s == null)
        {
            return false;
        }
        if (!(tryParseDouble(s)))//If it can't be converted into number.
            return false;
        if (s.length() < 5 || s.length() > 9)
            return false;
        for (int i = s.length(); i < 9; i++)
            s = "0" + s;
        int sum = 0;
        for (int i = 0; i < 9; i++)
        {
            int k = ((i % 2) + 1) * (int)(s.charAt(i) - '0');
            if (k > 9)
                k -= 9;
            sum += k;
        }
        return sum % 10 == 0;
    }

    /**
     * This function gets a string from the user and checks if it contains only letters.
     * @param st
     * @return
     */
    public static boolean isString(String st)
    {
        if (st != null)
        {
            for (char ch : st.toCharArray()){
                if (('A' <= ch && ch <= 'z') || ch == ' ' || ch == '-')
                    return true;
            }
        }
        return false;
    }

    /**
     * This gunction gets a string from the user and checks if it's valid number.
     * @param n
     * @return
     */
    public static boolean isNum(String n)
    {
        if (!tryParseDouble(n))
        {
            return false;
        }
        return true;
    }

    /**
     * This function gets string from the user and checks if it's valid cellphone number.
     * @param pel
     * @return
     */
    public static boolean isCellPhone(String pel)
    {
        if (pel == null)
        {
            return false;
        }

        if (pel.length() != 10 || !tryParseDouble(pel))
        {
            return false;
        }

        return true;
    }

    /**
     * This function gets a string from the user and checks if it's valid telephone.
     * @param tel
     * @return
     */
    public static boolean isTelPhone(String tel)
    {
        if (tel == null)
        {
            return false;
        }

        if (tel.length() != 7 || !tryParseDouble(tel))
        {
            return false;
        }

        return true;
    }
    public static boolean isEmailAddres(String email)
    {
        if(email == null)
        {
            return false;
        }
        if(!email.contains("@")) {
            return false;
        }
        return true;
    }

}
