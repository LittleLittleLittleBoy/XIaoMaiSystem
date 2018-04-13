package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.entity.OffTicketEntity;
import cn.edu.nju.candleflame.tickets.entity.ShowInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OffTicketRepository extends JpaRepository<OffTicketEntity,Long> {
    List<OffTicketEntity> findAllByShow(ShowInfoEntity showInfoEntity);
}
