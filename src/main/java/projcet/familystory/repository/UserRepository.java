package projcet.familystory.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import projcet.familystory.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;


    public void save(User user) {
        em.persist(user);
    }


    public User findOne(Long uid) {
        log.info("INFO");
        System.out.println("uid = " + uid);
        List<User> all = findAll();
        for (User u : all) {
            if (u.getUId().equals(uid)) {
                System.out.println("u.getUId() = " + u.getUId());
                return u; //optional 객체는 껍데기 통인데 null이 들어갈수 있어
            }
        }
        return null;
       // return em.find(User.class, uid);
    }


    public List<User> findAll(){
        List<User> result = em.createQuery("select u from User u", User.class)
                .getResultList();

        return result;
    }

    public List<User> findUserId(String userId) {
        return em.createQuery("select u from User u where u.userId = :userId", User.class)
                .setParameter("userId", userId)
                .getResultList();
    }



    public Optional<User> findByLoginId(String loginId){
//        List<User> all = findAll();
//        for (User u : all) {
//            if (u.getUserId().equals(loginId)) {
//                return Optional.of(u); //optional 객체는 껍데기 통인데 null이 들어갈수 있어
//            }
//        }
//        return Optional.empty();
//

        //다른 코드 버전
        //리스트를 stream으로 돌면서 만족하는 애만 다음 단계로 넘어가.
        //즉,   .filter(u -> u.getUserId().equals(loginId))이거 만족하면 뒤에 .findFirst() 만족하면 바로 넘어가
        return findAll().stream()
                .filter(u -> u.getUserId().equals(loginId))
                .findFirst();
    }
}


