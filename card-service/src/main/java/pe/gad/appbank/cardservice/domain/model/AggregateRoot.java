package pe.gad.appbank.cardservice.domain.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {
    private final List<Object> domainEvents = new ArrayList<>();

    protected void registerEvent(Object event) {
        this.domainEvents.add(event);
    }

    public List<Object> pullDomainEvents() {
        List<Object> events = new ArrayList<>(domainEvents);
        this.domainEvents.clear();
        return events;
    }
}
