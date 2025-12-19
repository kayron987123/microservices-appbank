package pe.gad.appbank.accountservice.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gad.appbank.accountservice.infrastructure.adapters.output.persistence.entities.AccountEntity;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    Boolean existsByAccountNumber(String accountNumber);
}
