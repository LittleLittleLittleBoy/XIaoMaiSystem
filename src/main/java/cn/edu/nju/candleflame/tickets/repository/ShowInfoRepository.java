package cn.edu.nju.candleflame.tickets.repository;

import cn.edu.nju.candleflame.tickets.entity.ShowInfoEntity;
import cn.edu.nju.candleflame.tickets.entity.TheaterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ShowInfoRepository extends JpaRepository<ShowInfoEntity,Long>{

    List<ShowInfoEntity> findAllByTheaterOrderByTimeDesc(TheaterEntity theaterEntity);

    List<ShowInfoEntity> findAllByTimeAfterAndTheaterOrderByTimeDesc(Timestamp timestamp,TheaterEntity theaterEntity);

    List<ShowInfoEntity> findAllByTimeBeforeAndTheaterOrderByTimeDesc(Timestamp timestamp,TheaterEntity theaterEntity);

    List<ShowInfoEntity> findAllByTimeAfterOrderByTimeDesc(Timestamp timestamp);

    ShowInfoEntity findByShowid(int showid);
}
