package projcet.familystory.form;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserForm {


    /*
회원가입을 할 때 사용하는 form으로 id, password, name, nickname, email,phoneNumber, birthday의 데이터 내용을 담고 있다.
해당 폼양식에서 @NotEmpty 애노테이션 형식을 사용함으로써 공백 데이터를 방지한다.
만약 공백 데이터가 들어왔을 때 경우 해당 message를 출력함으로써 사용자에게 경고 UI를 제공한다.
*/
    @NotEmpty(message = "아이디는 필수 입니다")
    private String id;

    @NotEmpty(message = "비밀번호는 필수 입니다")
    private String passWord;

    @NotEmpty(message = "이름은 필수 입니다")
    private String name;

    @NotEmpty(message = "닉네임은 필수 입니다")
    private String nickName;

    @NotEmpty(message = "E-mail은 필수 입니다")
    private String email;

    @NotEmpty(message = "휴대폰 번호는 필수 입니다")
    private String phoneNumber;

    @NotEmpty(message = "생년월일은 필수 입니다")
    private String birthDay;

}
