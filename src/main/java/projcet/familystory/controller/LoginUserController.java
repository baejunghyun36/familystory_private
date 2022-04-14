package projcet.familystory.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projcet.familystory.domain.User;
import projcet.familystory.form.UserForm;
import projcet.familystory.service.UserService;
import projcet.familystory.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginUserController {

    private final UserService userService;

    @GetMapping("/{uID}/myPage")
    public String myPage(@PathVariable Long uID, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        //log.info("\n\n\nsession 정보 : {}", session);
        User user = userService.findByUid(uID);
        Long uid1 = user.getUId();
        Long uid2 = ((User)session.getAttribute(SessionConst.LOGIN_USER)).getUId();
        if(uid1.equals(uid2)){
            model.addAttribute("myInformation", user);
            return "users/myPage";
        }
        else{
            return "redirect:/";
        }
    }

    @GetMapping("/myPage")
    public String myPage2(Model model, HttpServletRequest request) {
        HttpSession session2 = request.getSession(false);
        User user = (User) session2.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("myInformation", user);
        return "users/myPage";
    }


}
