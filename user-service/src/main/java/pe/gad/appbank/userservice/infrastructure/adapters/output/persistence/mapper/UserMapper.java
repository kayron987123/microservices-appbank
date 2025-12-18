package pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pe.gad.appbank.userservice.domain.model.*;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.entities.UserEntity;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        uses = {UserDocumentMapper.class}
)
public interface UserMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "email", source = "email.value")
    @Mapping(target = "password", source = "password.value")
    @Mapping(target = "firstName", source = "personalInfo.firstName")
    @Mapping(target = "lastName", source = "personalInfo.lastName")
    @Mapping(target = "phone", source = "personalInfo.phone")
    @Mapping(target = "documentNumber", source = "personalInfo.documentNumber")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "deletedAt", ignore = true)
    UserEntity toEntity(User user);

    @Mapping(target = "id", source = "id", qualifiedByName = "mapUserId")
    @Mapping(target = "email", source = "email", qualifiedByName = "mapEmail")
    @Mapping(target = "password", source = "password", qualifiedByName = "mapPasswordEncoded")
    @Mapping(target = "personalInfo", source = "userEntity", qualifiedByName = "mapPersonalInfo")
    @Mapping(target = "status", source = "status")
    User toDomain(UserEntity userEntity);

    @Named("mapUserId")
    default UserId mapUserId(UUID id) {
        return id != null ? UserId.of(id) : null;
    }

    @Named("mapEmail")
    default Email mapEmail(String email) {
        return email != null ? Email.of(email) : null;
    }

    @Named("mapPasswordEncoded")
    default Password mapPasswordEncoded(String hash) {
        return hash != null ? Password.encoded(hash) : null;
    }

    @Named("mapPersonalInfo")
    default PersonalInfo mapPersonalInfo(UserEntity entity) {
        return PersonalInfo.of(
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhone(),
                entity.getDocumentNumber()
        );
    }
}
