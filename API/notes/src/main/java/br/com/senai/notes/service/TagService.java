package br.com.senai.notes.service;

import br.com.senai.notes.dto.tag.CadastrarTagDTO;
import br.com.senai.notes.dto.tag.ListarTagDTO;
import br.com.senai.notes.model.Tag;
import br.com.senai.notes.model.Usuario;
import br.com.senai.notes.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    public Tag criarTag(CadastrarTagDTO dto) {
        Usuario usuarioAssociado = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);

        if (usuarioAssociado == null) {
            return null;
        }

        Tag novaTag = new Tag();

        novaTag.setTitulo(dto.getTitulo());
        novaTag.setUsuario(usuarioAssociado);

        return tagRepository.save(novaTag);

    }

    private final TagRepository tagRepository;

    public TagService(TagRepository repo) { tagRepository = repo; }
    public List<ListarTagDTO> ListarTodos() {

        List<Tag> tags = tagRepository.findAll();

        return tags.stream()
                .map(this::converterParaListagemDTO)
                .collect(Collectors.toList());
    }

    public List<ListarTagDTO> findByUsuarioEmail(String email) {

        return tagRepository.findByUsuarioEmail(email)
                .stream()
                .map(this::converterParaListagemDTO)
                .collect(Collectors.toList());

    }

    public ListarTagDTO buscarPorId(Integer id) {

        return tagRepository.findById(id)
                .map(this::converterParaListagemDTO)
                .orElse(null);

    }

    public ListarTagDTO adicionarTag(CadastrarTagDTO dto) {
        Tag tag = new Tag();
        tag.setTitulo(dto.getTitulo());
        Tag salvo = tagRepository.save(tag);
        return converterParaListagemDTO(salvo);
    }

    public ListarTagDTO deletarTag(Integer id) {
        Tag tag = tagRepository.findById(id).orElse(null);

        if (tag == null) {
            return null;
        }

        tagRepository.delete(tag);
        return converterParaListagemDTO(tag);
    }

    public ListarTagDTO atualizarTag(Integer id, CadastrarTagDTO dto) {
        Tag tagAntigo = tagRepository.findById(id).orElse(null);

        if (tagAntigo == null) {
            return null;
        }
        tagAntigo.setTitulo(dto.getTitulo());
        Tag atualizada = tagRepository.save(tagAntigo);
        return converterParaListagemDTO(atualizada);

    }

    //Conversao Entidade para DTO
    private ListarTagDTO converterParaListagemDTO(Tag tag) {
        ListarTagDTO dto = new ListarTagDTO();
        dto.setId(tag.getTagId());
        dto.setTitulo(tag.getTitulo());
        return dto;
    }

}
