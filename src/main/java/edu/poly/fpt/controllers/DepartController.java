package edu.poly.fpt.controllers;

import edu.poly.fpt.dtos.DepartDto;
import edu.poly.fpt.models.Depart;
import edu.poly.fpt.services.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("departs")
public class DepartController {

    @Autowired
    private DepartService departService;

    @RequestMapping("/")
    public String add(ModelMap model){
        model.addAttribute("departDto", new DepartDto());
        return "departs/list";
    }

    @PostMapping("saveOrUpdate")
    public String saveOrUpdate(ModelMap model, @Validated DepartDto departDto, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("departDto", departDto);
            return "departs/list";
        }
        Depart depart = new Depart();
        if(departDto.getId() > 0){
            depart.setId(departDto.getId());
        }
        depart.setName(departDto.getName());
        departService.save(depart);
        return "redirect:/departs/";
    }

    @GetMapping("edit/{id}")
    public String edit(ModelMap model, @PathVariable("id")Integer id){
        Optional<Depart> optionalDepart = departService.findById(id);
        if(optionalDepart.isPresent()){
            Depart depart = optionalDepart.get();
            DepartDto departDto = new DepartDto();
            departDto.setId(depart.getId());
            departDto.setName(depart.getName());

            model.addAttribute("departDto", departDto);
            return "departs/list";
        }
        return "redirect:/departs/";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id")Integer id){
        departService.deleteById(id);
        return "redirect:/departs/";
    }


    @PostMapping("find")
    public String find(ModelMap model, @RequestParam(value = "name", defaultValue = "")String name){
        List<Depart> list = departService.findByNameLikeOrderByName(name);
        model.addAttribute("depart", new Depart());
        model.addAttribute("departs", list);
        return "departs/list";
    }

    @ModelAttribute("departs")
    public List<Depart> getDeparts(){
        return departService.findAll();
    }

    @ModelAttribute("departActive")
    public String getActive(){
        return "active";
    }

    @ModelAttribute("attr_user")
    public org.springframework.security.core.userdetails.User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }
}
