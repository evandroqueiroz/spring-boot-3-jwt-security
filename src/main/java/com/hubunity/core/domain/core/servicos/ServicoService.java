package com.hubunity.core.domain.core.servicos;

import com.hubunity.core.domain.core.dicsituacao.Situacao;
import com.hubunity.core.domain.core.empresa.Empresa;
import com.hubunity.core.domain.core.produtos.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicoService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ServicoRepository servicoRepository;
    private final ServicoExecucaoRepository execucaoRepository;
    private final ServicoProdutoRepository servicoProdutoRepository;

    // -------------------- POST -------------------- //

    @Transactional
    public ServicoResponse criar(ServicoRequest request) {
        if (request.getPrecoPromocional() != null &&
                request.getPrecoPromocional().compareTo(request.getPreco()) > 0) {
            throw new RuntimeException("Preço promocional não pode ser maior que o preço normal");
        }
        Servico servico = new Servico();

        servico.setNome(request.getNome());
        servico.setDescricao(request.getDescricao());
        servico.setCategoria(request.getCategoria());
        servico.setPreco(request.getPreco());
        servico.setPrecoPromocional(request.getPrecoPromocional());
        servico.setDuracaoMinutos(request.getDuracaoMinutos());
        servico.setTempoToleranciaMin(request.getTempoToleranciaMin());

        servico.setSituacao(entityManager.getReference(Situacao.class, request.getIdSituacao()));
        servico.setEmpresa(entityManager.getReference(Empresa.class, request.getIdEmpresa()));

        Servico saved = servicoRepository.save(servico);
        return toResponse(saved);
    }

    @Transactional
    public ServicoResponse criarComProdutos(ServicoComProdutosRequest request) {
        ServicoResponse servicoResponse = criar(request.getServico());

        if (request.getProdutos() != null && !request.getProdutos().isEmpty()) {
            for (ServicoComProdutosRequest.ProdutoQuantidade pq : request.getProdutos()) {
                ServicoProdutoRequest spRequest = new ServicoProdutoRequest(
                        servicoResponse.getIdEmpresa(),
                        servicoResponse.getId(),
                        pq.getIdProduto(),
                        pq.getQuantidade()
                );
                vincularProduto(spRequest);
            }
        }
        return servicoResponse;
    }

    // -------------------- PUT -------------------- //

    @Transactional
    public ServicoResponse atualizar(String id, ServicoRequest request) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        if (request.getPrecoPromocional() != null &&
                request.getPrecoPromocional().compareTo(request.getPreco()) > 0) {
            throw new RuntimeException("Preço promocional não pode ser maior que o preço normal");
        }

        servico.setNome(request.getNome());
        servico.setDescricao(request.getDescricao());
        servico.setCategoria(request.getCategoria());
        servico.setPreco(request.getPreco());
        servico.setPrecoPromocional(request.getPrecoPromocional());
        servico.setDuracaoMinutos(request.getDuracaoMinutos());
        servico.setTempoToleranciaMin(request.getTempoToleranciaMin());

        servico.setSituacao(entityManager.getReference(Situacao.class, request.getIdSituacao()));
        servico.setEmpresa(entityManager.getReference(Empresa.class, request.getIdEmpresa()));

        Servico updated = servicoRepository.save(servico);
        return toResponse(updated);
    }

    // -------------------- GET -------------------- //

    @Transactional(readOnly = true)
    public ServicoResponse buscarPorId(String id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        return toResponse(servico);
    }

    @Transactional(readOnly = true)
    public List<ServicoResponse> listarTodos() {
        return servicoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ServicoResponse> buscarPorCategoria(String categoria) {
        return servicoRepository.findByCategoria(categoria).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ServicoResponse> buscarServicosComPromocao() {
        return servicoRepository.findServicosComPromocao().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // -------------------- DELETE -------------------- //

    @Transactional
    public void deletar(String id) {
        if (!servicoRepository.existsById(id)) {
            throw new RuntimeException("Serviço não encontrado");
        }
        servicoRepository.deleteById(id);
    }

    // -------------------- RESPONSE -------------------- //

    @Transactional
    public ServicoExecucaoResponse executarServico(ServicoExecucaoRequest request) {
        if (!servicoRepository.existsById(request.getIdServico())) {
            throw new RuntimeException("Serviço não encontrado");
        }

        ServicoExecucao execucao = new ServicoExecucao();
        execucao.setServico(entityManager.getReference(Servico.class, request.getIdServico()));
        execucao.setEmpresa(entityManager.getReference(Empresa.class, request.getIdEmpresa()));
        execucao.setQuantidade(request.getQuantidade());
        execucao.setObservacao(request.getObservacao());

        ServicoExecucao saved = execucaoRepository.save(execucao);
        return toExecucaoResponse(saved);
    }

    @Transactional
    public ServicoProdutoResponse vincularProduto(ServicoProdutoRequest request) {
        if (!servicoRepository.existsById(request.getIdServico())) {
            throw new RuntimeException("Serviço não encontrado");
        }

        if (servicoProdutoRepository.findByServico_IdAndProduto_Id(
                request.getIdServico(), request.getIdProduto()).isPresent()) {
            throw new RuntimeException("Produto já vinculado a este serviço");
        }

        ServicoProduto sp = new ServicoProduto();
        sp.setServico(entityManager.getReference(Servico.class, request.getIdServico()));
        sp.setEmpresa(entityManager.getReference(Empresa.class, request.getIdEmpresa()));
        sp.setProduto(entityManager.getReference(Produto.class, request.getIdProduto()));
        sp.setQuantidade(request.getQuantidade());

        ServicoProduto saved = servicoProdutoRepository.save(sp);
        return toServicoProdutoResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ServicoProdutoResponse> listarProdutosPorServico(String idServico) {
        return servicoProdutoRepository.findByServico_Id(idServico).stream()
                .map(this::toServicoProdutoResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void desvincularProduto(String id) {
        if (!servicoProdutoRepository.existsById(id)) {
            throw new RuntimeException("Vínculo não encontrado");
        }
        servicoProdutoRepository.deleteById(id);
    }

    private ServicoResponse toResponse(Servico servico) {
        return new ServicoResponse(
            servico.getId(),
            servico.getSituacao() != null ? servico.getSituacao().getId() : null,
            servico.getEmpresa() != null ? servico.getEmpresa().getId() : null,
            servico.getCodigo(),
            servico.getNome(),
            servico.getDescricao(),
            servico.getCategoria(),
            servico.getPreco(),
            servico.getPrecoPromocional(),
            servico.getDuracaoMinutos(),
            servico.getTempoToleranciaMin()
        );
    }

    private ServicoExecucaoResponse toExecucaoResponse(ServicoExecucao execucao) {
        return new ServicoExecucaoResponse(
                execucao.getId(),
                execucao.getEmpresa() != null ? execucao.getEmpresa().getId() : null,
                execucao.getServico() != null ? execucao.getServico().getId() : null,
                execucao.getDataExecucao(),
                execucao.getQuantidade(),
                execucao.getObservacao()
        );
    }

    private ServicoProdutoResponse toServicoProdutoResponse(ServicoProduto sp) {
        return new ServicoProdutoResponse(
                sp.getId(),
                sp.getServico() != null ? sp.getServico().getId() : null,
                sp.getProduto() != null ? sp.getProduto().getId() : null,
                sp.getEmpresa() != null ? sp.getEmpresa().getId() : null,
                sp.getQuantidade()
        );
    }
}