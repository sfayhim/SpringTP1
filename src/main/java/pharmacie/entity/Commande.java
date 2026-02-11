package pharmacie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // la clé est autogénérée par la BD, On ne veut pas de "setter"
    private Integer numero;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column()
    private Date saisiele;

    @Temporal(TemporalType.DATE)
    @Column()
    private Date envoyele;

    @Column()
    private BigDecimal port;

    @ToString.Exclude
    @Column()
    private BigDecimal remise;

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

    @ToString.Exclude
    @Size(max = 40)
    @Column(length = 40)
    private String destinataire;

    @Size(max = 60)
    @Column(length = 60)
    private String adresse;

    @ManyToOne(optional = false)
    @ToString.Exclude
    private Dispensaire dispensaire;

    @OneToMany(
        mappedBy = "commande",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Ligne> lignes = new ArrayList<>();


}
