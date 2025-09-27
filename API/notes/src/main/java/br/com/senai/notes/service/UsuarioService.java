package br.com.senai.notes.service;


import br.com.senai.notes.model.Usuario;
import br.com.senai.notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

   public UsuarioService(UsuarioRepository repo) {
       usuarioRepository = repo;

    }

    // Listar

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // CADASTRAR
    public Usuario cadastrarUsuario(Usuario us) {
       return usuarioRepository.save(us);
    }

    // BUSCAR
    public Usuario buscarPorId(Integer id) { return usuarioRepository.findById(id).orElse(null);}


    // DELETAR

    public Usuario deletarUsuario(Integer id) {
        // 1. verifico se o usuario existe
        Usuario usuario = buscarPorId(id);

        // 2. se nao existir retorno nullo
        if (usuario == null) {
            return null;
        }
        // se existir excluo
        usuarioRepository.delete(usuario);
        return usuario;
    }


        // ATUALIZAR
        public Usuario atualizarUsuario(Integer id, Usuario usuarioNovo) {
            // 1. procurar quem eu vou atualizar
            Usuario usuarioAntigo = buscarPorId(id);

            // 2. Se eu nao achar retorno nullo
            if (usuarioAntigo == null) {
                return null;
            }

            // 3.
            usuarioAntigo.setEmail(usuarioNovo.getEmail());
            usuarioAntigo.setSenha(usuarioNovo.getSenha());
            usuarioAntigo.setNomeCompleto(usuarioNovo.getNomeCompleto());
            return usuarioRepository.save(usuarioAntigo);

        }



}
