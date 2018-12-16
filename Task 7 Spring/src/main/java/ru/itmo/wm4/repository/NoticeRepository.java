package ru.itmo.wm4.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.domain.User;
public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
