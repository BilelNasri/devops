package com.example.adoptionproject.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdoptantDTO {
    String nom;
    String adresse;
    int telephone;
}
