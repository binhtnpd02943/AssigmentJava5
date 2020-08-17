package edu.poly.fpt.controllers;

import edu.poly.fpt.dtos.StaffDto;
import edu.poly.fpt.models.Depart;
import edu.poly.fpt.models.PagerModel;
import edu.poly.fpt.models.Staff;
import edu.poly.fpt.services.StaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("staffs")
public class StaffController {

    @Autowired
    private StaffService staffService;

    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 12;

    @GetMapping("/")
    public ModelAndView homepage(@RequestParam("page") Optional<Integer> page){
        return getStaffsAndPage(new StaffDto(), page);
    }


    @PostMapping("saveOrUpdate")
    public String saveOrUpdate(ModelMap model,@Validated StaffDto staffDto,
                               BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("message", "Please input all required fields");
//            model.addAttribute("staffDto", staffDto);

        }

        Path path = Paths.get("images/");
        try (InputStream inputStream = staffDto.getImage().getInputStream()){
            Files.copy(inputStream, path.resolve(staffDto.getImage().getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        Staff staff = new Staff();
        Depart depart = new Depart();
        depart.setId(staffDto.getDepartId());
        if(staffDto.getId() > 0){
            staff.setId(staffDto.getId());
        }

        staff.setName(staffDto.getName());
        staff.setBirthday(staffDto.getBirthday());
        staff.setEmail(staffDto.getEmail());
        staff.setGender(staffDto.getGender());
        staff.setPhone(staffDto.getPhone());
        staff.setNotes(staffDto.getNotes());
        staff.setSalary(staffDto.getSalary());
        staff.setAddress(staffDto.getAddress());
        staff.setAbout(staffDto.getAbout());
        staff.setDepart(depart);

        if(staffDto.getImage().isEmpty()){
            Staff oldStaff = staffService.findById(staffDto.getId()).get();
            staff.setPhoto(oldStaff.getPhoto());
        }else{
            staff.setPhoto(staffDto.getImage().getOriginalFilename());
        }

        staffService.save(staff);
        model.addAttribute("staffDto", staffDto);
        return "redirect:/staffs/";
    }

    @GetMapping("edit/{id}")
    public ModelAndView edit(@PathVariable("id")long id, @RequestParam("page") Optional<Integer> page){
        Staff staff = staffService.findById(id).get();
        StaffDto staffDto = new StaffDto();

        staffDto.setId(staff.getId());
        staffDto.setName(staff.getName());
        staffDto.setBirthday(staff.getBirthday());
        staffDto.setEmail(staff.getEmail());
        staffDto.setGender(staff.getGender());
        staffDto.setPhone(staff.getPhone());
        staffDto.setNotes(staff.getNotes());
        staffDto.setSalary(staff.getSalary());
        staffDto.setAddress(staff.getAddress());
        staffDto.setAbout(staff.getAbout());
        staffDto.setDepartId(staff.getDepart().getId());
        staffDto.setImageName(staff.getPhoto());

        return getStaffsAndPage(staffDto, page);
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id")long id){
        if(staffService.findById(id).isPresent()){
            staffService.deleteById(id);
        }
        return "redirect:/staffs/";
    }

    @GetMapping("profile/{id}")
    public String profile(@PathVariable("id")Long id, ModelMap model){
        if(staffService.findById(id).isPresent()){
            model.addAttribute("staff", staffService.findById(id).get());
            model.addAttribute("records", staffService.getRecordsByStaffIdOrderByDate(id));
            model.addAttribute("countRecord", staffService.getCountTypeStaff(id));
            return "staffs/profile";
        }
        return "redirect:staffs/";
    }

    @PostMapping("profile")
    public String profile(ModelMap model, @RequestParam("id")Long id){
        if(staffService.findById(id).isPresent()){
            model.addAttribute("staff", staffService.findById(id).get());
            model.addAttribute("records", staffService.getRecordsByStaffIdOrderByDate(id));
            model.addAttribute("countRecord", staffService.getCountTypeStaff(id));
            return "staffs/profile";
        }
        return "redirect:staffs/";
    }

    @ModelAttribute("departs")
    public List<Depart> getDeparts(){
        return staffService.findAllDeparts();
    }

    @ModelAttribute("staffActive")
    public String getActive(){
        return "active";
    }

    public ModelAndView getStaffsAndPage(StaffDto staffDto, @RequestParam("page") Optional<Integer> page){
        ModelAndView modelAndView = new ModelAndView("staffs/list");
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        Page<Staff> staffList = staffService.findAll(PageRequest.of(evalPage, INITIAL_PAGE_SIZE, Sort.by("id")));
        PagerModel pager = new PagerModel(staffList.getTotalPages(),staffList.getNumber(),BUTTONS_TO_SHOW);
        modelAndView.addObject("staffDto", staffDto);
        modelAndView.addObject("staffs",staffList);
        modelAndView.addObject("selectedPageSize", INITIAL_PAGE_SIZE);
        modelAndView.addObject("pager", pager);
        return modelAndView;
    }

    @ModelAttribute("attr_user")
    public org.springframework.security.core.userdetails.User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return (User) auth.getPrincipal();
        }
        return null;
    }
}
