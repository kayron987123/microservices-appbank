package pe.gad.appbank.userservice.infrastructure.adapters.output.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gad.appbank.userservice.application.ports.output.RolePersistencePort;
import pe.gad.appbank.userservice.domain.exception.RoleNotFoundException;
import pe.gad.appbank.userservice.domain.model.Role;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.entities.RoleEntity;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.mapper.RoleMapper;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.repository.RoleRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RolePersistenceAdapter implements RolePersistencePort {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Optional<Role> findRoleByName(String roleName) {
        RoleEntity role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with name: " + roleName));

        return Optional.of(roleMapper.toDomain(role));
    }
}
