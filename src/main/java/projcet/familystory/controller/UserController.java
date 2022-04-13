package projcet.familystory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import projcet.familystory.domain.User;
import projcet.familystory.form.LoginForm;
import projcet.familystory.form.UserForm;
import projcet.familystory.service.UserService;
import projcet.familystory.session.SessionConst;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
        [home.html 회원가입 버튼 -> createUserForm.html] 과정
        회원가입 버튼을 눌렀을 때 users/new로 GetMapping 된다.
        "userForm"이라는 이름을 가진 model 박스에 UserForm의 객체를 담아서 createUserFormd.html 에 보낸다.
    */
    @GetMapping("/users/new")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/createUserForm";
    }

    @PostMapping("/users/new")
    public String create(@Valid UserForm form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "users/createUserForm";
        }

        User user = new User();
        user.setUserId(form.getId());
        user.setPassWord(form.getPassWord());
        user.setName(form.getName());
        user.setNickName(form.getNickName());
        user.setEmail(form.getEmail());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setBirthDay(form.getBirthDay());
//        user.setImage(form.getImage());
//        user.setMainGroupId(form.getMainGroupId());

        Optional<User> signUser = userService.join(user);

        if (signUser == null) {
            result.reject("signupFail", "이미 존재하는 아이디입니다 ");
            return "users/createUserForm";
        }

        model.addAttribute("loginForm", new LoginForm());
        return "home";
//        else {
//            return "users/createUserForm";
//        }
        //홈에 보내
    }

    //@PostMapping("/users/login")
//    public String login(@Valid LoginForm form, BindingResult result, Model model, HttpServletResponse response) {
//
//
//        if (result.hasErrors()) {
//            return "home";
//        }
//
//        User loginUser = userService.login(form.getLoginId(), form.getPassword());
//
//
//        if (loginUser == null) {
//            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다. ");
//            return "home";
//        }
//
//
//        //쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두종료)
//        Cookie idCookie = new Cookie("userId", String.valueOf(loginUser.getUId()));
//        response.addCookie(idCookie);
//
//        return "redirect:/users/login";
//
//        if(userService.findOne(user)==false){
//            return "redirect:/";
//        }
//
//        else{
//            model.addAttribute("loginUser", user);
//            return "users/login";
//        }
//
//        //홈에 보내
//    }

    @PostMapping("/users/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult result, Model model, HttpServletRequest request) {


        if (result.hasErrors()) {
            return "home";
        }

        User loginUser = userService.login(form.getLoginId(), form.getPassword());

        log.info("login? {}", loginUser);


        if (loginUser == null) {
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다. ");
            return "home";
        }


        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);

        //쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두종료)
//        Cookie idCookie = new Cookie("userId", String.valueOf(loginUser.getUId()));
//        response.addCookie(idCookie);

        return "redirect:/users/login";
       // return "loginHome";

    }


    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "users/userList";
    }

    //    @GetMapping("/users")
//    public String list(Model model) {
//        List<User> users = userService.findUsers();
//        model.addAttribute("users", users);
//        return "users/userList";
//    }


//    @PostMapping("/logout")
//    public String logout(HttpServletResponse response) {
//        expireCookie(response, "userId");
//        return "redirect:/";
//
//    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session !=null){
            session.invalidate();
        }
        return "redirect:/";
    }

//    private void expireCookie(HttpServletResponse response, String cookieName) {
//        Cookie cookie = new Cookie(cookieName, null);
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//    }

/*
    쿠키에 중요한 값을 노출하지 않고 사용자 별로 에측 불가능한 임의의 토큰(랜덤 값)을 노출하고, 서버에서 토큰과 사용자 id를 매핑해서
  인식한다. 그리고 서버에서 토큰을 관리한다.
  토큰은 해커가 임의의 값을 넣어도 찾을수 없도록 예상 불가능해야한다.
  해커가 토큰을 털어가도 시간이 지나면 사용할 수 없도록 서버에서 해당 토큰의 만료시간을 짧게(예: 30분)을 유지한다.
  또는 해킹이 의심되는 경우 서버에서 해당 토큰을 강제로 제거하면 된다.


    회원과 관련된 정보는 전혀 클라이언트에 전달하지 않는다는 것.
    오직 추정 불가능한 세션 ID만 쿠키를 통해 클라이언트에 전달한다.


    쿠키 값을 변조 가능, -> 예상 불가능한 복잡한 세션 Id를 사용
    쿠키에 보관하는 정보는 클라이엉ㄴ트 해킹시 털릴 가능성이 있따. -> 세션 Id가 털려도 여기에는 중요한 정보가 없다.
    쿠키탈취 후 사용 -> 해커가 토큰을 털어가도 시간이 지나면 사용할 수 없도록 서버에서 세션의 만료시간을 짧게(예:30분) 유지한다.
    또는 해킹이 의심되는 경우 서버에서 해당 세션을 강제로 제거하면 된다.


    쿠키에 중요한 정보를 보관하는 바업ㅂ은 여러가지 보안 이슈가 있따.
    이 문제를 해결하려면 결국 중요한 정보를 모두 서버에 저장해야하낟.
    그리고 클라이언트와 서버는 추정 불가능한 임의의 식별자 값으로 연결해야한다.

    이렇게 서버에 중요한 정보를 보관하고 연결을 유지하는 방법을 세션이라 한다.

    세션을 사용해서 서버에서 중요한 정보를 관리하게 된다.
     - 예상 불가능한 복잡한 세션 Id를 사용함으로써 쿠키 값을 변조할 수 없다.
     - 세션 Id가 털려도 여기에는 중요한 정보가 없다.
     - 해커가 토큰을 털어가도 시간이 지나면 사용할 수 없도록 서버에서 세션의 만료시간을 짧게 유지



    */





}
