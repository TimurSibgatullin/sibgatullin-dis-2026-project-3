package ru.freelib.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.freelib.security.CustomUserDetails;
import ru.freelib.service.BookService;
import ru.freelib.service.CommentService;
import ru.freelib.service.FavoriteService;
import ru.freelib.service.GenreService;
import ru.freelib.util.FileStorageUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final GenreService genreService;
    private final BookService bookService;
    private final CommentService commentService;
    private final FavoriteService favoriteService;
    private final FileStorageUtil fileStorage;

    @GetMapping("/book")
    public String viewBook(@RequestParam Long id,
                           Model model,
                           @AuthenticationPrincipal CustomUserDetails user) {
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("comments", commentService.getByBookId(id));
        if (user != null) {
            model.addAttribute("isFavorite", favoriteService.exists(user.getId(), id));
        }
        return "book";
    }

    @GetMapping("/books")
    public String booksByGenre(@RequestParam Long genreId, Model model) {
        model.addAttribute("genre", genreService.getById(genreId));
        model.addAttribute("books", bookService.getByGenreId(genreId));
        return "books";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadBook(@RequestParam Long id) {
        var book = bookService.getById(id);
        Path file = fileStorage.getUploadDir().resolve(book.getFilePath());
        Resource resource = new FileSystemResource(file);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        String encodedName = URLEncoder.encode(
                book.getTitle() + book.getFilePath().substring(book.getFilePath().lastIndexOf('.')),
                StandardCharsets.UTF_8).replace("+", "%20");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedName + "\"")
                .body(resource);
    }
}