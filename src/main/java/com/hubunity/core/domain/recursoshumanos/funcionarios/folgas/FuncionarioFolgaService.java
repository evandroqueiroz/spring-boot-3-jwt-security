package com.hubunity.core.domain.recursoshumanos.funcionarios.folgas;

import com.hubunity.core.common.context.TenantContext;
import com.hubunity.core.domain.core.empresa.Empresa;
import com.hubunity.core.domain.recursoshumanos.funcionarios.Funcionario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioFolgaService {

  @PersistenceContext
  private EntityManager entityManager;

  private final FuncionarioFolgaRepository folgaRepository;

  // -------------------- POST -------------------- //

  @Transactional
  public FuncionarioFolgaResponse criar(FuncionarioFolgaRequest request) {
    FuncionarioFolga folga = new FuncionarioFolga();

    folga.setEmpresa(getEmpresaFromTenant());
    folga.setFuncionario(entityManager.getReference(Funcionario.class, request.getIdFuncionario()));
    folga.setDataInicio(request.getDataInicio());
    folga.setDataFim(request.getDataFim());
    folga.setMotivo(request.getMotivo());
    folga.setTipo(request.getTipo());
    folga.setAprovado(request.getAprovado() != null ? request.getAprovado() : false);
    folga.setObservacoes(request.getObservacoes());
    folga.setCreatedAt(new Date());
    folga.setUpdatedAt(new Date());

    FuncionarioFolga saved = folgaRepository.save(folga);
    return toResponse(saved);
  }

  // -------------------- PUT -------------------- //

  @Transactional
  public FuncionarioFolgaResponse atualizar(String id, FuncionarioFolgaRequest request) {
    FuncionarioFolga folga = folgaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Folga não encontrada"));

    folga.setEmpresa(getEmpresaFromTenant());
    folga.setFuncionario(entityManager.getReference(Funcionario.class, request.getIdFuncionario()));
    folga.setDataInicio(request.getDataInicio());
    folga.setDataFim(request.getDataFim());
    folga.setMotivo(request.getMotivo());
    folga.setTipo(request.getTipo());
    folga.setAprovado(request.getAprovado());
    folga.setObservacoes(request.getObservacoes());
    folga.setUpdatedAt(new Date());

    FuncionarioFolga updated = folgaRepository.save(folga);
    return toResponse(updated);
  }

  // -------------------- GET -------------------- //

  @Transactional(readOnly = true)
  public FuncionarioFolgaResponse buscarPorId(String id) {
    FuncionarioFolga folga = folgaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Folga não encontrada"));
    return toResponse(folga);
  }

  @Transactional(readOnly = true)
  public List<FuncionarioFolgaResponse> listarTodos() {
    return folgaRepository.findAll().stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<FuncionarioFolgaResponse> buscarPorTipo(String tipo) {
    return folgaRepository.findByTipo(tipo).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<FuncionarioFolgaResponse> buscarPorFuncionario(String idFuncionario) {
    return folgaRepository.findByFuncionarioId(idFuncionario).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<FuncionarioFolgaResponse> buscarPorAprovado(Boolean aprovado) {
    return folgaRepository.findByAprovado(aprovado).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  // -------------------- DELETE -------------------- //

  @Transactional
  public void deletar(String id) {
    if (!folgaRepository.existsById(id)) {
      throw new RuntimeException("Folga não encontrada");
    }
    folgaRepository.deleteById(id);
  }

  // -------------------- RESPONSE -------------------- //

  private FuncionarioFolgaResponse toResponse(FuncionarioFolga folga) {
    return new FuncionarioFolgaResponse(
        folga.getId(),
        folga.getEmpresa() != null ? folga.getEmpresa().getId() : null,
        folga.getFuncionario() != null ? folga.getFuncionario().getId() : null,
        folga.getDataInicio(),
        folga.getDataFim(),
        folga.getMotivo(),
        folga.getTipo(),
        folga.getAprovado(),
        folga.getObservacoes());
  }

  private Empresa getEmpresaFromTenant() {
    String idEmpresa = TenantContext.getCurrentTenant();

    if (idEmpresa == null || idEmpresa.isBlank()) {
      throw new IllegalStateException("Empresa não encontrada no contexto do tenant");
    }

    return entityManager.getReference(Empresa.class, idEmpresa);
  }

}
