package pe.gad.appbank.cardservice.infrastructure.adapters.input.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.gad.appbank.cardservice.application.ports.dto.CardDto;
import pe.gad.appbank.cardservice.application.ports.dto.CreateCardCommand;
import pe.gad.appbank.cardservice.application.ports.input.CreateCardUseCase;
import pe.gad.appbank.cardservice.infrastructure.adapters.input.rest.mapper.CardRestMapper;
import pe.gad.appbank.cardservice.infrastructure.adapters.input.rest.model.request.CreateCardRequest;
import pe.gad.appbank.cardservice.infrastructure.adapters.input.rest.model.response.CardResponse;
import pe.gad.appbank.cardservice.infrastructure.adapters.input.rest.model.response.DataResponse;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardRestAdapter {
    private final CreateCardUseCase createCardUseCase;
    private final CardRestMapper cardRestMapper;

    @PostMapping
    public ResponseEntity<DataResponse<CardResponse>> createCard(@RequestBody @Valid CreateCardRequest request){
        CreateCardCommand command = cardRestMapper.toCommand(request);
        CardDto cardDto = createCardUseCase.createCard(command);
        CardResponse response = cardRestMapper.toResponse(cardDto);

        URI location = URI.create("/cards/" + response.id());

        DataResponse<CardResponse> dataResponse = DataResponse.<CardResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Card created successfully")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.created(location).body(dataResponse);
    }
}
