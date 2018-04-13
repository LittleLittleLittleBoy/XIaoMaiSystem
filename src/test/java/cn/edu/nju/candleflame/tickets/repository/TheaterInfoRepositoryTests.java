package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.TicketsApplication;
import cn.edu.nju.candleflame.tickets.entity.LoginEntity;
import cn.edu.nju.candleflame.tickets.entity.TheaterInfoEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TicketsApplication.class)
public class TheaterInfoRepositoryTests {
    @Autowired
    private TheaterInfoRepository theaterInfoRepository;
    @Test
    public void test(){

        String id=theaterInfoRepository.findMaxTheaterId();
        System.out.println(id);
    }
    @Test
    public void test1(){
        List<TheaterInfoEntity> list=theaterInfoRepository.findAllMessage();
        System.out.println(list.size());
    }
}
