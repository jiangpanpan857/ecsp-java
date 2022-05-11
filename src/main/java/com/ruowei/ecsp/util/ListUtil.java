package com.ruowei.ecsp.util;



import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListUtil {

    /**
     * @param oldList
     * @param newList
     * @param <T>
     * @return
     * @apiNote author: czz; 按序集成
     */
    public static <T> List<T> mergeSecond(List<T> oldList, List<T> newList) {
        List<T> list = new ArrayList<>(oldList);
        list.addAll(newList);
        return list;
    }

    /**
     * @param oldList
     * @param newArr
     * @param <T>
     * @return
     * @apiNote author: czz; 按序集成
     */
    public static <T> List<T> mergeSecond(List<T> oldList, T... newArr) {
        List<T> list = new ArrayList<>(oldList);
        list.addAll(StreamUtil.filterCollect(Objects::nonNull, newArr));
        return list;
    }

    /**
     * @param newList
     * @param oldArr
     * @param <T>
     * @return
     * @apiNote author: czz; 按序集成
     */
    public static <T> List<T> mergeFirst(List<T> newList, T... oldArr) {
        List<T> list = new ArrayList<>(StreamUtil.filterCollect(Objects::nonNull, oldArr));
        try {
            list.addAll(newList);
        } catch (Exception e) {
            AssertUtil.logError(e, " cellCollectFail");
        }
        return list;
    }

    /**
     * @param begin
     * @param newList
     * @param endArr
     * @param <T>
     * @return
     * @apiNote author: czz; 按序集成各部分
     */
    public static <T> List<T> mergeSecondThird(T begin, List<T> newList, T... endArr) {
        List<T> list = new ArrayList<>();
        list.add(begin);
        list.addAll(newList);
        list.addAll(StreamUtil.filterCollect(Objects::nonNull, endArr));
        return list;
    }

    /**
     * @return 获取format为"MM"的月份str集合
     * @apiNote author: czz;
     */
    public static List<String> getMonthsBy_MM() {
        List<String> strings = new ArrayList<>();
        String preStr = "0";
        for (int i = 1; i < 13; i++) {
            String monthStr = i >= 10 ? "" + i : preStr + i;
            strings.add(monthStr);
        }
        return strings;
    }

    /**
     * @return "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"
     * @apiNote author: czz;
     */
    public static List<String> getMonthsWith_yue() {
        return Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");
    }





    public static <T> Boolean isNotEmpty(List<T> list) {
        return !CollectionUtils.isEmpty(list);
    }

    public static <T> Boolean isEmpty(List<T> list) {
        return CollectionUtils.isEmpty(list);
    }

}
