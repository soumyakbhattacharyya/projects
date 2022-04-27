package link.rekruter.stage;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class StageService {

    private final StageRepository stageRepository;

    public StageService(final StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    public List<StageDTO> findAll() {
        return stageRepository.findAll()
                .stream()
                .map(stage -> mapToDTO(stage, new StageDTO()))
                .collect(Collectors.toList());
    }

    public StageDTO get(final Long id) {
        return stageRepository.findById(id)
                .map(stage -> mapToDTO(stage, new StageDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final StageDTO stageDTO) {
        final Stage stage = new Stage();
        mapToEntity(stageDTO, stage);
        return stageRepository.save(stage).getId();
    }

    public void update(final Long id, final StageDTO stageDTO) {
        final Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(stageDTO, stage);
        stageRepository.save(stage);
    }

    public void delete(final Long id) {
        stageRepository.deleteById(id);
    }

    private StageDTO mapToDTO(final Stage stage, final StageDTO stageDTO) {
        stageDTO.setId(stage.getId());
        stageDTO.setName(stage.getName());
        return stageDTO;
    }

    private Stage mapToEntity(final StageDTO stageDTO, final Stage stage) {
        stage.setName(stageDTO.getName());
        return stage;
    }

}
