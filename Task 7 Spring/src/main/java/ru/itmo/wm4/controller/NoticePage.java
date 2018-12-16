package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wm4.form.NoticeCredentials;
import ru.itmo.wm4.form.validator.NoticeCredentialsTextValidator;
import ru.itmo.wm4.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {

    private final NoticeCredentialsTextValidator noticeCredentialsTextValidator;
    private final NoticeService noticeService;

    public NoticePage(NoticeService noticeService, NoticeCredentialsTextValidator noticeCredentialsTextValidator){
        this.noticeCredentialsTextValidator = noticeCredentialsTextValidator;
        this.noticeService = noticeService;
    }
    @InitBinder("NoticeCredentials")
    public void initRegisterFormBinder(WebDataBinder binder) {
        binder.setValidator(noticeCredentialsTextValidator);
    }

    @GetMapping(path = "/notice")
    public String noticeGet(Model model){
        model.addAttribute("noticeForm",new NoticeCredentials());
        model.addAttribute("notices",noticeService.findAll());
        return "NoticePage";
    }
    @PostMapping(path = "/notice")
    public String noticePost(@Valid @ModelAttribute("noticeForm") NoticeCredentials addNoticeForm, BindingResult bindingResult, HttpSession httpSession){
        if(bindingResult.hasErrors()){
            return "NoticePage";
        }
        noticeService.addNotice(addNoticeForm);
        return "redirect:/";
    }
}
