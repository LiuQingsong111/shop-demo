package com.shop.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 *
 * @author shop-demo
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private Long pageNum;

    /**
     * 每页数量
     */
    private Long pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 构建分页结果
     */
    public static <T> PageResult<T> of(Long pageNum, Long pageSize, Long total, List<T> list) {
        PageResult<T> result = new PageResult<>();
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setTotal(total);
        result.setPages((total + pageSize - 1) / pageSize);
        result.setList(list);
        return result;
    }
}
