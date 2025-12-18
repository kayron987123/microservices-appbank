package pe.gad.appbank.userservice.domain.event;

public interface DomainEvent {
    String aggregateId();
    String aggregateType();
}
