package link.rekruter.message;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import link.rekruter.candidate_message.CandidateMessage;
import link.rekruter.candidate_message.CandidateMessageRepository;


@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final CandidateMessageRepository candidateMessageRepository;

    public MessageService(final MessageRepository messageRepository,
            final CandidateMessageRepository candidateMessageRepository) {
        this.messageRepository = messageRepository;
        this.candidateMessageRepository = candidateMessageRepository;
    }

    public List<MessageDTO> findAll() {
        return messageRepository.findAll()
                .stream()
                .map(message -> mapToDTO(message, new MessageDTO()))
                .collect(Collectors.toList());
    }

    public MessageDTO get(final Long id) {
        return messageRepository.findById(id)
                .map(message -> mapToDTO(message, new MessageDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final MessageDTO messageDTO) {
        final Message message = new Message();
        mapToEntity(messageDTO, message);
        return messageRepository.save(message).getId();
    }

    public void update(final Long id, final MessageDTO messageDTO) {
        final Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(messageDTO, message);
        messageRepository.save(message);
    }

    public void delete(final Long id) {
        messageRepository.deleteById(id);
    }

    private MessageDTO mapToDTO(final Message message, final MessageDTO messageDTO) {
        messageDTO.setId(message.getId());
        messageDTO.setSender(message.getSender());
        messageDTO.setSubject(message.getSubject());
        messageDTO.setBody(message.getBody());
        messageDTO.setSendAt(message.getSendAt());
        messageDTO.setMessages(message.getMessages() == null ? null : message.getMessages().getId());
        return messageDTO;
    }

    private Message mapToEntity(final MessageDTO messageDTO, final Message message) {
        message.setSender(messageDTO.getSender());
        message.setSubject(messageDTO.getSubject());
        message.setBody(messageDTO.getBody());
        message.setSendAt(messageDTO.getSendAt());
        if (messageDTO.getMessages() != null && (message.getMessages() == null || !message.getMessages().getId().equals(messageDTO.getMessages()))) {
            final CandidateMessage messages = candidateMessageRepository.findById(messageDTO.getMessages())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "messages not found"));
            message.setMessages(messages);
        }
        return message;
    }

}
