package com.hubunity.core.domain.core.empresa;

import com.hubunity.core.domain.core.dicsituacao.Situacao;
import com.hubunity.core.domain.localidade.bairro.Bairro;
import com.hubunity.core.domain.localidade.cep.Cep;
import com.hubunity.core.domain.localidade.distrito.Distrito;
import com.hubunity.core.domain.localidade.dto.*;
import com.hubunity.core.domain.localidade.estado.Estado;
import com.hubunity.core.domain.localidade.localidade.Localidade;
import com.hubunity.core.domain.localidade.logradouro.Logradouro;
import com.hubunity.core.domain.localidade.municipio.Municipio;
import com.hubunity.core.domain.localidade.pais.Pais;
import com.hubunity.core.domain.localidade.tipologradouro.TipoLogradouro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    @PersistenceContext
    private EntityManager entityManager;

    private final EmpresaRepository empresaRepository;

    // -------------------- POST -------------------- //

    @Transactional
    public EmpresaResponseDTO criar(EmpresaRequest request) {
        if (request.getIdSituacao() == null) {
            throw new IllegalArgumentException("ID da Situação é obrigatório.");
        }
        if (request.getIdLocalidade() == null) {
            throw new IllegalArgumentException("ID da Localidade é obrigatório.");
        }
        if (request.getRazaoSocial() == null || request.getRazaoSocial().trim().isEmpty()) {
            throw new IllegalArgumentException("Razão Social é obrigatória.");
        }
        if (request.getDocumento() == null || request.getDocumento().trim().isEmpty()) {
            throw new IllegalArgumentException("Documento é obrigatório.");
        }

        Empresa empresa = new Empresa();

        empresa.setSituacao(entityManager.getReference(Situacao.class, request.getIdSituacao()));
        empresa.setLocalidade(entityManager.getReference(Localidade.class, request.getIdLocalidade()));
        empresa.setRazaoSocial(request.getRazaoSocial());
        empresa.setNomeFantasia(request.getNomeFantasia());
        empresa.setDocumento(request.getDocumento());
        empresa.setEmail(request.getEmail());
        empresa.setTelefone(request.getTelefone());

        Empresa saved = empresaRepository.save(empresa);
        return toResponse(saved);
    }

    // -------------------- PUT -------------------- //

    @Transactional
    public EmpresaResponseDTO atualizar(String id, EmpresaRequest request) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        if (request.getIdSituacao() != null) {
            empresa.setSituacao(entityManager.getReference(Situacao.class, request.getIdSituacao()));
        }
        if (request.getIdLocalidade() != null) {
            empresa.setLocalidade(entityManager.getReference(Localidade.class, request.getIdLocalidade()));
        }
        if (request.getRazaoSocial() != null) {
            empresa.setRazaoSocial(request.getRazaoSocial());
        }
        if (request.getNomeFantasia() != null) {
            empresa.setNomeFantasia(request.getNomeFantasia());
        }
        if (request.getDocumento() != null) {
            empresa.setDocumento(request.getDocumento());
        }
        if (request.getEmail() != null) {
            empresa.setEmail(request.getEmail());
        }
        if (request.getTelefone() != null) {
            empresa.setTelefone(request.getTelefone());
        }

        Empresa updated = empresaRepository.save(empresa);
        return toResponse(updated);
    }

    // -------------------- GET -------------------- //

    @Transactional(readOnly = true)
    public List<EmpresaResponseDTO> listarTodos() {
        return empresaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmpresaResponseDTO buscarPorId(String id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        return toResponse(empresa);
    }

    // -------------------- DELETE -------------------- //

    @Transactional
    public void deletar(String id) {
        if (!empresaRepository.existsById(id)) {
            throw new RuntimeException("Empresa não encontrada");
        }
        empresaRepository.deleteById(id);
    }

    // -------------------- RESPONSE -------------------- //

    private EmpresaResponseDTO toResponse(Empresa empresa) {
        return EmpresaResponseDTO.builder()
                .id(empresa.getId())
                .idSituacao(empresa.getSituacao() != null ? empresa.getSituacao().getId() : null)
                .localidade(mapLocalidade(empresa.getLocalidade()))
                .razaoSocial(empresa.getRazaoSocial())
                .nomeFantasia(empresa.getNomeFantasia())
                .documento(empresa.getDocumento())
                .email(empresa.getEmail())
                .telefone(empresa.getTelefone())
                .createdAt(empresa.getCreatedAt())
                .updatedAt(empresa.getUpdatedAt())
                .build();
    }

    private LocalidadeResponseDTO mapLocalidade(Localidade localidade) {
        if (localidade == null)
            return null;

        return LocalidadeResponseDTO.builder()
                .id(localidade.getId())
                .pais(mapPais(localidade.getPais()))
                .estado(mapEstado(localidade.getEstado()))
                .municipio(mapMunicipio(localidade.getMunicipio()))
                .distrito(mapDistrito(localidade.getDistrito()))
                .bairro(mapBairro(localidade.getBairro()))
                .tipoLogradouro(mapTipoLogradouro(localidade.getTipoLogradouro()))
                .logradouro(mapLogradouro(localidade.getLogradouro()))
                .cep(mapCep(localidade.getCep()))
                .numero(localidade.getNumero())
                .complemento(localidade.getComplemento())
                .build();
    }

    private PaisResponseDTO mapPais(Pais pais) {
        if (pais == null)
            return null;
        return PaisResponseDTO.builder()
                .id(pais.getId())
                .nome(pais.getNome())
                .sigla(pais.getSigla())
                .build();
    }

    private EstadoResponseDTO mapEstado(Estado estado) {
        if (estado == null)
            return null;
        return EstadoResponseDTO.builder()
                .id(estado.getId())
                .pais(mapPais(estado.getPais()))
                .nome(estado.getNome())
                .sigla(estado.getSigla())
                .build();
    }

    private MunicipioResponseDTO mapMunicipio(Municipio municipio) {
        if (municipio == null)
            return null;
        return MunicipioResponseDTO.builder()
                .id(municipio.getId())
                .estado(mapEstado(municipio.getEstado()))
                .nome(municipio.getNome())
                .build();
    }

    private DistritoResponseDTO mapDistrito(Distrito distrito) {
        if (distrito == null)
            return null;
        return DistritoResponseDTO.builder()
                .id(distrito.getId())
                .municipio(mapMunicipio(distrito.getMunicipio()))
                .nome(distrito.getNome())
                .build();
    }

    private BairroResponseDTO mapBairro(Bairro bairro) {
        if (bairro == null)
            return null;
        return BairroResponseDTO.builder()
                .id(bairro.getId())
                .municipio(mapMunicipio(bairro.getMunicipio()))
                .nome(bairro.getNome())
                .build();
    }

    private TipoLogradouroResponseDTO mapTipoLogradouro(TipoLogradouro tipo) {
        if (tipo == null)
            return null;
        return TipoLogradouroResponseDTO.builder()
                .id(tipo.getId())
                .nome(tipo.getNome())
                .build();
    }

    private LogradouroResponseDTO mapLogradouro(Logradouro logradouro) {
        if (logradouro == null)
            return null;
        return LogradouroResponseDTO.builder()
                .id(logradouro.getId())
                .tipoLogradouro(mapTipoLogradouro(logradouro.getTipoLogradouro()))
                .nome(logradouro.getNome())
                .build();
    }

    private CepResponseDTO mapCep(Cep cep) {
        if (cep == null)
            return null;
        return CepResponseDTO.builder()
                .id(cep.getId())
                .logradouro(mapLogradouro(cep.getLogradouro()))
                .bairro(mapBairro(cep.getBairro()))
                .cep(cep.getCep())
                .build();
    }

}
