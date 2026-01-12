package sumdu.edu.ua.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sumdu.edu.ua.core.domain.Book;
import sumdu.edu.ua.core.domain.PageRequest;
import sumdu.edu.ua.core.port.CatalogRepositoryPort;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final CatalogRepositoryPort bookRepo;

    public BooksController(CatalogRepositoryPort bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = bookRepo.search(null, new PageRequest(0, 20)).getItems();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("books/{id}")
    public String getBook(@PathVariable long id, Model model) {
        Book book = bookRepo.findById(id);
        model.addAttribute("book", book);
        return "book-comments";
    }

    @PostMapping
    public String addBook(
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam int pubYear,
            RedirectAttributes redirectAttributes
    ) {
        if (title == null || title.isBlank() ||
                author == null || author.isBlank() ||
                pubYear <= 0 || pubYear > 9999) {
            redirectAttributes.addFlashAttribute("error", "title & author & pubYear required");
            return "redirect:/books";
        }

        try {
            bookRepo.add(title, author, pubYear);
            return "redirect:/books";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot add book: " + e.getMessage());
            return "redirect:/books";
        }
    }
}
