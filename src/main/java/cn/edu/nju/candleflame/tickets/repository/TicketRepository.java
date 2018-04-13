package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.entity.ShowInfoEntity;
import cn.edu.nju.candleflame.tickets.entity.TicketEntity;
import cn.edu.nju.candleflame.tickets.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findAllByShowAndIscancel(ShowInfoEntity showInfoEntity,boolean iscancel);

    List<TicketEntity> findAllByShowAndUserAndIscancel(ShowInfoEntity showInfoEntity, UserInfoEntity userInfoEntity,boolean iscancel);

    List<TicketEntity> findAllByUserOrderByCreatetimeDesc(UserInfoEntity userInfoEntity);

    List<TicketEntity> findAllByUserAndIspayedAndIssuccessAndIscancelOrderByCreatetimeDesc(UserInfoEntity userInfoEntity,boolean ispayed,boolean issuccess,boolean iscancel);

    List<TicketEntity> findAllByUserAndIscancelAndIspayedOrderByCreatetimeDesc(UserInfoEntity userInfoEntity,boolean iscancel,boolean ispayed);

    List<TicketEntity> findAllByUserAndIscancelOrderByCreatetimeDesc(UserInfoEntity userInfoEntity,boolean iscancel);

    TicketEntity findByUserAndTicketid(UserInfoEntity user,String ticketid);

    TicketEntity findByTicketid(String ticketid);


    List<TicketEntity> findAllByIspayedAndIscancelAndCreatetimeBefore(boolean ispayed,boolean iscancel, Timestamp timestamp);
}
