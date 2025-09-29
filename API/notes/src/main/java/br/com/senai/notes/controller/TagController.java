package br.com.senai.notes.controller;

import br.com.senai.notes.model.Tag;
import br.com.senai.notes.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tags", description = "Metodos de Tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService service) {
        this.tagService = service;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> listarTags() {

        List<Tag> tags = tagService.ListarTodos();

        return ResponseEntity.ok(tags);
    }

    @PostMapping
    public ResponseEntity<Tag> adicionarTag(@RequestBody Tag tag) {
        tagService.adicionarTag(tag);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> atualizarTagPorId(@PathVariable Integer id, @RequestBody Tag tagNovo) {

        Tag tag = tagService.atualizarTag(id, tagNovo);

        if (tag == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarTagPorId(@PathVariable Integer id) {
        Tag tag = tagService.buscarPorId(id);

        if (tag == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(tag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarTag(
            @PathVariable Integer id,
            @RequestBody Tag tagNovo
    ) {
        Tag tag = tagService.atualizarTag(id, tagNovo);
        if (tag == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(tag);
    }



}
