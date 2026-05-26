package ru.freelib.controller.web.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.freelib.model.entity.Author;
import ru.freelib.service.AuthorService;
import ru.freelib.service.BookService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthorService authorService;
    private final BookService bookService;

    @GetMapping
    public String panel() {
        return "admin/panel";
    }

    @PostMapping
    public String lookupAuthor(@RequestParam Author author, RedirectAttributes redirectAttributes) {
        return "redirect:/admin/edit-author?id=" + author.getId();
    }
}