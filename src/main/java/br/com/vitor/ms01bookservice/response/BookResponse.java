package br.com.vitor.ms01bookservice.response;

import lombok.*;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse  {

    private Long id;
    private String author;
    private LocalDateTime launchDate;
    private Double price;
    private String title;

}
