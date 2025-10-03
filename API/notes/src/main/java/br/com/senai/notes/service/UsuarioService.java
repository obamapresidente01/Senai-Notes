package br.com.senai.notes.service;


import br.com.senai.notes.dto.usuario.UsuarioCadastroDto;
import br.com.senai.notes.dto.usuario.UsuarioListarDto;
import br.com.senai.notes.model.Usuario;
import br.com.senai.notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

   public UsuarioService(UsuarioRepository repo) {
       usuarioRepository = repo;

    }

    // Listar

    public List<UsuarioListarDto> listarTodos() {
      // 1. Busca todas as entidades do banco
       List<Usuario> usuarios = usuarioRepository.findAll();
     /*   return usuarioRepository.findAll();*/

        // 2. Mapeia a lista de entidades para uma lista de DTOs
        return usuarios.stream()
                .map(this::converterUsuarioParaListarDTO).collect(Collectors.toList());

    }

    private UsuarioListarDto converterUsuarioParaListarDTO(Usuario usuario) {
       UsuarioListarDto dto = new UsuarioListarDto();

       // Mapeamento campo a campo
        dto.setNomeCompleto(usuario.getNomeCompleto());
        dto.setEmail(usuario.getEmail());
        dto.setUsuarioId(usuario.getUsuarioId());

        return dto;

    }






    // CADASTRAR
    public Usuario cadastrarUsuario(UsuarioCadastroDto dto) {

        Usuario usuario = new Usuario();

        usuario.setEmail(dto.getEmail());
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setSenha(dto.getSenha());



        return usuarioRepository.save(usuario);
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
        public Usuario atualizarUsuario(Integer id, UsuarioCadastroDto dto) {
            // 1. procurar quem eu vou atualizar
            Usuario usuarioAntigo = buscarPorId(id);

            // 2. Se eu nao achar retorno nullo
            if (usuarioAntigo == null) {
                return null;
            }

            // 3.
            usuarioAntigo.setEmail(dto.getEmail());
            usuarioAntigo.setSenha(dto.getSenha());
            usuarioAntigo.setNomeCompleto(dto.getNomeCompleto());
            return usuarioRepository.save(usuarioAntigo);

        }



}
