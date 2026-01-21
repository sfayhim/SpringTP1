package pharmacie.entity;

import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
public class Ligne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // la clé est autogénérée par la BD, On ne veut pas de "setter"
    private Integer id;

    @ToString.Exclude
    @Size(max = 255)
    @Column(length = 255)
    private String quantite;

    @ManyToOne(optional = false)
    @NonNull
    @ToString.Exclude

    private Medicament medicament ;

    @ManyToOne(optional = false)
    @NonNull
    @ToString.Exclude

    private Commande commande ;


}
