package projcet.familystory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projcet.familystory.domain.User;
import projcet.familystory.repository.UserRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@Service
@org.springframework.transaction.annotation.Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
/*
    컨트롤러에 해당 로직을 둘 수도 있지만, 이렇게 되면 컨트롤러가 너무 많은 역할을 담당한다.
    그래서 일반적으로 비즈니스 로직은 서비스라는 계층을 별도로 만들어서 처리한다.
        그리고 컨트롤러는 비즈니스 로직이 있는 서비스를 호출하는 역할을 담당한다.
  */

    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public Optional<User> join(User user) {
        List<User> findUsers = userRepository.findUserId(user.getUserID());
        if (findUsers.isEmpty()){
            userRepository.save(user);
            return Optional.of(user);
        }
        return null;
    }

    public User findByUid(Long Uid){
        return userRepository.findOne(Uid);
    }

    public Optional<User> findId(User user) {
        List<User> findUsers = userRepository.findAll();
        for(User u : findUsers){
            if(user.getName().equals(u.getName())&&user.getEmail().equals(u.getEmail())) {
                return Optional.of(u);
            }
        }
        return null;
    }


    public User login(String loginId, String password) {

     /*
        첫번째 코드 방식
        Optional<User> findUserOptional = userRepository.findByLoginId(loginId);
        User user = findUserOptional.get();
        if(user.getPassWord().equals(password)){
            return user;
        }
        else{
            return null;
        }

        두번째 코드 방식
        Optional<User> byLoginId = userRepository.findByLoginId(loginId);
        return byLoginId.filter(u -> u.getPassWord().equals(password))
                .orElse(null);
*/

        // 3번째 코드 방식
        return userRepository.findByLoginId(loginId).filter(u -> u.getPassword().equals(password))
                .orElse(null);

    }
}


/*
    private boolean validateDuplicateUser(User user) {
        //List<User> findUsers = userRepository.findByName(user.getUserId());
        if (findUsers.isEmpty()){
            return false;
            //throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
        return true;
    }

    */

//회원 조회
  /*  public boolean findOne(User user) {
        //List<Member> findMembers = memberRepository.findByName(member.getUserId());
        List<User> findUsers = userRepository.findAll();
        for(User u : findUsers){
            if(user.getUserId().equals(u.getUserId())&&user.getPassWord().equals(u.getPassWord()))
                return true;
        }
        return false;
    }
*/