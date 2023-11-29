package com.example.demo.Controller;

import com.example.demo.Config.SecurityConfiguration;
import com.example.demo.Domain.User;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private SecurityConfiguration bCryptPasswordEncoder;
    @Autowired
    private UserRepository repo;

    @RequestMapping("/")
    public String viewHomePage() {
        return "index";
    }

    @RequestMapping("/login_page")
    public String viewLoginPage() {
        return "login";
    }

    @RequestMapping("/reset_password")
    public String viewResetPassworddPage() {
        return "reset_password";
    }

    @RequestMapping("/contactus_page")
    public String viewContactUsPage() {
        return "contactus";
    }

    @RequestMapping("/new_account")
    public String viewCreateAccountPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "create_account";
    }

    @RequestMapping(value = "/account_registration", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("user") User user, Model model) {
        try {

            if (service.appUserEmailExists(user.getEmail())) {

                model.addAttribute("message", "Email " + user.getEmail() + " is Already Taken!");
                return "create_account";

            } else
                user.setRoles("ROLE_USER");
            user.setPassword(bCryptPasswordEncoder.getPasswordEncoder().encode(user.getPassword()));

            user.setActive(true);
            service.save(user);

        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "success";

    }

}
