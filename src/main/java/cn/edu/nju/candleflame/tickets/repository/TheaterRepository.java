package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.entity.TheaterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TheaterRepository extends JpaRepository<TheaterEntity,Long> {

    TheaterEntity findByTheaterid(String theaterId);

}
