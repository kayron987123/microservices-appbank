package pe.gad.appbank.cardservice.infrastructure.adapters.input.rest.mapper;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pe.gad.appbank.cardservice.application.ports.dto.CardDto;
import pe.gad.appbank.cardservice.application.ports.dto.CreateCardCommand;
import pe.gad.appbank.cardservice.infrastructure.adapters.input.rest.model.request.CreateCardRequest;
import pe.gad.appbank.cardservice.infrastructure.adapters.input.rest.model.response.CardResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface CardRestMapper {
    @Mapping(target = "cardTypeName", source = "cardType")
    CreateCardCommand toCommand(@Valid CreateCardRequest request);

    CardResponse toResponse(CardDto cardDto);
}
