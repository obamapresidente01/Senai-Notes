package br.com.senai.notes.dto.tag;

import br.com.senai.notes.model.Tag;
import br.com.senai.notes.model.Usuario;
import lombok.Data;

@Data
public class ListarTagDTO {
    private Integer id;
    private String titulo;
    // TODO: Trocar para DTO de Usuario
    private Usuario usuario;

    public ListarTagDTO(Tag tag) {
        this.id = tag.getTagId();
        this.titulo = tag.getTitulo();
    }
}
