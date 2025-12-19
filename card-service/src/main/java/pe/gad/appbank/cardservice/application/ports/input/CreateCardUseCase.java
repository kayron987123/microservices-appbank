package pe.gad.appbank.cardservice.application.ports.input;

import pe.gad.appbank.cardservice.application.ports.dto.CardDto;
import pe.gad.appbank.cardservice.application.ports.dto.CreateCardCommand;

public interface CreateCardUseCase {
    CardDto createCard(CreateCardCommand command);
}
