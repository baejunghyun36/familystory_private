package projcet.familystory.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projcet.familystory.domain.User;
import projcet.familystory.form.UserForm;
import projcet.familystory.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginUserController {

    private final UserService userService;

    @GetMapping("/{uID}/myPage")
    public String myPage(@PathVariable Long uID, Model model) {

        User user = userService.findByUid(uID);
        model.addAttribute("myInformation", user);
        return "users/myPage";
    }

}
