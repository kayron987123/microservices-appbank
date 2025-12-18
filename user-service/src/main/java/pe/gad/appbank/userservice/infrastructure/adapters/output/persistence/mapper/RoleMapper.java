package pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.gad.appbank.userservice.domain.model.Role;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.entities.RoleEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface RoleMapper {
    Role toDomain(RoleEntity role);
}
