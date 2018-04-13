package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity,Long> {

    RoomEntity findByTheateridAndRoomid(String theaterid,String roomid);

    @Query("select r from room r where r.theaterid=:theaterid and r.isdelete=false ")
    List<RoomEntity> findAvaliableRoom(@Param("theaterid")String theaterid);
}
