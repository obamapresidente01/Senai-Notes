package br.com.senai.notes.service;

import br.com.senai.notes.dto.tag.CadastrarTagDTO;
import br.com.senai.notes.model.Tag;
import br.com.senai.notes.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository repo) { tagRepository = repo; }
    public List<Tag> ListarTodos() {
        return tagRepository.findAll();
    }

    public List<Tag> findByUsuarioEmail(String email) {
        return tagRepository.findByUsuarioEmail(email);
    }

    public Tag buscarPorId(Integer id) {
        return tagRepository.findById(id).orElse(null);
    }

    public Tag adicionarTag(CadastrarTagDTO dto) {
        Tag tag = new Tag();

        tag.setTitulo(dto.getTitulo());

        return tagRepository.save(tag);
    }

    public Tag deletarTag(Integer id) {
        Tag tag = buscarPorId(id);

        if(tag == null) {
            return null;
        }

        tagRepository.delete(tag);
        return tag;
    }

    public Tag atualizarTag(Integer id, Tag tagNovo) {
        Tag tagAntigo = buscarPorId(id);

        if(tagAntigo == null) {
            return null;
        }

        tagAntigo.setTitulo(tagNovo.getTitulo());
        return tagRepository.save(tagAntigo);
    }

}
