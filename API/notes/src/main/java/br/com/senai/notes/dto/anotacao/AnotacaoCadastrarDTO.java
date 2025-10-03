package br.com.senai.notes.dto.anotacao;

import lombok.Data;

@Data

public class AnotacaoCadastrarDTO {
    private String titulo;
    private String conteudo;
    private String imagemUrl;
    private Integer usuarioId;
    private Integer tagId;

}
