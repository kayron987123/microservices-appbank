package pe.gad.appbank.cardservice.domain.event;

public interface DomainEvent {
    String aggregateId();
    String aggregateType();
}
