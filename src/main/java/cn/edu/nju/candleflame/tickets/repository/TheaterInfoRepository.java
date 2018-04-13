package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.entity.TheaterInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TheaterInfoRepository extends JpaRepository<TheaterInfoEntity, String> {
    @Query("select max(t.theaterid)from theaterinfo t")
    String findMaxTheaterId();

    TheaterInfoEntity findFirstByTheaterid(String theaterid);

    TheaterInfoEntity findById(int id);

    @Query("from theaterinfo t order by t.id desc ")
    List<TheaterInfoEntity> findAllMessage();

    @Query("from theaterinfo t where t.isread=0 order by t.id desc ")
    List<TheaterInfoEntity> findAllUndoMessage();
}
