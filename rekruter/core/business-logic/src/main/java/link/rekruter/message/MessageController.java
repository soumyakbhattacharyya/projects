package link.rekruter.message;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    private final MessageService messageService;

    public MessageController(final MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        return ResponseEntity.ok(messageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessage(@PathVariable final Long id) {
        return ResponseEntity.ok(messageService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createMessage(@RequestBody @Valid final MessageDTO messageDTO) {
        return new ResponseEntity<>(messageService.create(messageDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMessage(@PathVariable final Long id,
            @RequestBody @Valid final MessageDTO messageDTO) {
        messageService.update(id, messageDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable final Long id) {
        messageService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
