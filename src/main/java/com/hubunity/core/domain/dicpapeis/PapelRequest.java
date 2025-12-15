package com.hubunity.core.domain.dicpapeis;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PapelRequest {

    @NotBlank(message = "ID da situação é obrigatório")
    private String idSituacao;

    @NotBlank(message = "Nome do serviço é obrigatório")
    @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
    private String nome;

    @Size(max = 150, message = "Descrição deve ter no máximo 150 caracteres")
    private String descricao;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class PapelResponse {
    private String id;
    private String idSituacao;
    private String nome;
    private String descricao;
    private Date createdAt;

}

