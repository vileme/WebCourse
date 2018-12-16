package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public interface ArticleRepository {
    List<ArticleRepositoryImpl.FrontArticle> findAll();
    void save(User user, Article article);
    List<Article> findByUser(User user);
    void setShowing(Long articleId,boolean state);
    Article find(Long id);
}
