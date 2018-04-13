package cn.edu.nju.candleflame.tickets.service.impl;

import cn.edu.nju.candleflame.tickets.TicketsApplication;
import cn.edu.nju.candleflame.tickets.service.BankService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TicketsApplication.class)
public class BankServiceImplTest {
    @Autowired
    BankService bankService;
    @Test
    public void buyTicket() {
        bankService.buyTicket("1150170525@qq.com","0000001",100);
    }

    @Test
    public void refund() {
        bankService.refund("0000001","1150170525@qq.com",95);
    }
}