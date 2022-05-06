package com.ruowei.ecsp.util;

import com.ruowei.ecsp.web.rest.errors.BadRequestProblem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class AssertUtil {

    public static <T> void notNullThrow(T property, String title, String detail) {
        if (property != null) {
            toThrow(title, detail);
        }
    }

    public static <T> void nullThrow(T property, String title, String detail) {
        if (property == null) {
            toThrow(title, detail);
        }
    }

    public static void thenThrow(Boolean expression, String title, String detail) {
        if (expression) {
            toThrow(title, detail);
        }
    }

    public static void falseThrow(Boolean expression, String title, String detail) {
        if (!expression) {
            toThrow(title, detail);
        }
    }

    public static void toThrow(String title) {
        throw new BadRequestProblem(title);
    }

    public static void toThrow(String title, String detail) {
        throw new BadRequestProblem(title, detail);
    }

    public static <K> void blankThrow(List<K> kValues, String title, String detail) {
        if (CollectionUtils.isEmpty(kValues)) {
            toThrow(title, detail);
        }
    }

    public static <K> void blankThrow(String title, String detail, @NotNull @NotEmpty K... kValues) {
        if (kValues[0] == null) {
            toThrow(title, detail);
        }
    }

    /*
    pool select relative
     */

    /**
     * @param selected
     * @param value
     * @param title
     * @param detail
     * @apiNote author: czz; 已选择, 其对应值判空
     */
    public static void selectedBlankThrow(Boolean selected, Integer value, String title, String detail) {
        if (selected) {
            blankThrow(value, title, detail);
        }
    }

    /**
     * @param selected
     * @param value
     * @param title
     * @param detail
     * @apiNote author: czz; 已选择, 对应值判空
     */
    public static void selectedBlankThrow(Boolean selected, String value, String title, String detail) {
        if (selected) {
            blankThrow(value, title, detail);
        }
    }

    /**
     * @param selected
     * @param value
     * @param title
     * @param detail
     * @apiNote author: czz; 已选择, 对应值判空
     */
    public static void selectedBlankThrow(Boolean selected, BigDecimal value, String title, String detail) {
        if (selected) {
            blankThrow(value, title, detail);
        }
    }

    /**
     * @param selected
     * @param value
     * @param title
     * @param detail1
     * @param detail2
     * @apiNote author: czz; 选择判空, 选择为真后, 对应值判空
     */
    public static void selectBlankThrow(Boolean selected, Integer value, String title, String detail1, String detail2) {
        nullThrow(selected, title, detail1);
        if (selected) {
            blankThrow(value, title, detail2);
        }
    }

    /**
     * @param selected
     * @param value
     * @param title
     * @param detail1
     * @param detail2
     * @apiNote author: czz; 选择判空, 选择为真后, 对应值判空
     */
    public static void selectBlankThrow(Boolean selected, String value, String title, String detail1, String detail2) {
        nullThrow(selected, title, detail1);
        if (selected) {
            blankThrow(value, title, detail2);
        }
    }

    /**
     * @param selected
     * @param value
     * @param title
     * @param detail1
     * @param detail2
     * @apiNote author: czz; 选择判空, 选择为真后, 对应值判空
     */
    public static void selectBlankThrow(Boolean selected, BigDecimal value, String title, String detail1, String detail2) {
        nullThrow(selected, title, detail1);
        if (selected) {
            blankThrow(value, title, detail2);
        }
    }

    public static void blankThrow(String str, String title, String detail) {
        if (StringUtils.isBlank(str)) {
            toThrow(title, detail);
        }
    }

    public static void blankEmptyThrow(String str, String title, String detail) {
        if (StringUtils.isBlank(str) || "[]".equals(str)) {
            toThrow(title, detail);
        }
    }

    public static void blankThrow(Integer number, String title, String detail) {
        if (number == null || number <= 0) {
            toThrow(title, detail);
        }
    }

    public static void blankThrow(BigDecimal number, String title, String detail) {
        if (number == null || number.compareTo(BigDecimal.ZERO) < 0) {
            toThrow(title, detail);
        }
    }

    public static void logError(Exception e, String title, String detail) {
        e.printStackTrace();
        toThrow(title, detail);
    }

    public static void logError(Exception e, String title) {
        e.printStackTrace();
        toThrow(title);
    }

    public static void logError(String title) {
        toThrow(title);
    }

    public static void nullLogError(Object o, String title, String detail) {
        if (o == null) {
            log.error(title + ": " + detail);
        }
    }

    public static void logErrorInfo(String detail) {
        log.error(detail);
    }

    public static <T> void logErrorList(List<T> errorList) {
        if (ListUtil.isNotEmpty(errorList)) {
            log.error(errorList.toString());
        }
    }
}
