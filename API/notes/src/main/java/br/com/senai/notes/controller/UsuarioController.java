package br.com.senai.notes.controller;


import br.com.senai.notes.model.Usuario;
import br.com.senai.notes.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService service) { usuarioService = service; }

   // LISTAR
   @GetMapping
   ResponseEntity<List<Usuario>> listarUsuario() {
        // 1.
       List<Usuario> usuarios = usuarioService.listarTodos();

       return ResponseEntity.ok(usuarios);
   }

   // CADASTRAR
    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(
            @RequestBody Usuario usuario) {

        //1.

        usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);


    }

    // buscar
    @GetMapping("/{id}")

    public ResponseEntity<?> buscarusuarioPorId(@PathVariable Integer id) {
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
    public ResponseEntity<?> atualizarUsuario (
            @PathVariable Integer id, @RequestBody Usuario usuarioNovo) {
        // 1.
        Usuario usuario = usuarioService.atualizarUsuario(id, usuarioNovo);

        // 2.
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuario " + id + " nao encontrado!");
        }

        // 3.
        return ResponseEntity.ok(usuario);
    }




}
