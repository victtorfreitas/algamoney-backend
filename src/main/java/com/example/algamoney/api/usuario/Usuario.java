package com.example.algamoney.api.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity(name = "usuario")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    private String nome;
    private String senha;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_permissao", joinColumns = @JoinColumn(name = "codigo_usuario"),
            inverseJoinColumns = @JoinColumn(name = "codigo_permissao"))
    private List<Permissao> permissoes;


    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @JsonIgnore
    @Transient
    public Collection<? extends GrantedAuthority> getPermissoes() {
        return this.permissoes
                .stream()
                .map(permissao -> new SimpleGrantedAuthority(permissao.getDescricao().toUpperCase()))
                .collect(Collectors.toSet());
    }
}


