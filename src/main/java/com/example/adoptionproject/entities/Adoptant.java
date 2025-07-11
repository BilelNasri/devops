package com.example.adoptionproject.entities;

import javax.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Adoptant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAdoptant;

    private String nom;
    private String adresse;
    private int telephone;

    @OneToMany(mappedBy = "adoptant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Adoption> adoptions;

}
