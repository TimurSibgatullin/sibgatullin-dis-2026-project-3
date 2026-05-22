package ru.freelib.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.freelib.exception.AiServiceException;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handle404(NoHandlerFoundException ex, HttpServletRequest req) {
        if (isAjax(req)) {
            return ResponseEntity.status(404).body(Map.of("error", "Endpoint not found"));
        }
        return errorView("404", "Страница не найдена", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        log.warn("Validation failed: {}", ex.getMessage());
        if (isAjax(req)) {
            Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a));
            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }
        return errorView("400", "Ошибка валидации данных", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AiServiceException.class)
    public Object handleAi(AiServiceException ex, HttpServletRequest req) {
        log.error("AI error: {}", ex.getMessage());
        if (isAjax(req)) {
            return ResponseEntity.status(502).body(Map.of("error", "Сервис генерации временно недоступен"));
        }
        return errorView("502", "Сервис генерации временно недоступен", HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
        log.warn("Bad request: {}", ex.getMessage());
        if (isAjax(req)) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
        return errorView("400", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public Object handleGeneral(Exception ex, HttpServletRequest req) {
        log.error("Unhandled exception", ex);
        if (isAjax(req)) {
            return ResponseEntity.status(500).body(Map.of("error", "Внутренняя ошибка сервера"));
        }
        return errorView("500", "Произошла непредвиденная ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ModelAndView errorView(String view, String message, HttpStatus status) {
        ModelAndView mav = new ModelAndView("error/" + view);
        mav.addObject("errorMessage", message);
        mav.addObject("statusCode", status.value());
        mav.addObject("currentContext", ""); // Добавляем вручную для error-страниц
        mav.setStatus(status);
        return mav;
    }

    private boolean isAjax(HttpServletRequest req) {
        String xhr = req.getHeader("X-Requested-With");
        String accept = req.getHeader("Accept");
        // Считаем AJAX только если явно просят JSON и НЕ просят HTML
        return "XMLHttpRequest".equals(xhr) ||
                (accept != null && accept.contains("application/json") && !accept.contains("text/html"));
    }
}