package az.customers.repository;

import az.customers.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByIdAndActiveTrue(Long id);

    boolean existsByFin(String fin);

    boolean existsByPassportSerial(String serial);

    Page<Customer> findByActiveTrue(Pageable pageable);

    Optional<Customer> findByFin(String fin);

}
