package br.com.senai.notes.dto.usuario;

import br.com.senai.notes.model.Usuario;
import lombok.Data;

@Data
public class UsuarioListarDto {
    private String nomeCompleto;
    private String email;
    private Integer usuarioId;

}
