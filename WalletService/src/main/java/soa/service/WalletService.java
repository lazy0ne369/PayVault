package soa.service;

import org.springframework.stereotype.Service;

import soa.model.Wallet;
import soa.repo.WalletRepo;

@Service
public class WalletService {

    private final WalletRepo walletRepo;

    public WalletService(WalletRepo walletRepo) {
        this.walletRepo = walletRepo;
    }

    // Create wallet
    public Wallet createWallet(Long userId) {

        Wallet existingWallet = walletRepo.findByUserId(userId);

        if (existingWallet != null) {
            return existingWallet;
        }

        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setBalance(0.0);

        return walletRepo.save(wallet);
    }

    // Get wallet
    public Wallet getWallet(Long userId) {

        Wallet wallet = walletRepo.findByUserId(userId);

        if (wallet == null) {
            throw new RuntimeException("Wallet not found");
        }

        return wallet;
    }

    // Add money
    public Wallet addMoney(Long userId, Double amount) {

        Wallet wallet = walletRepo.findByUserId(userId);

        if (wallet == null) {
            throw new RuntimeException("Wallet not found");
        }

        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        wallet.setBalance(wallet.getBalance() + amount);

        return walletRepo.save(wallet);
    }

    // Debit money
    public Wallet debit(Long userId, Double amount) {

        Wallet wallet = walletRepo.findByUserId(userId);

        if (wallet == null) {
            throw new RuntimeException("Wallet not found");
        }

        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance() - amount);

        return walletRepo.save(wallet);
    }
}