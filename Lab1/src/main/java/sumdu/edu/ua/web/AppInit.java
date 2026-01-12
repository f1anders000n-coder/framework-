package sumdu.edu.ua.web;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import sumdu.edu.ua.db.CommentDao;

public class AppInit implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent e) {
        try { new CommentDao().init(); }
        catch (Exception ex) { throw new RuntimeException(ex); }
    }
}
