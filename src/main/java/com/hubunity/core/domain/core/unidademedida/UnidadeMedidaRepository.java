package com.hubunity.core.domain.core.unidademedida;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnidadeMedidaRepository extends JpaRepository<UnidadeMedida, String> {

    List<UnidadeMedida> findByCodigo(String codigo);

    List<UnidadeMedida> findByNome(String nome);

}
