package com.ruowei.ecsp.util;

import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {

    private static final char SEPARATOR = '_';

    /**
     * 驼峰命名法工具
     *
     * @return camelCase(" hello_world ") == "helloWorld"
     * capCamelCase("hello_world") == "HelloWorld"
     * uncamelCase("helloWorld") = "hello_world"
     */
    public static String camelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = i != 1; // 不允许第二个字符是大写
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static Boolean symbolArrayStrBlank(String s) {
        return StringUtils.isBlank(s) || "[]".equals(s);
    }

    /**
     * @param s
     * @return
     * @apiNote author: czz; 判断水务年报中 数据是否为空/-  计算时使用
     */
    public static Boolean cellEmpty(String s) {
        return StringUtils.isBlank(s) || "-".equals(s);
    }

    /**
     * @param s
     * @return
     * @apiNote author: czz; not blank[null or empty], not "-"
     */
    public static Boolean cellNotEmpty(String s) {
        return !cellEmpty(s);
    }

    /**
     * @apiNote author: zk 随机生成8位字母数字混合密码
     */
    private static final String lowStr = "abcdefghijklmnopqrstuvwxyz";
    private static final String numStr = "0123456789";

    // 随机获取字符串字符
    private static char getRandomChar(String str) {
        SecureRandom random = new SecureRandom();
        return str.charAt(random.nextInt(str.length()));
    }

    // 随机获取小写字符
    private static char getLowChar() {
        return getRandomChar(lowStr);
    }

    // 随机获取大写字符
    private static char getUpperChar() {
        return Character.toUpperCase(getLowChar());
    }

    // 随机获取数字字符
    private static char getNumChar() {
        return getRandomChar(numStr);
    }

    //指定调用字符函数
    private static char getRandomChar(int funNum) {
        switch (funNum) {
            case 0:
                return getLowChar();
            case 1:
                return getUpperChar();
            case 2:
                return getNumChar();
            default:
                return getNumChar();
        }
    }

    // 指定长度，随机生成复杂密码
    public static String getRandomPwd() {
        List<Character> list = new ArrayList<>(8);
        list.add(getLowChar());
        list.add(getUpperChar());
        list.add(getNumChar());

        for (int i = 3; i < 8; i++) {
            SecureRandom random = new SecureRandom();
            int funNum = random.nextInt(4);
            list.add(getRandomChar(funNum));
        }

        Collections.shuffle(list); // 打乱排序
        StringBuilder stringBuilder = new StringBuilder(list.size());
        for (Character c : list) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String getDateStr(String str) {
        /**
         * "2022-03-30 17:35:56"
         */
        return str.substring(0, 10).replaceAll("-", "");
    }

    public static List<Long> getLongList(String str) {
        if (StringUtils.isBlank(str)) {
            return new ArrayList<>();
        }
        List<Long> list = Arrays.stream(str.split(",")).map(Long::valueOf).collect(Collectors.toList());
        return list;
    }

    public static List<String> split(String str, String separator) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(str)) {
            return Arrays.asList(str.split(separator));
        }
        return list;
    }

    public static List<String> splitAndFilterBlank(String str, String separator) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(str)) {
            String[] strs = str.split(separator);
            for (String s : strs) {
                if (StringUtils.isNotBlank(s)) {
                    list.add(s);
                }
            }
        }
        return list;
    }

    public static String getFinallyTradeType(@NotNull @NotEmpty String tradeType) {
        /**
         * CCER-15, CCER-17,CCER-16,SHEA,CCER,GDEA,TJEA21,TJEA13,TJEA15,TJEA14,TJEA19,TJEA20,TJEA17,
         * TJEA16,CEA,HBEA,BEA,CCER,PCER,SZA-2013,SZA-2014,SZA-2015,SZA-2016,SZA-2017,SZA-2020,SZA-2019,SZA-2018,
         */
        return removeNum(tradeType).replaceAll("-", "");
    }

    public static List<String> getTradeTotalPriceAndType(String str) {
        /**
         * origin: "182500.00ï¼BEAï¼"
         * -*-*-* 乱码数据，只想获取数字【价格】，和英文字母【碳交易类型】 -*-*-*
         * result: ["182500.00", "BEA"]
         */
        String notNumStr = removeNum(str);
        int notNumStartIndex = str.length() - notNumStr.length();
        String priceStr = str.substring(0, notNumStartIndex);
        String typeStr = leaveCharacter(notNumStr);
        return Arrays.asList(priceStr, typeStr);
    }

    public static String removeNum(String str) {
        /**
         * origin: "182500.00ï¼BEAï¼"
         * -*-*-* 上面转为去除数字和小数点 -*-*-*-  只有一位小数点或其他部位没有小数点 -*-*-*-
         * result: "ï¼BEAï¼"
         */
        return str.replaceAll("[0-9]", "").replaceAll("[.]", "");
    }

    public static String leaveCharacter(String str) {
        /**
         * origin: "ï¼BEAï¼"
         * -*-*-* 上面转为只保留字母 -*-*-*-
         * result: "BEA"
         */
        return str.replaceAll("[^a-zA-Z]", "");
    }

    public static String join(String separator, String... strs) {
        return StringUtils.join(Arrays.asList(strs), separator);
    }

    public static String join(List<String> list) {
        return StringUtils.join(list, ",");
    }

    public static String join(List<String> list, String separator, String defaultStr) {
        return StringUtils.join(list, separator);
    }

    /**
     * @param str
     * @return
     * @apiNote author: czz; 判断字符是否包含汉字
     */
    public static boolean generateJudgment(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher isNum = p.matcher(str);
        if (isNum.find()) {
            return true;
        }
        return false;
    }

    public static boolean carbonTradeTypeUseless(String type) {
        return !carbonTradeTypeUseful(type);
    }

    public static boolean carbonTradeTypeUseful(String type) {
        return type != null && !type.equals("") && !type.equals("-");
    }

    public static String getDefaultTradeValue(String str) {
        if (StringUtils.isBlank(str)) {
            return "-";
        }
        return str.replaceAll(",", "");
    }

    public static boolean isNotBlank(String name) {
        return !StringUtils.isBlank(name);
    }
}
