package com.hubunity.core.domain.horariotrabalho;

import com.hubunity.core.common.context.TenantContext;
import com.hubunity.core.domain.empresa.Empresa;
import com.hubunity.core.domain.funcionarios.Funcionario;
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
public class HorarioTrabalhoService {

    @PersistenceContext
    private EntityManager entityManager;

    private final HorarioTrabalhoRepository repository;

    // -------------------- POST -------------------- //

    @Transactional
    public HorarioTrabalhoResponse criar(HorarioTrabalhoRequest request) {
        HorarioTrabalho horario = new HorarioTrabalho();

        horario.setEmpresa(getEmpresaFromTenant());
        horario.setFuncionario(entityManager.getReference(Funcionario.class, request.getIdFuncionario()));
        horario.setDiaSemana(request.getDiaSemana());
        horario.setHoraInicio(request.getHoraInicio());
        horario.setHoraFim(request.getHoraFim());
        horario.setIntervaloInicio(request.getIntervaloInicio());
        horario.setIntervaloFim(request.getIntervaloFim());
        horario.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);
        horario.setCreatedAt(new Date());
        horario.setUpdatedAt(new Date());

        HorarioTrabalho saved = repository.save(horario);
        return toResponse(saved);
    }

    // -------------------- PUT -------------------- //

    @Transactional
    public HorarioTrabalhoResponse atualizar(String id, HorarioTrabalhoRequest request) {
        HorarioTrabalho horario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horário de trabalho não encontrado"));

        horario.setEmpresa(getEmpresaFromTenant());
        horario.setFuncionario(entityManager.getReference(Funcionario.class, request.getIdFuncionario()));
        horario.setDiaSemana(request.getDiaSemana());
        horario.setHoraInicio(request.getHoraInicio());
        horario.setHoraFim(request.getHoraFim());
        horario.setIntervaloInicio(request.getIntervaloInicio());
        horario.setIntervaloFim(request.getIntervaloFim());
        horario.setAtivo(request.getAtivo());
        horario.setUpdatedAt(new Date());

        HorarioTrabalho updated = repository.save(horario);
        return toResponse(updated);
    }

    // -------------------- GET -------------------- //

    @Transactional(readOnly = true)
    public HorarioTrabalhoResponse buscarPorId(String id) {
        HorarioTrabalho horario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horário de trabalho não encontrado"));
        return toResponse(horario);
    }

    @Transactional(readOnly = true)
    public List<HorarioTrabalhoResponse> listarTodos() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HorarioTrabalhoResponse> buscarPorFuncionario(String idFuncionario) {
        return repository.findByFuncionarioId(idFuncionario).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HorarioTrabalhoResponse> buscarPorDiaSemana(Integer diaSemana) {
        return repository.findByDiaSemana(diaSemana).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HorarioTrabalhoResponse> buscarPorAtivo(Boolean ativo) {
        return repository.findByAtivo(ativo).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // -------------------- DELETE -------------------- //

    @Transactional
    public void deletar(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Horário de trabalho não encontrado");
        }
        repository.deleteById(id);
    }

    // -------------------- RESPONSE -------------------- //

    private HorarioTrabalhoResponse toResponse(HorarioTrabalho horario) {
        return new HorarioTrabalhoResponse(
                horario.getId(),
                horario.getEmpresa() != null ? horario.getEmpresa().getId() : null,
                horario.getFuncionario() != null ? horario.getFuncionario().getId() : null,
                horario.getFuncionario() != null && horario.getFuncionario().getPessoa() != null
                        ? horario.getFuncionario().getPessoa().getNomeCompleto()
                        : null,
                horario.getDiaSemana(),
                horario.getHoraInicio(),
                horario.getHoraFim(),
                horario.getIntervaloInicio(),
                horario.getIntervaloFim(),
                horario.getAtivo());
    }

    private Empresa getEmpresaFromTenant() {
        String idEmpresa = TenantContext.getCurrentTenant();

        if (idEmpresa == null || idEmpresa.isBlank()) {
            throw new IllegalStateException("Empresa não encontrada no contexto do tenant");
        }

        return entityManager.getReference(Empresa.class, idEmpresa);
    }

}
