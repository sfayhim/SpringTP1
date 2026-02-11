package pharmacie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
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
    @Positive
    @Size(max = 255)
    @Column(length = 255)
    private Integer quantite;

    @ManyToOne(optional = false)
    @NonNull
    @ToString.Exclude

    private Medicament medicament ;

    @ManyToOne(optional = false)
    @NonNull
    @ToString.Exclude

    private Commande commande ;


}
