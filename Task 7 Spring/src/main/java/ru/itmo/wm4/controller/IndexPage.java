package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itmo.wm4.service.NoticeService;

import javax.servlet.http.HttpSession;

@Controller
public class IndexPage extends Page {

    private final NoticeService noticeService;

    public IndexPage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }
    @GetMapping(path = "")
    public String index(Model model) {
        model.addAttribute("notices",noticeService.findAll());
        return "IndexPage";
    }

    @GetMapping(path = "/logout")
    public String index(HttpSession httpSession) {
        unsetUser(httpSession);
        return "redirect:/";
    }
}
