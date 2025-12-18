package pe.gad.appbank.userservice.application.ports.output;

import java.util.List;

public interface OutboxPersistencePort {
    void saveAll(List<Object> events);
}
