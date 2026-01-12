package sumdu.edu.ua.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sumdu.edu.ua.core.domain.Book;
import sumdu.edu.ua.core.domain.PageRequest;
import sumdu.edu.ua.core.port.CatalogRepositoryPort;

import java.io.IOException;
import java.util.List;

public class BooksServlet extends HttpServlet {

    private final CatalogRepositoryPort bookRepo;

    public BooksServlet(CatalogRepositoryPort bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageRequest pageRequest = new PageRequest(0, 20);
        List<Book> books = bookRepo.search(null, pageRequest).getItems();

        req.setAttribute("books", books);
        req.getRequestDispatcher("/WEB-INF/views/books.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");

        String title = req.getParameter("title");
        String author = req.getParameter("author");
        int pubYear = Integer.parseInt(req.getParameter("pubYear"));

        if (title == null || title.isBlank() || author == null || author.isBlank() || pubYear <= 0 || pubYear > 9999) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "title & author & pubYear required");
            return;
        }

        try {
            bookRepo.add(title, author, pubYear);
            resp.sendRedirect(req.getContextPath() + "/books");
        } catch (Exception e) {
            throw new ServletException("Cannot add book", e);
        }
    }
}
