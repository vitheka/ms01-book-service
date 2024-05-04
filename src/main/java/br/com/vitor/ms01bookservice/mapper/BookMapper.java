package br.com.vitor.ms01bookservice.mapper;

import br.com.vitor.ms01bookservice.domain.Book;
import br.com.vitor.ms01bookservice.response.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {

    BookResponse bookToBookresponse(Book request);

}
