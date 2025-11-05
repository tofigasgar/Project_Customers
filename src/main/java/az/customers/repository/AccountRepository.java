package az.customers.repository;

import az.customers.model.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Integer> {

    Optional<Accounts> findByIdAndCustomerId(Long id, Long customerId);

    boolean existsByAccountNumber(String accountNumber);
}

