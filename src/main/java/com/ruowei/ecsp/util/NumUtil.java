package com.ruowei.ecsp.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ruowei.ecsp.config.Constants.ZERO;

public class NumUtil {

    /*
    used in test to show the value.
     */
    public static void show(Object num) {
        System.out.println("    " + num + "    ");
    }

    public static void showNum(Number num, String title) {
        System.out.println(title + ": " + num);
        System.out.println();
    }

    /*
    transfer , get default
     */

    public static List<BigDecimal> toBigDecimal(Double... values) {
        List<Double> doubles = Arrays.asList(values);
        return doubles.stream().map(BigDecimal::valueOf).collect(Collectors.toList());
    }

    public static BigDecimal getTradeTotalPrice(String priceStr, String numStr) {
        try {
            BigDecimal price = new BigDecimal(priceStr);
            BigDecimal num = new BigDecimal(numStr);
            return price.multiply(num);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param num
     * @return
     * @apiNote author: czz; 水务年报导出用,数字设为指定小数位数后转为string
     */
    public static String toString_scale1(Number num) {
        if (num == null) {
            return "-";
        }
        BigDecimal num_ = setScale1RoundHalfUp(num);
        return num_.toString();
    }

    public static String toString_scale0(String numStr) {
        return numStr.equals("-") ? numStr : setRoundHalfUp(new BigDecimal(numStr)).toString();
    }

    /**
     * @param numStr
     * @return
     * @apiNote author: czz; 水务年报excel用
     */
    public static String toNegativeString(String numStr) {
        return "-".equals(numStr) ? "-" : "-" + numStr;
    }

    public static String toNegativeString_scale1(Number num) {
        if (num == null) {
            return "-";
        }
        String numStr = toString_scale1(num);
        return toNegativeString(numStr);
    }

    /**
     * @param num
     * @return
     * @apiNote author: czz; 计算碳_量 时, 得到null说明此类碳库未选或此碳层无此类 为了统一计算将null转为0
     */
    public static BigDecimal getDefaultValue(BigDecimal num) {
        return num == null ? BigDecimal.ZERO : num;
    }

    /**
     * @param num
     * @return
     * @apiNote author: czz; 碳层灌木起算0.05限制.
     */
    public static BigDecimal getDefaultShrubCC(BigDecimal num) {
        return num.compareTo(BigDecimal.valueOf(0.05)) < 0 ? BigDecimal.ZERO : num;
    }

    /*
    check if in period.
    or
    compare
     */

    /**
     * @param num1
     * @param start
     * @param end
     * @return
     * @apiNote author: czz;  num ∈ [start, end]
     */
    public static boolean isInOrEqualEither(@NotNull Number num1, Number start, Number end) {
        // num ∈ (start, end]
        List<BigDecimal> values = getCompareValues(num1, start, end);
        return values.get(0).compareTo(values.get(1)) >= 0 && values.get(0).compareTo(values.get(2)) <= 0;
    }

    /**
     * @param num1
     * @param start
     * @param end
     * @return
     * @apiNote author: czz; num ∈ (start, end]
     */
    public static boolean isInOrEqualEnd(@NotNull Number num1, Number start, Number end) {
        // num ∈ (start, end]
        List<BigDecimal> values = getCompareValues(num1, start, end);
        return values.get(0).compareTo(values.get(1)) > 0 && values.get(0).compareTo(values.get(2)) <= 0;
    }

    /**
     * @param num
     * @param start
     * @param end
     * @return
     * @apiNote author: czz; get bigDecimalList from numList
     */
    private static List<BigDecimal> getCompareValues(@NotNull Number num, Number start, Number end) {
        BigDecimal num_ = new BigDecimal(num.toString());
        BigDecimal start_ = start == null ? ZERO : new BigDecimal(start.toString());
        BigDecimal end_ = end != null && new BigDecimal(end.toString()).compareTo(ZERO) > 0
            ? new BigDecimal(end.toString())
            : new BigDecimal("10000");
        return Arrays.asList(num_, start_, end_);
    }

    /**
     * @param first  数字1
     * @param second 数字2
     * @return 数字1是否比数字2大
     * @apiNote author: czz;
     */
    public static Boolean firstBigger(Number first, Number second) {
        return compareTo(first, second) > 0;
    }

    /**
     * @param first  数字1
     * @param second 数字2
     * @return 数字1是否大于等于数字2
     * @apiNote author: czz;
     */
    public static Boolean firstBiggerOrEqual(Number first, Number second) {
        return compareTo(first, second) >= 0;
    }

    /**
     * @param first  数字1
     * @param second 数字2
     * @return 数字1是否比2小
     * @apiNote author: czz;
     */
    public static Boolean firstSmaller(Number first, Number second) {
        return compareTo(first, second) < 0;
    }

    /**
     * @param first  数字1
     * @param second 数字2
     * @return 数字1是否小于等于数字2
     * @apiNote author: czz;
     */
    public static Boolean firstSmallerOrEqual(Number first, Number second) {
        return compareTo(first, second) <= 0;
    }

    /**
     * @param first  数字1
     * @param second 数字2
     * @return 数字1与2是否相等
     * @apiNote author: czz;
     */
    public static Boolean isEqual(Number first, Number second) {
        return compareTo(first, second) == 0;
    }

    /**
     * @param first  数字1
     * @param second 数字2
     * @return 数字1比较2的结果[-1: 小于, 0: 等于, 1: 大于 ]
     */
    public static int compareTo(Number first, Number second) {
        return new BigDecimal(first.toString()).compareTo(new BigDecimal(second.toString()));
    }

    /*
    sum
     */

    /**
     * @param numList BigDecimal_List
     * @return sumValue
     * @apiNote author: czz; sum BigDecimal_List.
     */
    public static BigDecimal sumList(List<BigDecimal> numList) {
        return numList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * @param numList
     * @return
     * @apiNote author: czz; 判断列表中是否全是null, 如果是则返null, 否则排除null进行计算
     */
    public static BigDecimal sumNotAllNullList(List<BigDecimal> numList) {
        if (StreamUtil.checkAllNull(numList)) {
            return null;
        }
        return numList.stream().filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * @param numArr
     * @return
     * @apiNote author: czz; 判断列表中是否全是null, 如果是则返null, 否则排除null进行计算
     */
    public static BigDecimal sumNotAllNullList(BigDecimal... numArr) {
        if (StreamUtil.checkAllNull(numArr)) {
            return null;
        }
        return Arrays.stream(numArr).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * @param numArr
     * @return
     * @apiNote author: czz; 判断列表中是否全是null, 如果是则返"-", 否则排除null进行计算后结果转为String
     */
    public static String sumNotAllNullList_toString(BigDecimal... numArr) {
        if (StreamUtil.checkAllNull(numArr)) {
            return "-";
        }
        BigDecimal sumResult = Arrays.stream(numArr).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        return toString_scale1(sumResult);
    }

    public static String sumNotAllNullList_toNegativeString_scale1(BigDecimal... numArr) {
        if (StreamUtil.checkAllNull(numArr)) {
            return "-";
        }
        BigDecimal sumResult = Arrays.stream(numArr).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        return toNegativeString_scale1(sumResult);
    }

    /**
     * @param numStrList
     * @return
     * @apiNote author: czz; 判断列表中是否全是null, 如果是则返"-", 否则排除null进行计算后结果转为String
     */
    public static String sumNotAllNullList_toString(List<String> numStrList) {
        if (StreamUtil.checkAllNull(numStrList)) {
            return "-";
        }
        Stream<BigDecimal> numStream = numStrList
            .stream()
            .map(numStr -> {
                if (StringUtil.cellEmpty(numStr)) {
                    return null;
                }
                return new BigDecimal(numStr);
            })
            .filter(Objects::nonNull);
        BigDecimal sumResult = numStream.reduce(BigDecimal.ZERO, BigDecimal::add);
        return toString_scale1(sumResult);
    }

    /**
     * @param numStrArr
     * @return
     * @apiNote author: czz;
     */
    public static String sumNotAllCellEmpty(String... numStrArr) {
        return sumNotAllCellEmpty(Arrays.asList(numStrArr));
    }

    public static String sumNotAllCellEmptyScale0(String... numStrArr) {
        String str = sumNotAllCellEmpty(Arrays.asList(numStrArr));
        return toString_scale0(str);
    }

    public static void setLastScale0(List<String> strings) {
        int len = strings.size();
        strings.set(len - 1, toString_scale0(strings.get(len - 1)));
    }

    public static String sumNotAllCellEmpty(List<String> cellStrList) {
        cellStrList = cellStrList.stream().filter(StringUtil::cellNotEmpty).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cellStrList)) {
            return "-";
        }
        BigDecimal sumResult = cellStrList.stream().map(BigDecimal::new).reduce(BigDecimal.ZERO, BigDecimal::add);
        return toString_scale1(sumResult);
    }

    /**
     * @param numStrArr
     * @return
     * @apiNote author: czz; 判断列表中是否全是null, 如果是则返"-", 否则排除null进行计算后结果转为String
     */
    public static String sumNotAllNullList_toString(String... numStrArr) {
        return sumNotAllNullList_toString(Arrays.asList(numStrArr));
    }

    public static BigDecimal sumListUp(List<BigDecimal> numList) {
        return numList.stream().reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.UP);
    }

    /**
     * @param numList
     * @return
     * @apiNote author: czz; sum BigDecimal[]
     */
    public static BigDecimal sumList(BigDecimal... numList) {
        return Arrays.stream(numList).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * @param defaultFlag
     * @param numList
     * @return
     * @apiNote author: czz; sum nonNull values.
     */
    public static BigDecimal sumList(boolean defaultFlag, BigDecimal... numList) {
        if (!defaultFlag) {
            return sumList(numList);
        }
        return Arrays.stream(numList).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /*
     * sub
     */

    public static BigDecimal oneSubAll(BigDecimal first, BigDecimal... second) {
        return first.subtract(sumList(second));
    }

    /**
     * @param numList
     * @return
     * @apiNote author: czz; multiply each in BigDecimal[]
     */
    public static BigDecimal multiplyList(BigDecimal... numList) {
        return Arrays.stream(numList).reduce(BigDecimal.ONE, BigDecimal::multiply);
    }

    /**
     * @param num
     * @return
     * @apiNote author: czz; 向上保留整数
     */
    public static int getIntUp(BigDecimal num) {
        return setScaleRound(num, 0, RoundingMode.UP).intValue();
    }

    public static BigDecimal setRoundHalfUp(BigDecimal num) {
        return setScaleZeroRound(num, RoundingMode.HALF_UP);
    }

    public static BigDecimal setRoundDown(BigDecimal num) {
        return setScaleZeroRound(num, RoundingMode.DOWN);
    }

    public static BigDecimal setRoundUp(BigDecimal num) {
        return setScaleZeroRound(num, RoundingMode.UP);
    }

    public static BigDecimal setScaleZeroRound(BigDecimal num, RoundingMode roundingMode) {
        return setScaleRound(num, 0, roundingMode);
    }

    public static BigDecimal setScale1RoundHalfUp(Number num) {
        BigDecimal num_ = new BigDecimal(num.toString());
        return setScaleRound(num_, 1, RoundingMode.HALF_UP);
    }

    public static BigDecimal setScaleRound(BigDecimal num, int scale, RoundingMode roundingMode) {
        num = num == null ? BigDecimal.ZERO : num;
        return num.setScale(scale, roundingMode);
    }

    public static BigDecimal nullAchievedSetScaleRound(BigDecimal num, int scale, RoundingMode roundingMode) {
        return num == null ? null : num.setScale(scale, roundingMode);
    }

    /**
     * @param first
     * @param second
     * @return
     * @apiNote author: czz; div then set scale: 0, and round-up
     */
    public static BigDecimal divUp(BigDecimal first, BigDecimal second) {
        return first.divide(second, 0, RoundingMode.UP);
    }

    public static BigDecimal divHalfUp(BigDecimal first, BigDecimal second) {
        return first.divide(second, RoundingMode.HALF_UP);
    }

    public static BigDecimal divForCarbonTradeAg(BigDecimal first, BigDecimal second) {
        return div(first, second, 2, RoundingMode.HALF_UP);
    }

    /**
     * @param first
     * @param second
     * @return
     * @apiNote author: czz; first/second half_up scale:15
     */
    public static BigDecimal div(BigDecimal first, BigDecimal second) {
        return first.divide(second, 15, RoundingMode.HALF_UP);
    }

    /**
     * @param first
     * @param second
     * @return
     * @apiNote author: czz; one / multiplyList(second[])
     */
    public static BigDecimal oneDivAll(BigDecimal first, BigDecimal... second) {
        BigDecimal second_ = multiplyList(second);
        return first.divide(second_, 15, RoundingMode.HALF_UP);
    }

    /**
     * @param first
     * @param second
     * @return
     * @apiNote author: czz; first/second  half_up scale:15
     */
    public static BigDecimal div(BigDecimal first, int second) {
        return first.divide(BigDecimal.valueOf(second), 15, RoundingMode.HALF_UP);
    }

    public static BigDecimal divDown(BigDecimal first, int second) {
        return first.divide(BigDecimal.valueOf(second), 0, RoundingMode.DOWN);
    }

    public static BigDecimal div(BigDecimal first, BigDecimal second, RoundingMode roundingMode) {
        return first.divide(second, roundingMode);
    }

    public static BigDecimal div(BigDecimal first, BigDecimal second, int scale, RoundingMode roundingMode) {
        return first.divide(second, scale, roundingMode);
    }

    public static Integer getAverage(int total, int period) {
        return BigDecimal.valueOf(total).divide(BigDecimal.valueOf(period), 15, RoundingMode.HALF_UP).intValue();
    }

    /**
     * @param total
     * @param period
     * @return
     * @apiNote author: czz; get total/period 保留0位小数
     */
    public static BigDecimal getAverage(BigDecimal total, BigDecimal period) {
        return total.divide(period, 0, RoundingMode.DOWN);
    }

    /**
     * @param old
     * @param current
     * @param period
     * @return
     * @apiNote author: czz; 向上取整
     */
    public static BigDecimal getGapAverage(BigDecimal old, BigDecimal current, int period) {
        BigDecimal current_ = current == null ? BigDecimal.ZERO : current;
        BigDecimal old_ = old == null ? BigDecimal.ZERO : old;
        return (current_.subtract(old_)).divide(BigDecimal.valueOf(period), 0, RoundingMode.HALF_UP);
    }

    /**
     * @param current
     * @param period
     * @return
     * @apiNote author: czz; 向上取整
     */
    public static BigDecimal getGapAverage(BigDecimal current, int period) {
        BigDecimal current_ = current == null ? BigDecimal.ZERO : current;
        return (current_).divide(BigDecimal.valueOf(period), 0, RoundingMode.HALF_UP);
    }

    /**
     * @param old
     * @param current
     * @param alterNum
     * @param period
     * @return
     * @apiNote author: czz; 向上取整
     */
    public static BigDecimal getGapAverage_alter(BigDecimal old, BigDecimal current, BigDecimal alterNum, int period) {
        BigDecimal current_ = current == null ? BigDecimal.ZERO : current;
        BigDecimal old_ = old == null ? BigDecimal.ZERO : old;
        BigDecimal tempAverage = (current_.subtract(old_)).divide(BigDecimal.valueOf(period), 15, RoundingMode.HALF_UP);
        BigDecimal result = tempAverage.compareTo(BigDecimal.ZERO) > 0
            ? tempAverage.multiply(BigDecimal.ONE.subtract(alterNum))
            : tempAverage.multiply(BigDecimal.ONE.add(alterNum));
        return setRoundHalfUp(result);
    }

    /**
     * @param current
     * @param alterNum
     * @param period
     * @return
     * @apiNote author: czz; 向上取整
     */
    public static BigDecimal getGapAverage_alter(BigDecimal current, BigDecimal alterNum, int period) {
        BigDecimal current_ = current == null ? BigDecimal.ZERO : current;
        BigDecimal tempAverage = (current_).divide(BigDecimal.valueOf(period), 15, RoundingMode.HALF_UP);
        BigDecimal result = tempAverage.multiply(BigDecimal.ONE.subtract(alterNum));
        return setRoundHalfUp(result);
    }

    public static BigDecimal getAverage(List<BigDecimal> total) {
        return sumList(total).divide(BigDecimal.valueOf(total.size()), 15, RoundingMode.HALF_UP);
    }

    /**
     * @param total
     * @param num
     * @return
     * @apiNote author: czz; get sum(List)/num  half_up scale:15
     */
    public static BigDecimal getAverage(List<BigDecimal> total, BigDecimal num) {
        return sumList(total).divide(num, 15, RoundingMode.HALF_UP);
    }

    /**
     * @param total
     * @param num
     * @return
     * @apiNote author: czz; get sum(List)/num  half_up scale:15
     */
    public static BigDecimal getAverage(List<BigDecimal> total, int num) {
        return sumList(total).divide(BigDecimal.valueOf(num), 15, RoundingMode.HALF_UP);
    }

    /**
     * @param num
     * @return
     * @apiNote author: czz; 获取开平方
     */
    public static BigDecimal getSqrt(BigDecimal num) {
        return BigDecimal.valueOf(Math.sqrt(num.doubleValue()));
    }

    public static List<BigDecimal> getTrueAverageN(
        List<BigDecimal> layerAreaList,
        List<BigDecimal> specificNList,
        List<BigDecimal> specificAreaList
    ) {
        int size = specificNList.size();
        List<BigDecimal> trueNList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            BigDecimal layerArea = layerAreaList.get(i);
            BigDecimal specificN = specificNList.get(i);
            BigDecimal specificArea = specificAreaList.get(i);
            BigDecimal trueN = specificN.multiply(specificArea).divide(layerArea, 0, RoundingMode.HALF_UP);
            trueNList.add(trueN);
        }
        return trueNList;
    }

    public static List<BigDecimal> getBigDecimalList(Double... values) {
        return Arrays.stream(values).map(BigDecimal::valueOf).collect(Collectors.toList());
    }

    /**
     * @param values
     * @return
     * @apiNote author: czz; trans from List<String> to BigDecimal-List
     */
    public static List<BigDecimal> getBigDecimalList(List<String> values) {
        return values.stream().map(BigDecimal::new).collect(Collectors.toList());
    }

    /**
     * @param values
     * @return
     * @apiNote author: czz; trans from BigDecimal[] to BigDecimal-List
     */
    public static List<BigDecimal> getBigDecimalList(BigDecimal... values) {
        return Arrays.asList(values);
    }


    /**
     * @param datas
     * @return
     * @apiNote author: 于青冉; Return true if all data is '-'
     */
    public static boolean judgeAllLine(List<String> datas) {
        // 过滤掉“-”值
        if (
            datas
                .stream()
                .filter(data -> {
                    return data == "-";
                })
                .collect(Collectors.toList())
                .size() ==
                datas.size()
        ) {
            return true;
        } else {
            return false;
        }
    }
}
