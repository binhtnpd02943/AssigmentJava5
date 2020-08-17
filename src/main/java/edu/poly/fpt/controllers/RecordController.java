package edu.poly.fpt.controllers;

import edu.poly.fpt.models.Record;
import edu.poly.fpt.models.Staff;
import edu.poly.fpt.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("records")
@Transactional
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/")
    public String list(ModelMap model){
        model.addAttribute("record", new Record());
        return "records/list";
    }

    @PostMapping("save")
    public String save(Record record){
        record.setDate(new Date());
        recordService.save(record);
        return "redirect:/records/";

    }

    @ModelAttribute("staffs")
    public List<Staff> getStaffs(){
        return recordService.findAllStaffs();
    }

    @ModelAttribute("resultStaffs")
    public List<Object[]> getResultStaffs(){
        return recordService.getListResultStaffs();
    }

    @ModelAttribute("resultDeparts")
    public List<Object[]> getResultDeparts(){
        return recordService.getListResultDeparts();
    }

    @ModelAttribute("recordActive")
    public String getActive(){
        return "active";
    }

    @ModelAttribute("attr_user")
    public org.springframework.security.core.userdetails.User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }
}
