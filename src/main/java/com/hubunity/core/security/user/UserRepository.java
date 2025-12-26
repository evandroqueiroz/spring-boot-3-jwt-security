package com.hubunity.core.security.user;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByNomeAcesso(String nomeAcesso);

  List<User> findAllByEmpresaId(String idEmpresa);

}
