package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.ArticleRepository;
import ru.itmo.webmail.model.service.UserService;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleRepositoryImpl implements ArticleRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();
    private static final UserService userService = new UserService();
    @Override
    public List<FrontArticle> findAll() {
        List<FrontArticle> returnArticles = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Article WHERE hidden = 0 ORDER BY creationTime DESC")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        returnArticles.add(new FrontArticle(toArticle(statement.getMetaData(), resultSet)));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find all articles.", e);
        }
        return returnArticles ;
    }
    private Article toArticle(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("userId".equalsIgnoreCase(columnName)) {
                article.setUserid(resultSet.getLong(i));
            } else if ("title".equalsIgnoreCase(columnName)) {
                article.setTitle(resultSet.getString(i));
            } else if ("text".equalsIgnoreCase(columnName)){
                article.setText(resultSet.getString(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                article.setCreationTime(resultSet.getTimestamp(i));
            } else if("hidden".equalsIgnoreCase(columnName)){
                article.setHidden(resultSet.getBoolean(i));
            } else if("id".equalsIgnoreCase(columnName)){
                article.setId(resultSet.getLong(i));
            }
            else {
                throw new RepositoryException("Unexpected column 'Article." + columnName + "'.");
            }
        }
        return article;
    }
    @Override
    public void save(User user, Article article) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Article (userId, title,text,creationTime,hidden) VALUES (?, ?, ?, NOW(),?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, user.getId());
                statement.setString(2, article.getTitle());
                statement.setString(3,article.getText());
                statement.setBoolean(4,false);
                if(statement.executeUpdate() !=1){
                    throw new RepositoryException("Can't save User.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save User.", e);
        }
    }

    @Override
    public List<Article> findByUser(User user){
        List<Article> result = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Article WHERE userId=? ORDER BY CreationTime DESC",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, Long.toString(user.getId()));
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        result.add(toArticle(statement.getMetaData(), resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find user's articles.", e);
        }
        return result;
    }
    @Override
    public void setShowing(Long articleId, boolean state) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Article SET hidden=? WHERE id=?",
                    Statement.RETURN_GENERATED_KEYS)) {
                if (state) statement.setString(1, Integer.toString(0));
                else statement.setString(1, Integer.toString(1));
                statement.setString(2, Long.toString(articleId));
                statement.execute();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't set showing.", e);
        }
    }
    @Override
    public Article find(Long id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Article WHERE id=?",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, Long.toString(id));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toArticle(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't connect to find article.", e);
        }
    }


    public static class FrontArticle {
        private String text;
        private String title;
        private String userName;
        private Date creationTime;

        FrontArticle(Article article) {
            this.text = article.getText();
            this.title = article.getTitle();
            this.creationTime =  article.getCreationTime();
            this.userName = userService.find(article.getUserid()).getLogin();
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Date getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(Date creationTime) {
            this.creationTime = creationTime;
        }
    }
}

