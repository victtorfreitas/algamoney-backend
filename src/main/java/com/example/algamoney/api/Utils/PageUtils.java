package com.example.algamoney.api.Utils;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageUtils {

    public static Page<?> paginate(Pageable pageable, JPQLQuery<?> query) {
        int pageSize = pageable.getPageSize();
        int page = pageSize * pageable.getPageNumber();

        long total = query.fetchCount();

        query.limit(pageSize).offset(page);

        return new PageImpl<>(query.fetch(), pageable, total);
    }
}
