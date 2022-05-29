package pl.jkap.sozzt.contract.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "contract")
@Getter
@Setter
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoiceNumber;
    private String location;
    private String executive;
    private LocalDateTime created;

    @OneToOne
    @JoinColumn(name = "idStatusContract")
    private StatusContract idStatusContract;

    @OneToMany (cascade = CascadeType.REMOVE)
    @JoinColumn(name = "idContract")
    private List<FileContract> filesContractData;
}


