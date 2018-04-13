package cn.edu.nju.candleflame.tickets.service;

import org.springframework.transaction.annotation.Transactional;

public interface BankService {
    @Transactional
    boolean buyTicket(String email,String theaterid,double money);
    @Transactional
    boolean refund(String theaterid,String email,double money);
}
