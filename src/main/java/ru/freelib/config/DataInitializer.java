package ru.freelib.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.freelib.model.entity.Author;
import ru.freelib.model.entity.UserAccount;
import ru.freelib.repository.AuthorRepository;
import ru.freelib.repository.UserAccountRepository;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserAccountRepository userAccountRepository;
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_PASSWORD = "HulTi55iYB}g+yXU";
    private static final String ADMIN_NICKNAME = "admin";
    private static final String ADMIN_BIO = "best admin";

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (userAccountRepository.existsByLogin(ADMIN_LOGIN)) {
            log.info("Админ уже есть, скипаем", ADMIN_LOGIN);
            return;
        }

        Author adminAuthor = Author.builder()
                .nickname(ADMIN_NICKNAME)
                .bio(ADMIN_BIO)
                .createdAt(LocalDateTime.now())
                .build();
        adminAuthor = authorRepository.save(adminAuthor);

        UserAccount admin = UserAccount.builder()
                .login(ADMIN_LOGIN)
                .passwordHash(passwordEncoder.encode(ADMIN_PASSWORD))
                .role(UserAccount.Role.ROLE_ADMIN)
                .author(adminAuthor)
                .createdAt(LocalDateTime.now())
                .build();
        userAccountRepository.save(admin);

        log.info("Админ успешно создан");
    }
}