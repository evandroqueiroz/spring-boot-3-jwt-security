package com.hubunity.core.domain.core.pessoas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {

  Optional<Pessoa> findByEmail(String email);

  Optional<Pessoa> findByDocumento(String documento);
}
