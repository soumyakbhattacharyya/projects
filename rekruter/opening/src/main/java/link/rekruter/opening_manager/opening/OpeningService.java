package link.rekruter.opening_manager.opening;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class OpeningService {

    private final OpeningRepository openingRepository;

    public OpeningService(final OpeningRepository openingRepository) {
        this.openingRepository = openingRepository;
    }

    public List<OpeningDTO> findAll() {
        return openingRepository.findAll()
                .stream()
                .map(opening -> mapToDTO(opening, new OpeningDTO()))
                .collect(Collectors.toList());
    }

    public OpeningDTO get(final Long id) {
        return openingRepository.findById(id)
                .map(opening -> mapToDTO(opening, new OpeningDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final OpeningDTO openingDTO) {
        final Opening opening = new Opening();
        mapToEntity(openingDTO, opening);
        return openingRepository.save(opening).getId();
    }

    public void update(final Long id, final OpeningDTO openingDTO) {
        final Opening opening = openingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(openingDTO, opening);
        openingRepository.save(opening);
    }

    public void delete(final Long id) {
        openingRepository.deleteById(id);
    }

    private OpeningDTO mapToDTO(final Opening opening, final OpeningDTO openingDTO) {
        openingDTO.setId(opening.getId());
        openingDTO.setShortDescription(opening.getShortDescription());
        return openingDTO;
    }

    private Opening mapToEntity(final OpeningDTO openingDTO, final Opening opening) {
        opening.setShortDescription(openingDTO.getShortDescription());
        return opening;
    }

}
