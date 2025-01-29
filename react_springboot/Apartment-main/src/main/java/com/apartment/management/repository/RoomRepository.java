package com.apartment.management.repository;

import com.apartment.management.model.Room;
import com.apartment.management.model.WaterMeter;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);
    
   
}
