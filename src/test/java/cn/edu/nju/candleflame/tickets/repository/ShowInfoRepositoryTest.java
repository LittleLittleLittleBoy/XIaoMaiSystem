package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.TicketsApplication;
import cn.edu.nju.candleflame.tickets.entity.ShowInfoEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TicketsApplication.class)
public class ShowInfoRepositoryTest {
    @Autowired
    ShowInfoRepository showInfoRepository;
    @Autowired
    TheaterRepository theaterRepository;

    @Test
    public void test(){
        List<ShowInfoEntity> showInfoEntityList = showInfoRepository.findAllByTheaterOrderByTimeDesc(theaterRepository.findByTheaterid("0000001"));
        System.out.println(showInfoEntityList.get(0).getDescription());
    }
//
//    @Test
//    public void test1(){
//        List<ShowInfoEntity> showInfoEntityList = showInfoRepository.findAllByTheaterid("0000001");
//        System.out.println(showInfoEntityList.get(0).getDescription());
//    }
//
//    @Test
//    public void test2(){
//        List<ShowInfoEntity> showInfoEntities=showInfoRepository.findAllByTimeAfterAndTheateridOrderByTimeDesc(new Timestamp(new java.util.Date().getTime()),"0000001");
//        System.out.println(showInfoEntities.size());
//    }
//    @Test
//    public void test3(){
//        List<ShowInfoEntity> showInfoEntities=showInfoRepository.findAllByTimeBeforeAndTheateridOrderByTimeDesc(new Timestamp(new java.util.Date().getTime()),"0000001");
//        System.out.println(showInfoEntities.size());
//        System.out.println(showInfoEntities.get(0).getTime());
//    }

}
