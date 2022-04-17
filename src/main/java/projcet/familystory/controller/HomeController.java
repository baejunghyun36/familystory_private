package projcet.familystory.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import projcet.familystory.domain.User;

import projcet.familystory.form.LoginForm;
import projcet.familystory.argumentresolver.Login;
import projcet.familystory.session.SessionConst;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {


    /*
   localhost:8080 접근 시 해당 RequestMapping을 통해2서 home.html 로 보여준다.
   이때, "loginForm"이라는 모델에 LoginForm()의 형식을 담고 간다.
   LoginForm()에는 loginId와 password를 String형태로 담고 있으며, @NotEmpty 애노테이션을 줌으로써 공백 입력시 오류를 표시한다.
*/

    // 최초 접근 시 해당 GetMapping을 통해서 home.html로 보여준다.
    @GetMapping("/")
    public String home(Model model, @Login User loginUser) {
        // 이때, "loginForm"이라는 이름을 가진 모델에 LoginForm()의 형식을 담고 간다.
        model.addAttribute("loginForm", new LoginForm());

        return "home";

    }



//    @GetMapping("/users/login")
//    public String homeLogin(HttpServletRequest request, Model model) {
//
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            return "home";
//        }
//
//        User loginUser = (User)session.getAttribute(SessionConst.LOGIN_USER);
//
//        if (loginUser == null) {
//            return "home";
//        }
//
//        model.addAttribute("loginUser", loginUser);
//        return "loginHome";
//    }
//


    // /loginHome과 매핑해서 loginHome.html로 보내준다.
   // @GetMapping("/loginHome")
    public String homeLogin2(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser, Model model) {
        // LOGIN_USER 는 "loginUser"로 정의되어 있다. 즉, "loginUser"라는 이름을 가진 session 상자에서 들어있는 객체를 꺼집어내서
        // loginUser 객체에 담아라.
        if (loginUser == null) {
            // 현재 세션에 아무것도 담겨져 있지 않는다면, 로그인에 실패했다는 의미.
            return "home";
        }
        // 로그인이 성공되어 세션이 생성되었으므로 "loginUser"라는 모델 상자에 loginUser 객체를 담고 loginHome.html로 간다.
        model.addAttribute( "loginUser", loginUser);
        return "loginHome";
    }

    //2번째 방법

    //Login 애노테이션 생성한거 적용하기.
    @GetMapping("/loginHome")
    //세션의 정보가 들어있으면 그 내용을 User user객체에 넣어준다.
    //원래 코드는 @SessionAttribute(name = SessionConst.LOGIN_USER, required = false)
    //이건데 @Login 으로 축약시켜놨다.
    public String homeLogin(@Login User loginUser, Model model) {

        //세션에 정보가 없으면 home.html로 보낸다.
        if (loginUser == null) {
            return "home";
        }
        //세션에 정보가 있다면 loginHome.html로 보낸다.
        model.addAttribute("loginUser", loginUser);
        return "loginHome";
    }





//    @GetMapping("/users/login")
//    //로그인 안한 사용자도 들어와야하니까 required false
//    public String homeLogin(@CookieValue(name = "userId", required = false) Long userUid, Model model) {
//
//        if (userUid == null) {
//            return "home";
//        }
//
//
//        User loginUser = userService.findByUid(userUid);
////        Optional<User> loginUser = userService.findByUid(userUid);
//        if (loginUser == null) {
//            return "home";
//        }
//
//        model.addAttribute("loginUser", loginUser);
//        return "loginHome";
//    }
}
