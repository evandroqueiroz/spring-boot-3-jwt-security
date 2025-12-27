package com.hubunity.core.domain.localidade.endereco;

import com.hubunity.core.domain.localidade.municipio.Municipio;
import com.hubunity.core.domain.localidade.municipio.MunicipioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {

  private final EnderecoRepository enderecoRepository;
  private final MunicipioRepository municipioRepository;

  @Transactional
  public EnderecoResponseDTO criar(EnderecoRequest request) {
    Municipio municipio = municipioRepository.findById(request.getIdMunicipio())
        .orElseThrow(() -> new RuntimeException("Município não encontrado"));

    Endereco endereco = new Endereco();
    endereco.setMunicipio(municipio);
    endereco.setLogradouro(request.getLogradouro());
    endereco.setNumero(request.getNumero());
    endereco.setComplemento(request.getComplemento());
    endereco.setBairro(request.getBairro());
    endereco.setCep(request.getCep());

    Endereco saved = enderecoRepository.save(endereco);
    return toResponse(saved);
  }

  @Transactional
  public EnderecoResponseDTO atualizar(String id, EnderecoRequest request) {
    Endereco endereco = enderecoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

    if (!endereco.getMunicipio().getId().equals(request.getIdMunicipio())) {
      Municipio municipio = municipioRepository.findById(request.getIdMunicipio())
          .orElseThrow(() -> new RuntimeException("Município não encontrado"));
      endereco.setMunicipio(municipio);
    }

    endereco.setLogradouro(request.getLogradouro());
    endereco.setNumero(request.getNumero());
    endereco.setComplemento(request.getComplemento());
    endereco.setBairro(request.getBairro());
    endereco.setCep(request.getCep());

    Endereco updated = enderecoRepository.save(endereco);
    return toResponse(updated);
  }

  @Transactional(readOnly = true)
  public List<EnderecoResponseDTO> listarTodos() {
    return enderecoRepository.findAll().stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public EnderecoResponseDTO buscarPorId(String id) {
    Endereco endereco = enderecoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
    return toResponse(endereco);
  }

  @Transactional
  public void deletar(String id) {
    if (!enderecoRepository.existsById(id)) {
      throw new RuntimeException("Endereço não encontrado");
    }
    enderecoRepository.deleteById(id);
  }

  private EnderecoResponseDTO toResponse(Endereco endereco) {
    return new EnderecoResponseDTO(
        endereco.getId(),
        endereco.getMunicipio().getId(),
        endereco.getLogradouro(),
        endereco.getNumero(),
        endereco.getComplemento(),
        endereco.getBairro(),
        endereco.getCep());
  }
}
