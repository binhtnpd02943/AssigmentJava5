package edu.poly.fpt.controllers;

import edu.poly.fpt.dtos.UserDto;
import edu.poly.fpt.models.Depart;
import edu.poly.fpt.models.User;
import edu.poly.fpt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("users")
@Transactional
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String list(ModelMap model){
        model.addAttribute("userDto", new UserDto());

        return "users/list";
    }

    @PostMapping("saveOrUpdate")
    public String saveOrUpdate(ModelMap model, @Validated UserDto userDto, BindingResult result){
        if(userService.existsById(userDto.getUsername())){
            model.addAttribute("edit", true);
        }
        if(result.hasErrors()){
            model.addAttribute("userDto", userDto);
            return "/users/list";
        }
        if(!userDto.getPassword().equals(userDto.getRePassword())){
            model.addAttribute("userDto", userDto);

            model.addAttribute("err", true);
            return "/users/list";
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFullname(userDto.getFullname());
        userService.save(user);
        return "redirect:/users/";
    }

    @GetMapping("edit/{username}")
    public String edit(@PathVariable("username")String username, ModelMap model){
        if(userService.existsById(username)){
            User user = userService.findById(username).get();
            UserDto userDto = new UserDto();
            userDto.setUsername(user.getUsername());
            userDto.setFullname(user.getFullname());
            userDto.setPassword("");
            userDto.setRePassword("");
            model.addAttribute("userDto", userDto);
            model.addAttribute("edit", true);
            return "users/list";
        }
        return "redirect:/users/";
    }

    @GetMapping("delete/{username}")
    public String delete(@PathVariable("username")String username){
        userService.deleteById(username);
        return "redirect:/users/";
    }

    @PostMapping("find")
    public String find(@RequestParam(value = "username", defaultValue = "")String username, ModelMap model){
        List<User> list = userService.findByUsernameLikeOrderByUsername(username);
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("users", list);
        return "users/list";
    }

    @ModelAttribute("users")
    public List<User> getUsers(){
        return userService.findAll();
    }

    @ModelAttribute("userActive")
    public String getActiver(){
        return "active";
    }

    @ModelAttribute("attr_user")
    public org.springframework.security.core.userdetails.User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (org.springframework.security.core.userdetails.User) auth.getPrincipal();
    }
}
