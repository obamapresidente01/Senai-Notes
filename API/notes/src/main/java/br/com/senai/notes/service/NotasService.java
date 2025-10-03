package br.com.senai.notes.service;

import br.com.senai.notes.dto.anotacao.AnotacaoCadastrarDTO;
import br.com.senai.notes.dto.anotacao.AnotacaoListarDTO;
import br.com.senai.notes.model.Notas;
import br.com.senai.notes.model.Usuario;
import br.com.senai.notes.repository.NotasRepository;
import br.com.senai.notes.repository.TagRepository;
import br.com.senai.notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class NotasService {
    //injecao de dependencia
    private final NotasRepository notasRepository;
    private final UsuarioRepository usuarioRepository;
    private final TagRepository tagRepository;

    public NotasService(NotasRepository notas, UsuarioRepository usuarioRepository, TagRepository tagRepository) {
        notasRepository = notas;
        this.usuarioRepository = usuarioRepository;
        this.tagRepository = tagRepository;
    }

    //listar notas
    public List<AnotacaoListarDTO> ListarTodos() {
        //1-busca as entidades do banco
        List<Notas> notas = notasRepository.findAll();

        //2-mapeia a lista de entidades para uma lista DTO
        return notas.stream()
                //usa metodo para auxiliar na conversao
                .map(this::converterParaListagemDTO)
                .collect(Collectors.toList());
    }

    private AnotacaoListarDTO converterParaListagemDTO(Notas notas) {
        AnotacaoListarDTO dto = new AnotacaoListarDTO();

        //mapeamento campo a campo
        dto.setTitulo(notas.getTitulo());
        dto.setConteudo(notas.getConteudo());
        dto.setImagemUrl(notas.getImagemUrl());
        //dto.setTag_id(notas.getTag_id());

        return dto;
    }

    //cadastrar notas
    public Notas cadastrarNotas(AnotacaoCadastrarDTO dto) {
        Tag tagAssociado = tagRepository.findById(dto.getTagId()).orElse(null);
        if (tagAssociado == null) {
        return null;
        }
        Usuario usuarioAssociado = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
        if (usuarioAssociado == null) {
            return null;
        }

        Notas novaAnotacao = new Notas();

        novaAnotacao.setTitulo(dto.getTitulo());
        novaAnotacao.setConteudo(dto.getConteudo());
        novaAnotacao.setImagemUrl(dto.getImagemUrl());
        novaAnotacao.setUsuario(dto.getUsuario());
        novaAnotacao.setTag(dto.getTag());

        return notasRepository.save(novaAnotacao);
    }

    //buscar por id
    public Notas buscarPorId(Integer id) {
        return notasRepository.findById(id).orElse(null);
    }

    //deletar notas
    public Notas deletarNotas(Integer id) {
        Notas notas = buscarPorId(id);
        //se n existir,retorno nulo
        if (notas == null) {
            return null;
        }
        notasRepository.delete(notas);
        return notas;
    }

    //atualizar
    public Notas atualizarNotas(Integer id, Notas notasNovas) {
        Notas notasAntigas = buscarPorId(id);
        if (notasAntigas == null) {
            return null;
        }

        //se eu encontrar eu atualizo
        notasAntigas.setConteudo(notasNovas.getConteudo());
        notasAntigas.setData(notasNovas.getData());
        notasAntigas.setTag(notasNovas.getTag());
        notasAntigas.setTitulo(notasNovas.getTitulo());
        notasAntigas.setUsuario(notasNovas.getUsuario());
        notasAntigas.setImagemUrl(notasNovas.getImagemUrl());
        return notasRepository.save(notasAntigas);
    }
}
