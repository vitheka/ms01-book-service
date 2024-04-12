package br.com.vitor.ms01bookservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Book implements Serializable {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 180)
    private String author;
    @Column(name = "launch_date", nullable = false)
    private LocalDateTime launchDate;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private String title;

    //TODO removes
    @Transient
    private String currency;
    @Transient
    private String environment;
    //end to-do

}
