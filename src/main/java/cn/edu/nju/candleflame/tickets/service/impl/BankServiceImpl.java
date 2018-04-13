package cn.edu.nju.candleflame.tickets.service.impl;

import cn.edu.nju.candleflame.tickets.entity.AdminInfoEntity;
import cn.edu.nju.candleflame.tickets.entity.MoneyInfoEntity;
import cn.edu.nju.candleflame.tickets.entity.TheaterEntity;
import cn.edu.nju.candleflame.tickets.entity.UserInfoEntity;
import cn.edu.nju.candleflame.tickets.repository.AdminInfoRepository;
import cn.edu.nju.candleflame.tickets.repository.MoneyInfoRepository;
import cn.edu.nju.candleflame.tickets.repository.TheaterRepository;
import cn.edu.nju.candleflame.tickets.repository.UserInfoRepository;
import cn.edu.nju.candleflame.tickets.service.BankService;
import cn.edu.nju.candleflame.tickets.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class BankServiceImpl implements BankService {
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    AdminInfoRepository adminInfoRepository;
    @Autowired
    MoneyInfoRepository moneyInfoRepository;
    @Override
    public boolean buyTicket(String email, String theaterid, double money) {
        try {
            UserInfoEntity user = userInfoRepository.findByEmail(email);
            TheaterEntity theater = theaterRepository.findByTheaterid(theaterid);
            user.setMoney(user.getMoney()-money);
            theater.setMoney(theater.getMoney()+money- TicketService.FEES);
            AdminInfoEntity adminInfoEntity = adminInfoRepository.findTopById(0);
            adminInfoEntity.setMoney(adminInfoEntity.getMoney()+TicketService.FEES);
            adminInfoRepository.save(adminInfoEntity);

            MoneyInfoEntity moneyInfoEntity1=new MoneyInfoEntity(user.getEmail(),0,theaterid,1,money-TicketService.FEES);
            moneyInfoRepository.save(moneyInfoEntity1);

            MoneyInfoEntity moneyInfoEntity2=new MoneyInfoEntity(user.getEmail(),0,"admin",2,TicketService.FEES);
            moneyInfoRepository.save(moneyInfoEntity2);


            userInfoRepository.save(user);
            theaterRepository.save(theater);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean refund(String theaterid, String email, double money) {
        try {
            UserInfoEntity user = userInfoRepository.findByEmail(email);
            TheaterEntity theater = theaterRepository.findByTheaterid(theaterid);
            user.setMoney(user.getMoney()+money);
            theater.setMoney(theater.getMoney()-money);

            MoneyInfoEntity moneyInfoEntity1=new MoneyInfoEntity(theaterid,1,user.getEmail(),0,money);
            moneyInfoRepository.save(moneyInfoEntity1);


            userInfoRepository.save(user);
            theaterRepository.save(theater);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
