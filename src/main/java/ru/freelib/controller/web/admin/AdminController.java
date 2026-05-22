package ru.freelib.controller.web.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String lookupAuthor(@RequestParam String author, Model model) {
        Author found = authorService.findByNickname(author);
        if (found == null) {
            model.addAttribute("errormessage", "Автор не найден!");
            return "admin/panel";
        }
        model.addAttribute("author", found);
        model.addAttribute("myBooks", bookService.findByAuthorId(found.getId()));
        return "admin/author-edit";
    }
}