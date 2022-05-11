package com.ruowei.ecsp.util;

import com.ruowei.ecsp.web.rest.errors.BadRequestProblem;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ruowei.ecsp.config.Constants.ZERO;


public class StreamUtil {

    /**
     * ---------------------------------------------------------------------------------------------------------------------
     * -            Sort
     * -
     * -
     * ---------------------------------------------------------------------------------------------------------------------
     */
    public static <T> List<T> sortedCollect(Collection<T> collection, Comparator<T> comparator) {
        return collection.stream().sorted(comparator).collect(Collectors.toList());
    }

    public static  <K,V> List<V> sortedCollectV(Collection<K> collection, Comparator<K> comparator,  Function<K, V> function) {
        return collection.stream().sorted(comparator).map(function).collect(Collectors.toList());
    }

    public static  <K,V> List<V> sortedCollectV(K[] collection, Comparator<K> comparator,  Function<K, V> function) {
        return Stream.of(collection).sorted(comparator).map(function).collect(Collectors.toList());
    }



    public static <K, V> List<V> streamMapCollect(Stream<K> kStream, Function<K, V> function) {
        return kStream.map(function).collect(Collectors.toList());
    }


    /**
     * @param kValues
     * @param functionK_VS
     * @param predicate
     * @param <K>
     * @param <V>
     * @return
     * @apiNote author: czz; List<K> -> Stream<List<V>> ->对内部V删选 -> 整合获取List<V>
     */
    public static <K, V> List<V> toListV_FilterV_CollectV(List<K> kValues, Function<K, List<V>> functionK_VS, Predicate<V> predicate) {
        return kValues
            .stream()
            .map(functionK_VS)
            .map(vs -> vs.stream().filter(predicate).collect(Collectors.toList()))
            .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }

    /**
     * @param kValues
     * @param predicateK
     * @param functionK_VS
     * @param predicateV
     * @param <K>
     * @param <V>
     * @return
     * @apiNote author: czz; KList -> filterK -> VList -> filterV -> collectV
     */
    public static <K, V> List<V> filterK_ToListV_FilterV_CollectV(
        List<K> kValues,
        Predicate<K> predicateK,
        Function<K, List<V>> functionK_VS,
        Predicate<V> predicateV
    ) {
        return kValues
            .stream()
            .filter(predicateK)
            .map(functionK_VS)
            .map(vs -> vs.stream().filter(predicateV).collect(Collectors.toList()))
            .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }



    /**
     * @param kValues
     * @param consumer
     * @param <K>
     * @apiNote author: czz; reset property of K
     */
    public static <K> void cycleAct(List<K> kValues, Consumer<K> consumer) {
        if (CollectionUtils.isEmpty(kValues)) {
            return;
        }
        kValues.forEach(consumer);
    }

    /**
     * @param kValues
     * @param predicate
     * @param consumer
     * @param <K>
     * @apiNote author: czz; 筛选重置值
     */
    public static <K> void filterResetProperty(List<K> kValues, Predicate<K> predicate, Consumer<K> consumer) {
        kValues.stream().filter(predicate).forEach(consumer);
    }

    /**
     * @param kValues
     * @param predicate
     * @param consumer
     * @param <K>
     * @return
     * @apiNote author: czz; 筛选重置值后收集
     */
    public static <K> List<K> filterResetCollect(List<K> kValues, Predicate<K> predicate, Consumer<K> consumer) {
        return kValues.stream().filter(predicate).peek(consumer).collect(Collectors.toList());

    }
    public static BigDecimal streamAdd(Stream<BigDecimal> stream) {
        return stream.filter(Objects::nonNull).reduce(ZERO, BigDecimal::add);
    }

    public static <K> BigDecimal sumProperty(List<K> kValues, Function<K, BigDecimal> function) {
        return streamAdd(kValues.stream().map(function));
    }

    /**
     * @param kValues
     * @param function
     * @param <K>
     * @return
     * @apiNote author: czz; k -> Integer, then sum.
     */
    public static <K> int sumIntProperty(List<K> kValues, ToIntFunction<K> function) {
        return kValues.stream().mapToInt(function).sum();
    }

    /**
     * @param kValues
     * @param function
     * @param <K>
     * @param <V>
     * @return
     * @apiNote author: czz; k -> v, then collect to List<V>
     */
    public static <K, V> List<V> collectV(List<K> kValues, Function<K, V> function) {
        if (CollectionUtils.isEmpty(kValues)) {
            return new ArrayList<>();
        }
        return kValues.stream().map(function).collect(Collectors.toList());
    }

    /**
     * @param values
     * @param <T>
     * @return
     * @apiNote author: czz; 判断全null
     */
    public static <T> Boolean checkAllNull(Stream<T> values) {
        return values.filter(Objects::nonNull).findAny().isEmpty();
    }

    /**
     * @param values
     * @param <T>
     * @return
     * @apiNote author: czz; 判断全null
     */
    public static <T> Boolean checkAllNull(T... values) {
        return Arrays.stream(values).filter(Objects::nonNull).findAny().isEmpty();
    }

