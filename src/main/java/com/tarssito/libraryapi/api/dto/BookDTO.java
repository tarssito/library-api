package com.tarssito.libraryapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {
    private Long id;

    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @NotEmpty
    private String isbn;
}
