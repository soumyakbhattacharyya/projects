package in.paymatrix.rest_ref_impl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import in.paymatrix.rest_ref_impl.config.ExceptionFactory;
import in.paymatrix.rest_ref_impl.entity.EmployeeEntity;
import in.paymatrix.rest_ref_impl.model.Employee;
import in.paymatrix.rest_ref_impl.repos.EmployeeRepository;


@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employee -> mapToDTO(employee, new Employee()))
                .collect(Collectors.toList());
    }

    public Employee get(final Long id) {
    	
    	Map<String, Object> contextParam = new HashMap<>();
    	contextParam.put("employeeId", id);
    	
        return employeeRepository.findById(id)
                .map(employee -> mapToDTO(employee, new Employee())).orElseThrow(() -> ExceptionFactory.createOne("1000", contextParam));
                
    }

    public Long create(final Employee employee) {
        final EmployeeEntity entity = new EmployeeEntity();
        mapToEntity(employee, entity);
        return employeeRepository.save(entity).getId();
    }

    public void update(final Long id, final Employee employee) {
        final EmployeeEntity entity = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(employee, entity);
        employeeRepository.save(entity);
    }

    public void delete(final Long id) {
        employeeRepository.deleteById(id);
    }

    private Employee mapToDTO(final EmployeeEntity entity, final Employee employee) {
        employee.setId(entity.getId());
        employee.setFirstName(entity.getFirstName());
        employee.setLastName(entity.getLastName());
        employee.setDesignation(entity.getDesignation());
        employee.setAge(entity.getAge());
        return employee;
    }

    private EmployeeEntity mapToEntity(final Employee employee, final EmployeeEntity entity) {
    	entity.setFirstName(employee.getFirstName());
    	entity.setLastName(employee.getLastName());
    	entity.setDesignation(employee.getDesignation());
    	entity.setAge(employee.getAge());
        return entity;
    }

}
