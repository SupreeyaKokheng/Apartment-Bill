package com.apartment.management.repository;

import com.apartment.management.model.Room;
import com.apartment.management.model.WaterMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
//import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface WaterMeterRepository extends JpaRepository<WaterMeter, Long> {
    // ค้นหามิเตอร์น้ำตามห้องและเดือน
    //List<WaterMeter> findByRoomAndRecordDateBetween(Room room, YearMonth startDate, YearMonth endDate);
    List<WaterMeter> findByRoomAndRecordDateBetween(Room room, LocalDate startDate, LocalDate endDate);

     @Query("SELECT wm FROM WaterMeter wm WHERE wm.room = :room AND YEAR(wm.recordDate) = :year AND MONTH(wm.recordDate) = :month")
    Optional<WaterMeter> findByRoomAndRecordMonth(@Param("room") Room room, @Param("year") int year, @Param("month") int month);


}
