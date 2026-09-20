package soa.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import soa.model.Wallet;

public interface WalletRepo extends JpaRepository<Wallet, Long> {

    Wallet findByUserId(Long userId);
}