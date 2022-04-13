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


    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public Optional<User> join(User user) {

        List<User> findUsers = userRepository.findUserId(user.getUserId());
        if (findUsers.isEmpty()){
            userRepository.save(user);
            return Optional.of(user);
            //throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
        return null;
    }

    public User findByUid(Long Uid){
        return userRepository.findOne(Uid);
    }


//
//    private boolean validateDuplicateUser(User user) {
//        //List<User> findUsers = userRepository.findByName(user.getUserId());
////        if (findUsers.isEmpty()){
////            return false;
////            //throw new IllegalArgumentException("이미 존재하는 회원입니다.");
////        }
////        return true;
//    }

    //회원 전체 조회
    public List<User> findUsers(){
        return userRepository.findAll();
    }

    //회원 조회
    public boolean findOne(User user) {
        //List<Member> findMembers = memberRepository.findByName(member.getUserId());
        List<User> findUsers = userRepository.findAll();
        for(User u : findUsers){
            if(user.getUserId().equals(u.getUserId())&&user.getPassWord().equals(u.getPassWord()))
                return true;
        }
        return false;
    }

    public User login(String loginId, String password) {
//        Optional<User> findUserOptional = userRepository.findByLoginId(loginId);
//        User user = findUserOptional.get();
//        if(user.getPassWord().equals(password)){
//            return user;
//        }
//        else{
//            return null;
//        }

        //다른 코드
//        Optional<User> byLoginId = userRepository.findByLoginId(loginId);
//        return byLoginId.filter(u -> u.getPassWord().equals(password))
//                .orElse(null);

        //또 다른 코드
        return userRepository.findByLoginId(loginId).filter(u -> u.getPassWord().equals(password))
                .orElse(null);

    }
}
