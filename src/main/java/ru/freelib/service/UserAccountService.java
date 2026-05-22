package ru.freelib.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.freelib.model.entity.UserAccount;
import ru.freelib.model.form.ProfileEditForm;
import ru.freelib.repository.UserAccountRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccount getById(Long id) {
        return userAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Аккаунт не найден"));
    }

    @Transactional
    public UserAccount updateProfile(Long id, ProfileEditForm form) {
        UserAccount account = getById(id);
        account.getAuthor().setNickname(form.getNickname().trim());
        account.getAuthor().setBio(form.getDescription() != null ? form.getDescription().trim() : null);
        return userAccountRepository.save(account);
    }

    @Transactional
    public void changePassword(Long id, String oldPassword, String newPassword) {
        UserAccount account = getById(id);
        if (!passwordEncoder.matches(oldPassword, account.getPasswordHash())) {
            throw new IllegalArgumentException("Текущий пароль неверен");
        }
        account.setPasswordHash(passwordEncoder.encode(newPassword));
        userAccountRepository.save(account);
    }

    @Transactional
    public void deleteAccount(Long id) {
        if (!userAccountRepository.existsById(id)) {
            throw new EntityNotFoundException("Аккаунт не найден для удаления");
        }
        userAccountRepository.deleteById(id);
    }
}