package projcet.familystory.social;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

// domain/user/Role.java
@Getter
@RequiredArgsConstructor
public enum Role1 {


    /* 스프링 시큐리티에서는 권한 코드에 항상 ROLE_이 앞에 있어야만 함
       그래서 코드별 키 값을 ROLE_GUSER, ROLE_USER 등으로 지정 */
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
