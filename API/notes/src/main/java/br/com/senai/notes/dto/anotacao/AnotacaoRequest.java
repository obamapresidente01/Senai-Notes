package br.com.senai.notes.dto.anotacao;

import lombok.Data;

@Data
public class AnotacaoRequest {
    private String titulo;
    private String conteudo;
    private String imagemUrl;
}
