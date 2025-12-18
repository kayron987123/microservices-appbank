package pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pe.gad.appbank.userservice.domain.model.UserDocument;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.entities.UserDocumentEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface UserDocumentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "documentType", source = "type")
    @Mapping(target = "documentNumber", source = "number")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDocumentEntity toEntity(UserDocument domain);

    // ENTIDAD -> DOMINIO
    @Mapping(target = "type", source = "documentType")
    @Mapping(target = "number", source = "documentNumber")
    UserDocument toDomain(UserDocumentEntity entity);
}
