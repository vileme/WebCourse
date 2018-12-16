package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LogoutPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {


        Long userId = (Long) request.getSession().getAttribute(USER_ID_SESSION_KEY);


        request.getSession().removeAttribute(USER_ID_SESSION_KEY);


        getEventService().somethingHappened(Event.EventEnum.Logout, getUserService().find(userId));

        throw new RedirectException("/index");
    }
}
