package com.apartment.management.repository;

import com.apartment.management.model.ElectricMeter;
import com.apartment.management.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import java.time.YearMonth;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ElectricMeterRepository extends JpaRepository<ElectricMeter, Long> {
    // ค้นหามิเตอร์ไฟตามห้องและเดือน
    List<ElectricMeter> findByRoomAndRecordDateBetween(Room room, LocalDate startDate, LocalDate endDate);
    List<ElectricMeter> findByRecordDateBetween(LocalDate startDate, LocalDate endDate);
}
