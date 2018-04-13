package cn.edu.nju.candleflame.tickets.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeUtilTest {
    @Test
    public void getTimeStamp() throws Exception {
        TimeUtil.getTimeStamp("03/31/2018 8:23 PM");
    }

}