package cn.edu.nju.candleflame.tickets.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JsonUtilTest {
    @Test
    public void fromString() throws Exception {
         String test="{\"sex\":\"1\",\"name\":\"xiaoming\"}";
         Map<String,String> map=JsonUtil.fromString(test);
         for (String i:map.keySet()){
             System.out.println(i+"   "+map.get(i));
         }
    }

    @Test
    public void toJson() throws Exception {
        HashMap<String,String> map=new HashMap<>();
        map.put("name","xiaoming");
        map.put("sex","1");
        System.out.println(JsonUtil.toJson(map));;
    }

}