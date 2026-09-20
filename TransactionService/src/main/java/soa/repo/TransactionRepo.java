package soa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import soa.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

}