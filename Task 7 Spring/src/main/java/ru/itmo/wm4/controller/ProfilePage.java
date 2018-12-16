package ru.itmo.wm4.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmo.wm4.service.NoticeService;
import ru.itmo.wm4.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class ProfilePage extends Page {
    private final UserService userService;
    private final NoticeService noticeService;

    public ProfilePage(UserService userService,NoticeService noticeService){
        this.userService = userService;
        this.noticeService = noticeService;
    }
    @GetMapping(path = "/user/{id}")
    public String ShowInfo(Model model,@PathVariable long id){
        model.addAttribute("userInfo",userService.findById(id));
        model.addAttribute("notices",noticeService.findAll());
        return "ProfilePage";
    }


}
