package edu.poly.fpt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("contacts")
public class ContactController {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/")
    public String home(){
        return "contacts/list";
    }

    @PostMapping("sendMail")
    public String send(ModelMap model,
                       @RequestParam("from")String from,
                       @RequestParam("to")String to,
                       @RequestParam("subject")String subject,
                       @RequestParam("body")String body){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage);
        return "redirect:/contacts/";
    }

    @ModelAttribute("contactActive")
    public boolean getActive(){
        return true;
    }
}
