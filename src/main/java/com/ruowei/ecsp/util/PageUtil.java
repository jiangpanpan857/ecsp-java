package com.ruowei.ecsp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PageUtil {

    /**
     * @param list     分页数据[one page]
     * @param pageable 分页信息
     * @param count    总数
     * @param <T>      泛型
     * @return ResponseEntity.ok().headers(header).body(body);
     * @apiNote author: czz;
     */
    public static <T> ResponseEntity<List<T>> pageReturn(@NotNull List<T> list, Pageable pageable, long count) {
        Page<T> page = new PageImpl<>(list, pageable, count);
        return pageReturn(page);
    }

    /**
     * @param pageData 分页数据
     * @param <T>      泛型
     * @return ResponseEntity.ok().headers(header).body(body);
     * @apiNote author: czz;
     */
    public static <T> ResponseEntity<List<T>> pageReturn(@NotNull Page<T> pageData) {
        return ResponseEntity.ok().headers(getPageHeader(pageData)).body(pageData.getContent());
    }

    public static <T,K> ResponseEntity<List<K>> pageReturn(@NotNull Page<T> pageData, List<K> kValues) {
        return ResponseEntity.ok().headers(getPageHeader(pageData)).body(kValues);
    }

    /**
     * @param list     一页数据【List形式】
     * @param pageable 分页信息
     * @param count    总数
     * @param <T>      泛型
     * @return Page<T>
     * @apiNote author: czz;
     */
    public static <T> Page<T> getPage(List<T> list, Pageable pageable, int count) {
        return new PageImpl<>(list, pageable, count);
    }

    /**
     * @param pageData 分页数据
     * @param <T>      泛型
     * @return HttpHeaders
     * @apiNote author: czz;
     */
    public static <T> HttpHeaders getPageHeader(@NotNull Page<T> pageData) {
        return PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), pageData);
    }

    public static <T> TypeReference<List<T>> listType(T t) {
        return new TypeReference<List<T>>() {};
    }
}
