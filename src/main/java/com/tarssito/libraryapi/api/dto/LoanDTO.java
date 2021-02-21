package com.tarssito.libraryapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
    private Long id;

    @NotEmpty
    private String costumer;

    @NotEmpty
    private String email;

    @NotEmpty
    private String isbn;

    @NotEmpty
    private BookDTO book;
}
