package br.com.senai.notes.controller;


import br.com.senai.notes.dto.usuario.UsuarioCadastroDto;
import br.com.senai.notes.dto.usuario.UsuarioListarDto;
import br.com.senai.notes.model.Usuario;
import br.com.senai.notes.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@SecurityRequirement(name = "segurancanotes")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService service) { usuarioService = service; }

   // LISTAR
   @GetMapping
   @Operation(
           summary = "Metodo de listar Usuario",
           description = "Retorna a lista de todos os Usuarios cadastrados"
   )
   ResponseEntity<List<UsuarioListarDto>> listarUsuario() {
        // 1.
       List<UsuarioListarDto> usuarios = usuarioService.listarTodos();

       return ResponseEntity.ok(usuarios);
   }

   // CADASTRAR
    @PostMapping
    @Operation(
            summary = "Metodo de cadastrar Usuario",
            description = "Retorna a lista de todos os Usuarios cadastrados"
    )
    public ResponseEntity<Usuario> cadastrarUsuario(
            @RequestBody UsuarioCadastroDto dto) {

        Usuario usuario = usuarioService.cadastrarUsuario(dto);

        //1.
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);


    }

    // buscar
    @GetMapping("/{id}")
    @Operation(
            summary = "Metodo de buscar Usuario",
            description = "Retorna a lista de todos os Usuarios"
    )

    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Integer id) {
        //1.
        Usuario usuario = usuarioService.buscarPorId(id);

        // 2.
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario " + id + " nao encontrado!");
        }
        // 3.
        return ResponseEntity.ok(usuario);
    }

    // DELETAR
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Metodo de deletar Usuario",
            description = "Retorna a lista de todos os Usuarios cadastrados"
    )
    public ResponseEntity<?> deletarUsuarioPorId(@PathVariable Integer id) {

        // 1.
        Usuario usuario = usuarioService.deletarUsuario(id);

        // 2.
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario " + id + " nao encontrado!");
        }

        // 3.
        return ResponseEntity.ok(usuario);
    }

    // ATUALIZAR
    @PutMapping("/{id}")
    @Operation(
            summary = "Metodo de atualizar Usuario",
            description = "Retorna a lista de todos os Usuarios atualizados"
    )
    public ResponseEntity<?> atualizarUsuario (
            @PathVariable Integer id, @RequestBody UsuarioCadastroDto dto) {
        // 1.
        Usuario usuario = usuarioService.atualizarUsuario(id, dto);

        // 2.
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuario " + id + " nao encontrado!");
        }

        // 3.
        return ResponseEntity.ok(usuario);
    }




}
