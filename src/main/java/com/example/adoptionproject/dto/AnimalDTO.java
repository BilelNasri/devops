package com.example.adoptionproject.dto;

import com.example.adoptionproject.entities.Espece;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnimalDTO {
    String nom;
    int age;
    boolean sterilise;
    Espece espece;
}
