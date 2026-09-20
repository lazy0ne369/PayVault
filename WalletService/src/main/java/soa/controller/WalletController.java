package soa.controller;

import org.springframework.web.bind.annotation.*;

import soa.model.Wallet;
import soa.service.WalletService;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    // Create wallet
    @PostMapping("/create/{userId}")
    public Wallet createWallet(@PathVariable Long userId) {
        return walletService.createWallet(userId);
    }

    // Get wallet
    @GetMapping("/{userId}")
    public Wallet getWallet(@PathVariable Long userId) {
        return walletService.getWallet(userId);
    }

    // Add money
    @PostMapping("/add/{userId}")
    public Wallet addMoney(
            @PathVariable Long userId,
            @RequestParam Double amount) {

        return walletService.addMoney(userId, amount);
    }

    // Debit money
    @PostMapping("/debit/{userId}")
    public Wallet debitWallet(
            @PathVariable Long userId,
            @RequestParam Double amount) {

        return walletService.debit(userId, amount);
    }
}