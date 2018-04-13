package cn.edu.nju.candleflame.tickets.util;

import com.google.gson.Gson;

public class GsonUtil {
    private static Gson gson = new Gson();

    public static String toString(Object o){
        return gson.toJson(o);
    }
}
