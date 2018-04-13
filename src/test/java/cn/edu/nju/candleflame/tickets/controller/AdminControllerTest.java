package cn.edu.nju.candleflame.tickets.controller;

import cn.edu.nju.candleflame.tickets.TicketsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TicketsApplication.class)
public class AdminControllerTest {
    @Autowired
    AdminController controller;

    @Test
    public void handleInformation() throws Exception {

    }
    @Test
    public void handleInformation2() throws Exception {
    }

}