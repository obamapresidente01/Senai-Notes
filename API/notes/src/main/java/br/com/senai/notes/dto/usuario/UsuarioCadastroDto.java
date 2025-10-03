package br.com.senai.notes.dto.usuario;

import lombok.Data;

@Data
public class UsuarioCadastroDto {

    private String nomeCompleto;
    private String email;
    private String senha;
}
