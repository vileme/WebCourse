package ru.itmo.wm4.service;


import java.util.List;
import org.springframework.stereotype.Service;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.form.NoticeCredentials;
import ru.itmo.wm4.repository.NoticeRepository;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService (NoticeRepository noticeRepository){
        this.noticeRepository = noticeRepository;
    }


    public Notice addNotice(NoticeCredentials registerForm) {
        Notice notice = new Notice();
        notice.setContent(registerForm.getText());
        noticeRepository.save(notice);
        return notice;
    }

    public List<Notice> findAll(){
        return noticeRepository.findAll();
    }
}
