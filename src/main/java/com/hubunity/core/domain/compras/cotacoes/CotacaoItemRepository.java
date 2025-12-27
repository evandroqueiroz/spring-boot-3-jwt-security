package com.hubunity.core.domain.compras.cotacoes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotacaoItemRepository extends JpaRepository<CotacaoItem, String> {
}
