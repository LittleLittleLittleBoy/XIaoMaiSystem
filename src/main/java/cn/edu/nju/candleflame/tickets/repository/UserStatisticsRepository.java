package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserStatisticsRepository extends JpaRepository<TicketEntity,Long> {

    @Query(value = "SELECT count(*) FROM ticket t WHERE t.userid=?1 AND t.iscancel=FALSE AND t.ispayed=TRUE UNION SELECT count(*) FROM ticket t WHERE t.userid=?1 AND t.iscancel=TRUE UNION SELECT sum(m.money) FROM moneyInfo m WHERE m.fromuser=?1",nativeQuery = true)
    public String[] findByUserStatistics(String email);

    @Query(value = "SELECT s.showid showid,sum(t.issuccess) success,sum(t.iscancel) cancel FROM showinfo s, ticket t WHERE s.showid=t.showid AND s.theaterid=?1 GROUP BY s.showid",nativeQuery = true)
    public List<Object[]> findByTheaterStatistics(String theaterid);

    @Query(value = "SELECT score from user_info;",nativeQuery = true)
    public Object[] findAllScore();

    @Query(value = "SELECT a.date,a.buy,b.cancel FROM (SELECT  DATE_FORMAT(m.time,'%Y-%m-%d') date,sum(m.money) buy from moneyInfo m WHERE m.fromtype=0 GROUP BY date ) a left join (SELECT  DATE_FORMAT(m.time,'%Y-%m-%d') date,sum(m.money) cancel from moneyInfo m WHERE m.fromtype=1 GROUP BY date ) b ON a.date=b.date ORDER BY a.date ASC LIMIT 10",nativeQuery = true)
    public List<Object[]> findMoneyByDate();

}
