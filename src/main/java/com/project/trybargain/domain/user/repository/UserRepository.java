package com.project.trybargain.domain.user.repository;

import com.project.trybargain.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public Optional<User> findById(long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    /**
     * 유저아이디로 찾기
     * @param userId
     * @return
     */
    public Optional<User> findUserByUserId(String userId) {
        List<User> findUser = em.createQuery("select u from User u where u.user_id = :userId", User.class)
                .setParameter("userId", userId).getResultList();
        return findUser.stream().findAny();
    }


    /**
     * 이메일로 찾기
     * @param email
     * @return
     */
    public Optional<User> findUserByEmail(String email) {
        List<User> findUser = em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email).getResultList();
        return findUser.stream().findAny();
    }

    /**
     * 닉네임으로 찾기
     * @param user_nm
     * @return
     */
    public Optional<User> findUserByName(String user_nm) {
        List<User> findUser = em.createQuery("select u from User u where u.user_nm = :user_nm", User.class)
                .setParameter("user_nm", user_nm).getResultList();
        return findUser.stream().findAny();

    }
}
