package com.example.demo.Controller;

import com.example.demo.Config.EmailSenderServiceConfig;
import com.example.demo.Domain.Ubudehe;
import com.example.demo.Domain.User;
import com.example.demo.Repositories.UbudeheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/budded")
public class UbudeheController {
    @Autowired
    private EmailSenderServiceConfig sendEmail;
    private final UbudeheRepository repo;

    @Autowired
    public UbudeheController(UbudeheRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/add")
    public String showRegisterForm(Ubudehe ubudehe) {
        return "Dashboard/add-citizen";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("viewCitizens", repo.findAll());
        return "Dashboard/viewCitizens";
    }

    @PostMapping("/register")
    public String addCitizen(@Valid Ubudehe ubudehe, BindingResult result, Model model,
            RedirectAttributes redirectAttributes, Principal principal, @AuthenticationPrincipal User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            if (result.hasErrors()) {
                return "Dashboard/add-citizen";
            }

            repo.save(ubudehe);
            sendEmail.sendCitizenEmail(ubudehe.getEmail(), "Citizen Registration",
                    ubudehe.getFirstName() + " " + ubudehe.getLastName(), ubudehe.getCategory());

            redirectAttributes.addFlashAttribute("message", "Information saved successfully!");

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/list";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Ubudehe ubudehe = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
        model.addAttribute("budded", ubudehe);
        return "Dashboard/edit-citizen";
    }

    @PostMapping("/update/{id}")
    public String updateCitizen(@PathVariable("id") long id, @Valid Ubudehe ubudehe, BindingResult result,
            Model model, RedirectAttributes redirectAttributes, Principal principal) {

        try {
            if (result.hasErrors()) {
                ubudehe.setId(id);
                return "Dashboard/edit-citizen";
            }
            repo.save(ubudehe);
            sendEmail.sendCitizenEmail(ubudehe.getAddress(), "Citizen Category Change",
                    ubudehe.getFirstName() + " " + ubudehe.getLastName(), ubudehe.getCategory());
            model.addAttribute("viewCitizens", repo.findAll());
            redirectAttributes.addFlashAttribute("message", "Information updated successfully!");

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCitizen(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {

        try {
            Ubudehe ubudehe = repo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
            repo.delete(ubudehe);
            model.addAttribute("viewCitizens", repo.findAll());
            redirectAttributes.addFlashAttribute("message", "Information deleted successfully!");

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/list";
    }

}
