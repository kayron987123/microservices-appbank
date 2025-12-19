package pe.gad.appbank.cardservice.application.ports.output;

import java.util.List;

public interface OutboxPersistencePort {
    void saveAll(List<Object> events);
}
