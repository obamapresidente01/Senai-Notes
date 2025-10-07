package br.com.senai.notes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "usuario")

public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Column(name = "nome_completo", nullable = false, columnDefinition = "TEXT")
    private String nomeCompleto;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT", unique = true)
    private String email;

    @Column(name = "senha", nullable = false, columnDefinition = "TEXT")
    private String senha;


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        // Retorna a senha do usuário (que estará criptografada no banco).
        return this.senha;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        // Retorna o identificador único do usuário para o login (estamos usando o email).
        return this.email;
    }

    // Para simplificar, os métodos abaixo sempre retornam 'true',
    // indicando que a conta do usuário está sempre ativa e válida.
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true; // A conta não expirou?
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true; // A conta não está bloqueada?
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true; // As credenciais não expiraram?
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true; // A conta está habilitada?
    }

}
