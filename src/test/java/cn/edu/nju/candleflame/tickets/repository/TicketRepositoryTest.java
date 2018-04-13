package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.TicketsApplication;
import cn.edu.nju.candleflame.tickets.entity.TicketEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TicketsApplication.class)
public class TicketRepositoryTest {

    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    ShowInfoRepository showInfoRepository;

    @Test
    public void test(){
        List<TicketEntity> ticketEntities=ticketRepository.findAllByShowAndIscancel(showInfoRepository.findByShowid(5),false);
        System.out.println("");
    }

}