package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.TicketsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TicketsApplication.class)
public class UserStatisticsRepositoryTest {
    @Autowired UserStatisticsRepository repository;

    @Test
    public void findByUserStatistics() {
        Object[] byUserStatistics = repository.findByUserStatistics("1150170525@qq.com");
        for (Object o:byUserStatistics){
            System.out.println(o.toString());
        }

    }
}