package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.TicketsApplication;
import cn.edu.nju.candleflame.tickets.entity.DistributionTicketEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TicketsApplication.class)
public class DistributionTicketRepositoryTest {

    @Autowired
    DistributionTicketRepository distributionTicketRepository;
    @Test
    public void findTodoTicket() {
        List<String> todoTicket = distributionTicketRepository.findTodoTicket();
        System.out.println(todoTicket.size());
    }
}