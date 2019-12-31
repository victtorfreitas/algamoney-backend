package com.example.algamoney.api.base;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class RepositoryDefault {
    private EntityManager em;

    public RepositoryDefault(EntityManager em) {
        this.em = em;
    }

    public JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(em);
    }
}
