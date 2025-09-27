package br.com.senai.notes.service;

import br.com.senai.notes.model.Notas;
import br.com.senai.notes.repository.NotasRepository;
import br.com.senai.notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class NotasService {
    //injecao de dependencia
    private final NotasRepository notasRepository;
    private final UsuarioRepository usuarioRepository;

    public NotasService(NotasRepository notas, UsuarioRepository usuarioRepository) {
        notasRepository = notas;
        this.usuarioRepository = usuarioRepository;
    }
    //listar notas
    public List<Notas> ListarTodos() {
        return notasRepository.findAll();
    }

    //cadastrar notas
    public Notas cadastrarNotas(Notas notas) {
        return notasRepository.save(notas);
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
