package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage extends Page {
    private Map<String,Object> article(HttpServletRequest request, Map<String, Object> view) {
        Article article = new Article();
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        article.setText(text);
        article.setTitle(title);

        User user = getUser();
        try {
            getArticleService().validateArticle(title, text);

        } catch (ValidationException e) {
            view.put("success", false);
            view.put("error", e.getMessage());
            return view;
        }
        getArticleService().addArticle(user, article);
        view.put("success", true);
        return view;
    }


    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
