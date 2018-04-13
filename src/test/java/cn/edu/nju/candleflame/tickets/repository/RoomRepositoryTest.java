package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.TicketsApplication;
import cn.edu.nju.candleflame.tickets.entity.RoomEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TicketsApplication.class)
public class RoomRepositoryTest {
    @Autowired
    RoomRepository roomRepository;
    @Test
    public void findByTheateridAndRoominfo() throws Exception {
        RoomEntity room = roomRepository.findByTheateridAndRoomid("0000001", "房间1");
        System.out.println(room.getRow());
    }
    @Test
    public void test(){
        List<RoomEntity> avaliableRoom = roomRepository.findAvaliableRoom("0000001");
        System.out.println(avaliableRoom.size());
    }

}