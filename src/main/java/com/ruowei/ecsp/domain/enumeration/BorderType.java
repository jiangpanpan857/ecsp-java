package com.ruowei.ecsp.domain.enumeration;

public enum BorderType {
    // 上下左右都有边框
    ALL,
    // 上下
    TOP_BOTTOM,
    // 上下左
    TOP_BOTTOM_LEFT,
    // 上下右
    TOP_BOTTOM_RIGHT,
    // 无
    NONE,
    // 上下加粗，左右细
    TOP_BOTTOM_REST_THIN,
    // 上左
    TOP_LEFT,
    // 上下左右窄框
    ALL_THIN,
    // 左
    LEFT,
    // 左下
    LEFT_BOTTOM
}
