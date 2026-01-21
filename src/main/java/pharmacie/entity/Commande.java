package pharmacie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // la clé est autogénérée par la BD, On ne veut pas de "setter"
    private Integer numero;

    @Column()
    private Date envoyele;

    @Size(min =2, max = 18)
    @Column()
    private BigDecimal port;

    @ToString.Exclude
    @Size(min =2, max = 10)
    @Column()
    private BigDecimal remise;

    @Column()
    private Date saisiele;

    @Size(max = 10)
    @Column(length = 10)
    private String code_postal;

    @Size(max = 15)
    @Column(length = 15)
    private String pays;

    @Size(max = 15)
    @Column(length = 15)
    private String region;

    @Size(max = 15)
    @Column(length = 15)
    private String ville;

    @ToString.Exclude
    @Size(max = 40)
    @Column(length = 40)
    private String destinataire;

    @Size(max = 60)
    @Column(length = 60)
    private String adresse;

    @ManyToOne(optional = false)
    @NonNull
    @ToString.Exclude

    private Dispensaire dispensaire ;

}
