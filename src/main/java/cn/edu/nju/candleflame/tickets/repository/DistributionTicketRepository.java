package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.entity.DistributionTicketEntity;
import cn.edu.nju.candleflame.tickets.entity.ShowInfoEntity;
import cn.edu.nju.candleflame.tickets.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface DistributionTicketRepository extends JpaRepository<DistributionTicketEntity,Long> {

    DistributionTicketEntity findByTicketid(String ticketid);

    @Query(value = "SELECT t.ticketid from distributionticket t,showinfo s WHERE t.showid=s.showid AND date_add(now(),INTERVAL 14 DAY)>s.time",nativeQuery = true)
    List<String> findTodoTicket();

    List<DistributionTicketEntity> findByShowAndUserAndIscancel(ShowInfoEntity show, UserInfoEntity user, boolean success);
}
