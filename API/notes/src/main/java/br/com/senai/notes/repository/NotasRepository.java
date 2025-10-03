package br.com.senai.notes.repository;

import br.com.senai.notes.model.Notas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface NotasRepository extends JpaRepository<Notas,Integer> {
    List<Notas> findByUsuarioEmail(String titulo);
}
