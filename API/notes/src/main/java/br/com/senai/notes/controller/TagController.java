package br.com.senai.notes.controller;

import br.com.senai.notes.dto.tag.CadastrarTagDTO;
import br.com.senai.notes.dto.tag.ListarTagDTO;
import br.com.senai.notes.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@SecurityRequirement(name = "segurancanotes")

@io.swagger.v3.oas.annotations.tags.Tag(name = "Tags", description = "Metodos de Tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService service) {
        this.tagService = service;
    }

    // LISTAR TODAS
    @GetMapping

    @Operation (
            summary = "Metodo de Listar todas as tags",
            description = "Retorna a lista de todas as tags cadastradas"
    )
    public ResponseEntity<List<ListarTagDTO>> listarTags() {
        List<ListarTagDTO> tags = tagService.listarTodos();
        return ResponseEntity.ok(tags);
    }

    // CADASTRAR
    @PostMapping
    @Operation(
            summary = "Adiciona uma tag para o usuario",
            description = "Cria uma nova tag vinculada a um usuário"
    )
    public ResponseEntity<ListarTagDTO> adicionarTag(@RequestBody CadastrarTagDTO dto) {
        ListarTagDTO novaTag = tagService.adicionarTag(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTag);
    }


    // LISTAR POR EMAIL

    @GetMapping("/usuario/{email}")
    @Operation(summary = "Listar tags por e-mail", description = "Retorna todas as tags vinculadas a um usuário pelo e-mail informado")
    public ResponseEntity<List<ListarTagDTO>> listarTagsPorEmail(@PathVariable String email) {
        List<ListarTagDTO> tags = tagService.findByUsuarioEmail(email);

        if (tags.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(tags);
    }


    // BUSCAR POR ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Busca uma tag pelo ID",
            description = "Retorna os detalhes de uma tag pelo seu ID"
    )
    public ResponseEntity<ListarTagDTO> buscarPorId(@PathVariable Integer id) {
        ListarTagDTO tag = tagService.buscarPorId(id);
        if (tag == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(tag);
    }

    // DELETAR
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta a tag",
            description = "Remove uma tag existente pelo seu ID"
    )
    public ResponseEntity<Void> deletarTagPorId(@PathVariable Integer id) {
        ListarTagDTO tag = tagService.deletarTag(id);
        if (tag == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(404).build();
    }

    // ATUALIZAR
    @PutMapping("/{id}")
    @Operation(
            summary = "Metodo de atualizar a tag do usuario",
            description = "Atualiza os dados de uma tag existente pelo seu ID"
    )
    public ResponseEntity<ListarTagDTO> atualizarTag(
            @PathVariable Integer id,
            @RequestBody CadastrarTagDTO dto
    ) {
        ListarTagDTO tag = tagService.atualizarTag(id, dto);
        if (tag == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tag);
    }
}
