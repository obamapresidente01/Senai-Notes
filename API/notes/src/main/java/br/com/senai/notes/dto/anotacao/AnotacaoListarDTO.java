package br.com.senai.notes.dto.anotacao;

import br.com.senai.notes.model.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AnotacaoListarDTO {
    //campos que eu quero expor na API
    private String titulo;
    private String conteudo;
    private String imagemUrl;
    private String tag_id;
}
