package com.shop.common.constant;

/**
 * 通用常量
 *
 * @author shop-demo
 * @since 1.0.0
 */
public class CommonConstant {

    private CommonConstant() {
    }

    /**
     * 默认分页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 最大分页大小
     */
    public static final int MAX_PAGE_SIZE = 100;

    /**
     * 是/否
     */
    public static final int YES = 1;
    public static final int NO = 0;

    /**
     * 状态：启用/禁用
     */
    public static final int STATUS_ENABLE = 1;
    public static final int STATUS_DISABLE = 0;

    /**
     * 删除标记：已删除/未删除
     */
    public static final int DELETED = 1;
    public static final int NOT_DELETED = 0;
}
