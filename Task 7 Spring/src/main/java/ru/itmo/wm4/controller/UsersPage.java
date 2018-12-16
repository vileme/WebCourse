package ru.itmo.wm4.controller;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import ru.itmo.wm4.domain.User;
import ru.itmo.wm4.service.NoticeService;
import ru.itmo.wm4.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UsersPage extends Page {
    private final UserService userService;
    private final NoticeService noticeService;

    public UsersPage(UserService userService,NoticeService noticeService) {
        this.userService = userService;
        this.noticeService = noticeService;
    }

    @GetMapping(path = "/users")
    public String main(Model model) {
        model.addAttribute("notices",noticeService.findAll());
        model.addAttribute("users", userService.findAll());

        return "UsersPage";
    }
    @PostMapping(path = "/users")
    public String mainPost(@RequestParam(value="userLogin") String login){
        userService.updateDisabled(login);
        return "redirect:/users";
    }
}
