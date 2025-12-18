package pe.gad.appbank.accountservice.domain.model.event;

public interface DomainEvent {
    String aggregateId();
    String aggregateType();
}
