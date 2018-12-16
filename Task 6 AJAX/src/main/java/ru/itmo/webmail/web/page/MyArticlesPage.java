package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage extends Page {
    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (getUser() == null) {
            throw new RedirectException("/index");
        }
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) view.get("user");
        view.put("articles", getArticleService().findArticleListByUser(user));
    }

    private Map<String, Object> changeHidden(HttpServletRequest request, Map<String, Object> view) {
        String clicked = request.getParameter("clickedButton");
        Long articleId = Long.parseLong(request.getParameter("articleId"));

        try {
            getArticleService().validateChanging(articleId, ((User) view.get("user")).getId());
        } catch (ValidationException e) {
            view.put("success", false);
            view.put("error", e.getMessage());
            return view;
        }
        getArticleService().setShowing(articleId, clicked.equals("Show"));
        view.put("success", true);
        return view;
    }
}