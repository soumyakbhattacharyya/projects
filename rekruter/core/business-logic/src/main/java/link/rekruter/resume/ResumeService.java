package link.rekruter.resume;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(final ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public List<ResumeDTO> findAll() {
        return resumeRepository.findAll()
                .stream()
                .map(resume -> mapToDTO(resume, new ResumeDTO()))
                .collect(Collectors.toList());
    }

    public ResumeDTO get(final Long id) {
        return resumeRepository.findById(id)
                .map(resume -> mapToDTO(resume, new ResumeDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final ResumeDTO resumeDTO) {
        final Resume resume = new Resume();
        mapToEntity(resumeDTO, resume);
        return resumeRepository.save(resume).getId();
    }

    public void update(final Long id, final ResumeDTO resumeDTO) {
        final Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(resumeDTO, resume);
        resumeRepository.save(resume);
    }

    public void delete(final Long id) {
        resumeRepository.deleteById(id);
    }

    private ResumeDTO mapToDTO(final Resume resume, final ResumeDTO resumeDTO) {
        resumeDTO.setId(resume.getId());
        resumeDTO.setFileName(resume.getFileName());
        resumeDTO.setContent(resume.getContent());
        resumeDTO.setDownloadLocation(resume.getDownloadLocation());
        return resumeDTO;
    }

    private Resume mapToEntity(final ResumeDTO resumeDTO, final Resume resume) {
        resume.setFileName(resumeDTO.getFileName());
        resume.setContent(resumeDTO.getContent());
        resume.setDownloadLocation(resumeDTO.getDownloadLocation());
        return resume;
    }

}
