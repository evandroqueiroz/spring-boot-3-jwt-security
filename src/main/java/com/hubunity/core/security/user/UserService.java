package com.hubunity.core.security.user;

import com.hubunity.core.domain.core.dicsituacao.Situacao;
import com.hubunity.core.domain.core.empresa.Empresa;
import com.hubunity.core.domain.core.pessoas.Pessoa;
import com.hubunity.core.security.user.dto.CreateUserRequest;
import com.hubunity.core.security.user.dto.UpdateUserRequest;
import com.hubunity.core.security.user.dto.UserResponseDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Senha errada");
        }

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("As senhas não são iguais");
        }

        // update the password
        user.setSenha(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    public void create(CreateUserRequest request, String currentEmpresaId) {
        var empresa = entityManager.getReference(Empresa.class, currentEmpresaId);
        var pessoa = entityManager.getReference(Pessoa.class, request.getIdPessoa());
        var situacao = entityManager.getReference(Situacao.class, request.getIdSituacao());

        var user = User.builder()
                .nomeAcesso(request.getNomeAcesso())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role(request.getRole())
                .empresa(empresa)
                .pessoa(pessoa)
                .situacao(situacao)
                .build();

        repository.save(user);
    }

    public List<UserResponseDTO> findAll(String currentEmpresaId) {
        return repository.findAllByEmpresaId(currentEmpresaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO findById(String id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void update(String id, UpdateUserRequest request) {
        var user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (request.getNomeAcesso() != null && !request.getNomeAcesso().isEmpty()) {
            user.setNomeAcesso(request.getNomeAcesso());
        }
        if (request.getSenha() != null && !request.getSenha().isEmpty()) {
            user.setSenha(passwordEncoder.encode(request.getSenha()));
        }
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }
        if (request.getIdSituacao() != null) {
            var situacao = entityManager.getReference(Situacao.class, request.getIdSituacao());
            user.setSituacao(situacao);
        }
        if (request.getIdPessoa() != null) {
            var pessoa = entityManager.getReference(Pessoa.class, request.getIdPessoa());
            user.setPessoa(pessoa);
        }

        repository.save(user);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    private UserResponseDTO toDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .nomeAcesso(user.getNomeAcesso())
                .role(user.getRole())
                .idEmpresa(user.getEmpresa().getId())
                .idPessoa(user.getPessoa().getId())
                .nomePessoa(user.getPessoa().getNomeCompleto())
                .idSituacao(user.getSituacao().getId())
                .ultimoLogin(user.getUltimoLogin())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
