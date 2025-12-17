package pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.UUID;

@Data
@Embeddable
public class UserRoleId {
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "role_id")
    private UUID roleId;
}
