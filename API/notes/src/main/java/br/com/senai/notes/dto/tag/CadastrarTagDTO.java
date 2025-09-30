package br.com.senai.notes.dto.tag;

import br.com.senai.notes.model.Usuario;
import lombok.Data;

@Data
public class CadastrarTagDTO {
    private String titulo;
    private Integer usuarioId;
}
