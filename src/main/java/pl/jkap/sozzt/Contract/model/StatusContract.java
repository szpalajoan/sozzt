package pl.jkap.sozzt.Contract.model;


import lombok.Getter;

import javax.persistence.*;


@Entity
@Table(name = "status_contract")
@Getter
public class StatusContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStatusContract;
    private String statusText;

}
