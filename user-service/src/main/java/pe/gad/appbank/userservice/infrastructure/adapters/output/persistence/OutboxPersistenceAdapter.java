package pe.gad.appbank.userservice.infrastructure.adapters.output.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gad.appbank.userservice.application.ports.output.OutboxPersistencePort;
import pe.gad.appbank.userservice.domain.event.DomainEvent;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.entities.OutboxEventEntity;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.repository.OutboxEventRepository;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxPersistenceAdapter implements OutboxPersistencePort {
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;
    private static final String AGGREGATE_TYPE_AND_ID = "UNKNOWN";

    @Override
    public void saveAll(List<Object> events) {
        List<OutboxEventEntity> entities = events.stream()
                .map(this::mapToEntity)
                .toList();
        outboxEventRepository.saveAll(entities);
    }

    private OutboxEventEntity mapToEntity(Object event) {
        OutboxEventEntity entity = new OutboxEventEntity();

        if (event instanceof DomainEvent domainEvent) {
            entity.setAggregateId(domainEvent.aggregateId());
            entity.setAggregateType(domainEvent.aggregateType());
            entity.setEventType(event.getClass().getSimpleName());
        } else {
            entity.setAggregateId(AGGREGATE_TYPE_AND_ID);
            entity.setAggregateType(AGGREGATE_TYPE_AND_ID);
            entity.setEventType(event.getClass().getSimpleName());
        }

        String jsonPayload = objectMapper.writeValueAsString(event);
        entity.setPayload(jsonPayload);

        entity.setStatus("PENDING");

        return entity;
    }
}
