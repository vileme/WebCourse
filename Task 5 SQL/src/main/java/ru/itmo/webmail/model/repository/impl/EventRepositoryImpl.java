package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.repository.EventRepository;

public class EventRepositoryImpl implements EventRepository {
    @Override
    public void somethingHappened(Event.EventEnum type, User user) {
        DatabaseUtils.MakeRequestToDB("INSERT INTO Event (userId, type, creationTime) VALUES (?, ?, NOW())",
                "Can't write an Event(enter or logout)", Long.toString(user.getId()), type.toString());
    }
}
