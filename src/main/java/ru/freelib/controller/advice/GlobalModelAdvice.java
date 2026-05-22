package ru.freelib.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.freelib.security.CustomUserDetails;

@ControllerAdvice
public class GlobalModelAdvice {

    @ModelAttribute("currentContext")
    public String currentContext(HttpServletRequest request) {
        return request.getContextPath();
    }

    @ModelAttribute("user")
    public Object currentUser(@AuthenticationPrincipal CustomUserDetails details) {
        if (details == null) return null;
        return new UserView(details);
    }

    public record UserView(
            Long id,
            String login,
            String nickname,
            String role,
            boolean isAdmin,
            boolean isAuthor,
            boolean isReader
    ) {
        public UserView(CustomUserDetails d) {
            this(
                    d.getId(),
                    d.getUsername(),
                    d.getUserAccount().getAuthor() != null
                            ? d.getUserAccount().getAuthor().getNickname()
                            : d.getUsername(),
                    d.getUserAccount().getRole().name(),
                    d.getUserAccount().getRole() == ru.freelib.model.entity.UserAccount.Role.ROLE_ADMIN,
                    d.getUserAccount().getRole() == ru.freelib.model.entity.UserAccount.Role.ROLE_AUTHOR
                            || d.getUserAccount().getRole() == ru.freelib.model.entity.UserAccount.Role.ROLE_ADMIN,
                    d.getUserAccount().getRole() == ru.freelib.model.entity.UserAccount.Role.ROLE_READER
            );
        }
    }
}