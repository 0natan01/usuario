package com.example.usuario.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneDto {
    private Long id;
    private String numero;
    private String ddd;
}
