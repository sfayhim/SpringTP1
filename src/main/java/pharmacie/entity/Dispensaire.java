
package pharmacie.entity;

import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Dispensaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // la clé est auto-générée par la BD, On ne veut pas de "setter"
    private Integer code;

    @Size(max = 10)
    @Column(length = 10)
    private String code_postal;

    @Size(max = 15)
    @Column(length = 15)
    private String pays;

    @Size(max = 30)
    @Column(length = 30)
    private String region;

    @Size(max = 15)
    @Column(length = 15)
    private String ville;

    @Size(max = 24)
    @Column(length = 24)
    private String fax;

    @Size(max = 24)
    @Column(length = 24)
    private String telephone;

    @Size(max = 30)
    @Column(length = 30)
    private String contact;

    @Size(max = 30)
    @Column(length = 30)
    private String fonction;

    @ToString.Exclude
    @Size(max = 40)
    @Column(length = 40)
    private String nom;

    @Size(max = 60)
    @Column(length = 60)
    private String adresse;


    @ToString.Exclude
    // CascadeType.ALL signifie que toutes les opérations CRUD sur le dispensaire sont également appliquées à ses commandes
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "dispensaire")
    private List<Commande> commandes = new LinkedList<>();
}




