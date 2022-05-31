package in.paymatrix.rest_ref_impl.service;

import in.paymatrix.rest_ref_impl.entity.EmployeeEntity;
import in.paymatrix.rest_ref_impl.entity.SkillEntity;
import in.paymatrix.rest_ref_impl.model.Skill;
import in.paymatrix.rest_ref_impl.repos.EmployeeRepository;
import in.paymatrix.rest_ref_impl.repos.SkillRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final EmployeeRepository employeeRepository;

    public SkillService(final SkillRepository skillRepository,
            final EmployeeRepository employeeRepository) {
        this.skillRepository = skillRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Skill> findAll() {
        return skillRepository.findAll()
                .stream()
                .map(skill -> mapToDTO(skill, new Skill()))
                .collect(Collectors.toList());
    }

    public Skill get(final Long id) {
        return skillRepository.findById(id)
                .map(skill -> mapToDTO(skill, new Skill()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final Skill skill) {
        final SkillEntity skillEntity = new SkillEntity();
        mapToEntity(skill, skillEntity);
        return skillRepository.save(skillEntity).getId();
    }

    public void update(final Long id, final Skill skill) {
        final SkillEntity skillEntity = skillRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(skill, skillEntity);
        skillRepository.save(skillEntity);
    }

    public void delete(final Long id) {
        skillRepository.deleteById(id);
    }

    private Skill mapToDTO(final SkillEntity skillEntity, final Skill skill) {
        skill.setId(skillEntity.getId());
        skill.setDomain(skillEntity.getDomain());
        skill.setProgrammingLanguage(skillEntity.getProgrammingLanguage());
        skill.setFunctional(skillEntity.getFunctional());
        skill.setEmployee(skillEntity.getEmployee() == null ? null : skillEntity.getEmployee().getId());
        return skill;
    }

    private SkillEntity mapToEntity(final Skill skill, final SkillEntity skillEntity) {
        skillEntity.setDomain(skill.getDomain());
        skillEntity.setProgrammingLanguage(skill.getProgrammingLanguage());
        skillEntity.setFunctional(skill.getFunctional());
        if (skill.getEmployee() != null && (skillEntity.getEmployee() == null || !skillEntity.getEmployee().getId().equals(skill.getEmployee()))) {
            final EmployeeEntity employee = employeeRepository.findById(skill.getEmployee())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "employee not found"));
            skillEntity.setEmployee(employee);
        }
        return skillEntity;
    }

}
