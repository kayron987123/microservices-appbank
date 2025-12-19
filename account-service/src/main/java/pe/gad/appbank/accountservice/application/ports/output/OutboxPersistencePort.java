package pe.gad.appbank.accountservice.application.ports.output;

import java.util.List;

public interface OutboxPersistencePort {
    void saveAll(List<Object> events);
}
