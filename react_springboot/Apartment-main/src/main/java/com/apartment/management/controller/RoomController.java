package com.apartment.management.controller;

import com.apartment.management.model.Room;
import com.apartment.management.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // ดึงข้อมูลห้องทั้งหมด
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.findAllRooms();
        if (rooms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    // ดึงข้อมูลห้องตาม id
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Optional<Room> room = roomService.findRoomById(id);
        if (room.isPresent()) {
            return new ResponseEntity<>(room.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // สร้างห้องใหม่
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        // ตรวจสอบว่าห้องมีอยู่แล้วหรือไม่ (Optional)
        if (room.getRoomNumber() == null || room.getRoomNumber().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // ข้อมูลห้องไม่สมบูรณ์
        }
        Room savedRoom = roomService.saveRoom(room);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    // อัปเดตข้อมูลห้อง
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        Optional<Room> existingRoom = roomService.findRoomById(id);
        if (existingRoom.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // อัปเดตข้อมูลห้อง
        room.setId(id);
        Room updatedRoom = roomService.saveRoom(room);
        return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
    }

    // ลบห้อง
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        Optional<Room> room = roomService.findRoomById(id);
        if (room.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
