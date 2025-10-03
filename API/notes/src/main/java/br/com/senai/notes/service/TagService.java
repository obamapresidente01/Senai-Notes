package br.com.senai.notes.service;

import br.com.senai.notes.dto.tag.CadastrarTagDTO;
import br.com.senai.notes.dto.tag.ListarTagDTO;
import br.com.senai.notes.model.Tag;
import br.com.senai.notes.model.Usuario;
import br.com.senai.notes.repository.TagRepository;
import br.com.senai.notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final UsuarioRepository usuarioRepository;
    private final TagRepository tagRepository;

    public TagService(UsuarioRepository usuarioRepository, TagRepository tagRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tagRepository = tagRepository;
    }

    // LISTAR TODAS
    public List<ListarTagDTO> listarTodos() {
        return tagRepository.findAll()
                .stream()
                .map(this::converterParaListagemDTO)
                .collect(Collectors.toList());
    }

    // LISTAR POR EMAIL DO USUARIO
    public List<ListarTagDTO> findByUsuarioEmail(String email) {
        return tagRepository.findByUsuarioEmail(email)
                .stream()
                .map(this::converterParaListagemDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR ID
    public ListarTagDTO buscarPorId(Integer id) {
        return tagRepository.findById(id)
                .map(this::converterParaListagemDTO)
                .orElse(null);
    }

    // CADASTRAR
    public ListarTagDTO adicionarTag(CadastrarTagDTO dto) {
        Usuario usuarioAssociado = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);

        if (usuarioAssociado == null) {
            return null; // ou pode lançar uma exceção customizada
        }

        Tag novaTag = new Tag();
        novaTag.setTitulo(dto.getTitulo());
        novaTag.setUsuario(usuarioAssociado);

        Tag salvo = tagRepository.save(novaTag);
        return converterParaListagemDTO(salvo);
    }

    // DELETAR
    public ListarTagDTO deletarTag(Integer id) {
        Tag tag = tagRepository.findById(id).orElse(null);

        if (tag == null) {
            return null;
        }

        tagRepository.delete(tag);
        return converterParaListagemDTO(tag);
    }

    // ATUALIZAR
    public ListarTagDTO atualizarTag(Integer id, CadastrarTagDTO dto) {
        Tag tagAntigo = tagRepository.findById(id).orElse(null);

        if (tagAntigo == null) {
            return null;
        }

        tagAntigo.setTitulo(dto.getTitulo());
        Tag atualizado = tagRepository.save(tagAntigo);

        return converterParaListagemDTO(atualizado);
    }

    // CONVERSÃO ENTIDADE > DTO
    private ListarTagDTO converterParaListagemDTO(Tag tag) {
        ListarTagDTO dto = new ListarTagDTO();
        dto.setId(tag.getTagId());
        dto.setTitulo(tag.getTitulo());
        return dto;
    }
}
