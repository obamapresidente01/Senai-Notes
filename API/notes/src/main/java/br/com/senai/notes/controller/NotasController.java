package br.com.senai.notes.controller;

import br.com.senai.notes.dto.anotacao.AnotacaoCadastrarDTO;
import br.com.senai.notes.dto.anotacao.AnotacaoListarDTO;
import br.com.senai.notes.model.Notas;
import br.com.senai.notes.service.NotasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
@SecurityRequirement(name = "segurancanotes")
@Tag(name = "Notas", description = "Metodos de Notas")

public class NotasController {

    private NotasService notasService;

    public NotasController(NotasService notas) {
        notasService = notas;
    }

    @GetMapping
    @Operation(
            summary = "Metodo de Listar todas as Anotacoes",
            description = "Esse metodo mostra todas as anotacoes"
    )
    //listar todas as notas
    public ResponseEntity<List<AnotacaoListarDTO>> Listar() {
        //1-pegar lista
        List<AnotacaoListarDTO> notas = notasService.ListarTodos();
        return ResponseEntity.ok(notas);
    }

    @PostMapping
    @Operation(
            summary = "Metodo de cadastrar todas as Anotacoes",
            description = "Cadastro de Anotacoes"
    )

    //cadastrar notas
    public ResponseEntity<Notas> cadastrarNota(@RequestBody AnotacaoCadastrarDTO dto) {

        Notas anotacaoSalva = notasService.cadastrarNotas(dto);

//        if(anotacaoSalva == null){
//            return ResponseEntity.badRequest();
//        }

        return ResponseEntity.status(HttpStatus.CREATED).body(anotacaoSalva);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Metodo de buscar todas as Anotacoes",
            description = "Buscar Anotacoes por ID"
    )
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


    @GetMapping("/buscarEmail/{email}")
    @Operation(
            summary = "Metodo de buscar todas as Anotacoes por Email",
            description = "Buscar Anotacoes por Email"
    )
    //buscar anotacao por EMAIL
    public ResponseEntity<?> buscarAnotacaoPorEmail(@PathVariable String email) {
        //1-procurar anotacao
        List<Notas> notas = notasService.buscarPorEmail(email);
        //2-se nao encontrar, retornar erro
        if (notas == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anotacão " + email + "não encontrada!");
        }
        //3-se encontrar, retorno a anotacao
        return ResponseEntity.ok(notas);
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Metodo de deletar todas as Anotacoes",
            description = "Metodo para deletar Anotacoes Existentes"
    )
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
    @Operation(
            summary = "Metodo de atualizar Anotacoes",
            description = "Metodo para atualizar Anotacoes Existentes"
    )
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
