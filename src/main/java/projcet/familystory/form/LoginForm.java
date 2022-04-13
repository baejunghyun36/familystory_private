package projcet.familystory.form;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@Data
public class LoginForm {

    /*
    사용자가 로그인할 때 사용하는 로그인 폼으로써 loginId, password의 데이터 내용을 담고 있다.
    해당 폼양식에서 @NotEmpty 애노테이션 형식을 사용함으로써 공백 데이터를 방지한다.
    만약 공백 데이터가 들어왔을 때 경우 해당 message를 출력함으로써 사용자에게 경고 UI를 제공한다.
*/
    @NotEmpty(message = "아이디는 필수입니다.")
    private String loginId;
    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

}
