package com.example.algamoney.api.usuario;

import com.example.algamoney.api.Utils.PageUtils;
import com.example.algamoney.api.base.RepositoryDefault;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl extends RepositoryDefault implements UsuarioRepositoryCustom {

    public UsuarioRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Page<Usuario> pesquisar(UsuarioFilter usuarioFilter, Pageable pageable) {
        QUsuario qUsuario = QUsuario.usuario;

        BooleanBuilder conditions = createFilter(usuarioFilter, qUsuario);

        JPAQuery<Usuario> query = queryFactory().selectFrom(qUsuario).where(conditions);

        return (Page<Usuario>) PageUtils.paginate(pageable, query);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        QUsuario qUsuario = QUsuario.usuario;

        BooleanBuilder conditions = createFilter(new UsuarioFilter(email), qUsuario);

        JPAQuery<Usuario> query = queryFactory().selectFrom(qUsuario).where(conditions);
        return Optional.ofNullable(query.fetchFirst());
    }

    private BooleanBuilder createFilter(UsuarioFilter usuarioFilter, QUsuario qUsuario) {
        BooleanBuilder conditions = new BooleanBuilder();

        if (StringUtils.isNotEmpty(usuarioFilter.getEmail())) {
            conditions.and(qUsuario.email.equalsIgnoreCase(usuarioFilter.getEmail()));
        }

        return conditions;
    }
}
