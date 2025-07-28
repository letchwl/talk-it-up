package br.com.tiu.forum.domain.resposta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {

    @Query("select r from Resposta r join fetch r.autor where r.id = :id")
    Optional<Resposta> findById(Long id);

}
