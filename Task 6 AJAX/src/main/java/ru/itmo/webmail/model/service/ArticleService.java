package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.ArticleRepository;
import ru.itmo.webmail.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validateArticle(String title, String text) throws ValidationException {

        if (title == null || title.isEmpty()) {
            throw new ValidationException("Title is required");
        }
        if (title.length() > 18) {
            throw new ValidationException("Title can't be longer than 18");
        }
        if (text == null|| text.isEmpty()) {
            throw new ValidationException("Text is required");
        }
        if(text.length()>100000){
            throw new ValidationException("Text can't be longer than 100000");
        }
    }
    public void addArticle(User user, Article article) {
        articleRepository.save(user, article);
    }

    public List<ArticleRepositoryImpl.FrontArticle> findAllShowed(){
        return articleRepository.findAll();
    }
    public List<Article> findArticleListByUser(User user){
        return articleRepository.findByUser(user);
    }

    public void setShowing(Long articleId, boolean state) {
        articleRepository.setShowing(articleId, state);
    }

    public void validateChanging(Long articleId, Long userId) throws ValidationException {
        if (articleRepository.find(articleId) == null) {
            throw new ValidationException("Article doesn't exist");
        }

        if (!articleRepository.find(articleId).getUserid().equals(userId)) {
            throw new ValidationException("You are not the author");
        }
    }

}

