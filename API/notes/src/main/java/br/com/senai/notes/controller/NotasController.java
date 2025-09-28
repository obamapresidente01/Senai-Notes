package br.com.senai.notes.controller;

import br.com.senai.notes.model.Notas;
import br.com.senai.notes.service.NotasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")

public class NotasController {

    private NotasService notasService;

    public NotasController(NotasService notas) {
        notasService = notas;
    }

    @GetMapping
    //listar todas as notas
    public ResponseEntity<List<Notas>> ListarNotas() {
        //1-pegar lista
        List<Notas> notas = notasService.ListarTodos();
        return ResponseEntity.ok().body(notas);
    }

    @PostMapping
    //cadastrar notas
    public ResponseEntity<Notas> cadastrarNota(@RequestBody Notas notas) {
        //1 - tentar cadastrar anotacao
        notasService.cadastrarNotas(notas);
        return ResponseEntity.status(HttpStatus.CREATED).body(notas);
    }

    @GetMapping("/{id}")
    //buscar anotacao por id
    public ResponseEntity<?> buscarAnotacaoPorId(@PathVariable Integer id) {
        //1-procurar anotacao
        Notas notas = notasService.buscarPorId(id);
        //2-se nao encontrar, retornar erro
        if (notas == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anotacão " + id + "não encontrada!");
        }

        //3-se encontrar, retorno a anotacao
        return ResponseEntity.ok(notas);
    }

    @DeleteMapping("/{id}")
    //deletar anotacao
    public ResponseEntity<?> deletarAnotacao(@PathVariable Integer id) {
        //1-verificar se a anotacao existe
        Notas notas = notasService.deletarNotas(id);
        //2-se nao existir retorno erro
        if (notas == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anotação " + id + "não foi encontrada!");
        }
        //3-se existir, retorno ok
        return ResponseEntity.ok(notas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAnotacao(@PathVariable Integer id, @RequestBody Notas notaNova) {
        //1-procuro a anotacao e tento atualizar
        Notas notas = notasService.atualizarNotas(id, notaNova);
        //2-se nao encontrar, mostro um erro
        if (notas == null) {
            return ResponseEntity.status(404).body("A anotação não foi encontrada!");
        }
        //3-se achar, retorno ok
        return ResponseEntity.ok(notas);
    }
}
