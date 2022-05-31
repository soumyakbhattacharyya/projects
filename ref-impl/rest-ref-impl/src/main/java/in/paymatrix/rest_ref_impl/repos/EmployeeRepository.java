package in.paymatrix.rest_ref_impl.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import in.paymatrix.rest_ref_impl.entity.EmployeeEntity;


public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}