    /**
     * @param values
     * @param <T>
     * @return
     * @apiNote author: czz; 判断是否全为null
     */
    public static <T> Boolean checkAllNull(List<T> values) {
        return values.stream().filter(Objects::nonNull).findAny().isEmpty();
    }

    public static Boolean checkAllStrNull(List<String> values) {
        return values.stream().filter(StringUtil::cellNotEmpty).findAny().isEmpty();
    }

    /**
     * @param kValues
     * @param function
     * @param <K>
     * @param <V>
     * @return
     * @apiNote author: czz; list转steam
     */
    public static <K, V> Stream<V> collectSteamV(List<K> kValues, Function<K, V> function) {
        if (CollectionUtils.isEmpty(kValues)) {
            return null;
        }
        return kValues.stream().map(function);
    }

    /**
     * @param kValues
     * @param function
     * @param <K>
     * @param <V>
     * @return
     * @apiNote author: czz; 获取不重复字段
     */
    public static <K, V> List<V> collectDistinctV(List<K> kValues, Function<K, V> function) {
        Stream<V> vStream = collectSteamV(kValues, function);
        if (vStream == null) {
            return new ArrayList<>();
        }
        return vStream.distinct().collect(Collectors.toList());
    }

    public static <K, V> V[] collectArr(List<K> kValues, Function<K, V> function, IntFunction<V[]> functionV) {
        if (CollectionUtils.isEmpty(kValues)) {
            return null;
        }
        return kValues.stream().map(function).toArray(functionV);
    }

    public static <K, V> V[] select2collectArr(Boolean selected, List<K> kValues, Function<K, V> function, IntFunction<V[]> functionV) {
        if (!Boolean.TRUE.equals(selected) || CollectionUtils.isEmpty(kValues)) {
            return null;
        }
        return kValues.stream().map(function).toArray(functionV);
    }

    public static <K, V> List<V> select2collectV(Boolean selected, List<K> kValues, Function<K, V> function) {
        if (!Boolean.TRUE.equals(selected) || CollectionUtils.isEmpty(kValues)) {
            return new ArrayList<>();
        }
        return kValues.stream().map(function).collect(Collectors.toList());
    }

    /**
     * @param kValues
     * @param predicate
     * @param title
     * @param detail
     * @param <K>
     * @return
     * @apiNote author: czz; 获取第一个符合要求的, 查不到抛异常
     */
    public static <K> K filterFirst(List<K> kValues, Predicate<K> predicate, String title, String detail) {
        return kValues.stream().filter(predicate).findFirst().orElseThrow(() -> new BadRequestProblem(title, detail));
    }

    /**
     * @param kValues
     * @param predicate
     * @param <K>
     * @return
     * @apiNote author: czz; 获取第一个符合要求的optional
     */
    public static <K> Optional<K> filterFirstOptional(List<K> kValues, Predicate<K> predicate) {
        return kValues.stream().filter(predicate).findFirst();
    }

    /**
     * @param kValues
     * @param predicate
     * @param <K>
     * @return
     * @apiNote author: czz; 获取所有合要求的
     */
    public static <K> List<K> filterCollect(List<K> kValues, Predicate<K> predicate) {
        return kValues.stream().filter(predicate).collect(Collectors.toList());
    }

    public static <K> Map<String, List<K>> filterCollect_AllPos(List<K> kValues, Predicate<K> predicate) {
        Map<String, List<K>> map = new HashMap<>();
        List<K> trueList = kValues.stream().filter(predicate).collect(Collectors.toList());
        map.put("trueList", trueList);
        List<K> falseList = kValues.stream().filter(k -> !predicate.test(k)).collect(Collectors.toList());
        map.put("falseList", falseList);
        return map;
    }

    /**
     * @param predicate
     * @param kValueArr
     * @param <K>
     * @return
     * @apiNote author: czz; 获取合要求的数据集合
     */
    public static <K> List<K> filterCollect(Predicate<K> predicate, K... kValueArr) {
        return Arrays.stream(kValueArr).filter(predicate).collect(Collectors.toList());
    }

    public static <K> Stream<K> filterStream(List<K> kValues, Predicate<K> predicate) {
        return kValues.stream().filter(predicate);
    }

    /**
     * @param kValues
     * @param function
     * @param <K>
     * @return
     * @apiNote author: czz;   K -> Integer, then get the min value.
     */
    public static <K> Integer minIntValue(List<K> kValues, ToIntFunction<K> function) {
        return kValues.stream().mapToInt(function).min().orElseThrow();
    }

    /**
     * @param optional
     * @param title
     * @param detail
     * @param <K>
     * @return
     * @apiNote author: czz; get value, if not present then throw.
     */
    public static <K> K optionalValue(Optional<K> optional, String title, String detail) {
        return optional.orElseThrow(() -> new BadRequestProblem(title, detail));
    }

    public static <K, V> long getCount(List<K> kValues, Function<K, V> function) {
        return kValues.stream().map(function).count();
    }

    /**
     * @param kValues
     * @param function
     * @param <K>
     * @param <V>
     * @return
     * @apiNote author: czz; k -> v, then count distinct v.
     */
    public static <K, V> long distinctCount(List<K> kValues, Function<K, V> function) {
        return kValues.stream().map(function).distinct().count();
    }
}
