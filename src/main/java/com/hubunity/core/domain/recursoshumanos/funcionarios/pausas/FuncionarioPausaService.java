package com.hubunity.core.domain.recursoshumanos.funcionarios.pausas;

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
public class FuncionarioPausaService {

  @PersistenceContext
  private EntityManager entityManager;

  private final FuncionarioPausaRepository pausaRepository;

  // -------------------- POST -------------------- //

  @Transactional
  public FuncionarioPausaResponse criar(FuncionarioPausaRequest request) {
    FuncionarioPausa pausa = new FuncionarioPausa();

    pausa.setEmpresa(getEmpresaFromTenant());
    pausa.setFuncionario(entityManager.getReference(Funcionario.class, request.getIdFuncionario()));
    pausa.setTipo(request.getTipo());
    pausa.setHorarioInicio(request.getHorarioInicio());
    pausa.setHorarioFim(request.getHorarioFim());
    pausa.setDescricao(request.getDescricao());
    pausa.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);
    pausa.setCreatedAt(new Date());
    pausa.setUpdatedAt(new Date());

    FuncionarioPausa saved = pausaRepository.save(pausa);
    return toResponse(saved);
  }

  // -------------------- PUT -------------------- //

  @Transactional
  public FuncionarioPausaResponse atualizar(String id, FuncionarioPausaRequest request) {
    FuncionarioPausa pausa = pausaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Pausa não encontrada"));

    pausa.setEmpresa(getEmpresaFromTenant());
    pausa.setFuncionario(entityManager.getReference(Funcionario.class, request.getIdFuncionario()));
    pausa.setTipo(request.getTipo());
    pausa.setHorarioInicio(request.getHorarioInicio());
    pausa.setHorarioFim(request.getHorarioFim());
    pausa.setDescricao(request.getDescricao());
    pausa.setAtivo(request.getAtivo());
    pausa.setUpdatedAt(new Date());

    FuncionarioPausa updated = pausaRepository.save(pausa);
    return toResponse(updated);
  }

  // -------------------- GET -------------------- //

  @Transactional(readOnly = true)
  public FuncionarioPausaResponse buscarPorId(String id) {
    FuncionarioPausa pausa = pausaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Pausa não encontrada"));
    return toResponse(pausa);
  }

  @Transactional(readOnly = true)
  public List<FuncionarioPausaResponse> listarTodos() {
    return pausaRepository.findAll().stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<FuncionarioPausaResponse> buscarPorTipo(String tipo) {
    return pausaRepository.findByTipo(tipo).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<FuncionarioPausaResponse> buscarPorFuncionario(String idFuncionario) {
    return pausaRepository.findByFuncionarioId(idFuncionario).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<FuncionarioPausaResponse> buscarPorAtivo(Boolean ativo) {
    return pausaRepository.findByAtivo(ativo).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  // -------------------- DELETE -------------------- //

  @Transactional
  public void deletar(String id) {
    if (!pausaRepository.existsById(id)) {
      throw new RuntimeException("Pausa não encontrada");
    }
    pausaRepository.deleteById(id);
  }

  // -------------------- RESPONSE -------------------- //

  private FuncionarioPausaResponse toResponse(FuncionarioPausa pausa) {
    return new FuncionarioPausaResponse(
        pausa.getId(),
        pausa.getEmpresa() != null ? pausa.getEmpresa().getId() : null,
        pausa.getFuncionario() != null ? pausa.getFuncionario().getId() : null,
        pausa.getTipo(),
        pausa.getHorarioInicio(),
        pausa.getHorarioFim(),
        pausa.getDescricao(),
        pausa.getAtivo());
  }

  private Empresa getEmpresaFromTenant() {
    String idEmpresa = TenantContext.getCurrentTenant();

    if (idEmpresa == null || idEmpresa.isBlank()) {
      throw new IllegalStateException("Empresa não encontrada no contexto do tenant");
    }

    return entityManager.getReference(Empresa.class, idEmpresa);
  }

}
