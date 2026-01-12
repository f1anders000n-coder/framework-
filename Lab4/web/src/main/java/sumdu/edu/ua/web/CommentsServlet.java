package sumdu.edu.ua.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sumdu.edu.ua.core.domain.Comment;
import sumdu.edu.ua.core.domain.PageRequest;
import sumdu.edu.ua.core.port.CatalogRepositoryPort;
import sumdu.edu.ua.core.port.CommentRepositoryPort;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentsServlet {
    private final CatalogRepositoryPort bookRepo;
    private final CommentRepositoryPort commentRepo;

    public CommentsServlet(CatalogRepositoryPort bookRepo, CommentRepositoryPort commentRepo) {
        this.bookRepo = bookRepo;
        this.commentRepo = commentRepo;
    }

    @GetMapping
    public String list(@RequestParam long bookId, Model model) {
        var book = bookRepo.findById(bookId);
        List<Comment> comments = commentRepo.list(bookId, null, null, new PageRequest(0, 20)).getItems();

        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        return "book-comments";
    }

    @PostMapping
    public String add(@RequestParam long bookId,
                      @RequestParam String author,
                      @RequestParam String text) {
        if (!author.isBlank() && !text.isBlank()) {
            commentRepo.add(bookId, author.trim(), text.trim());
        }

        return "redirect:/comments?bookId=" + bookId;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long bookId,
                         @RequestParam long commentId) {
        commentRepo.delete(bookId, commentId);
        return "redirect:/comments?bookId=" + bookId;
    }
}
