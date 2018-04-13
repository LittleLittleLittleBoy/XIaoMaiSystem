package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.entity.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<LoginEntity, String> {

    LoginEntity findByEmail(String email);

    LoginEntity findByCode(String code);

    LoginEntity findByEmailAndPass(String email,String pass);

}
