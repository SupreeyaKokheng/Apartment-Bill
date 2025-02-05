package com.apartment.management.service.impl;

import com.apartment.management.model.Billing;
import com.apartment.management.model.ElectricMeter;
import com.apartment.management.model.Room;
import com.apartment.management.model.WaterMeter;
import com.apartment.management.repository.BillingRepository;
import com.apartment.management.repository.RoomRepository;
import com.apartment.management.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private RoomRepository roomRepository;
    @Override
    public List<Billing> generateBillingForCurrentMonth() {
        YearMonth targetMonth = YearMonth.now();
        String monthString = targetMonth.toString(); // "2025-02"
    
        // ✅ ดึงข้อมูลห้องทั้งหมด
        List<Room> rooms = roomRepository.findAll();
        List<Billing> updatedBillings = new ArrayList<>();
    
        for (Room room : rooms) {
            // ✅ ค้นหาบิลของห้องในเดือนปัจจุบัน
            List<Billing> existingBillings = billingRepository.findByRoomAndMonth(room, monthString);
    
            // ✅ ตรวจสอบว่ามีบิลของห้องนี้ในเดือนนี้หรือยัง
            Billing billing;
            if (!existingBillings.isEmpty()) {
                billing = existingBillings.get(0);
                System.out.println("🔄 อัปเดตบิลของห้อง " + room.getRoomNumber());
            } else {
                billing = new Billing();
                billing.setRoom(room);
                billing.setRoomNumber(room.getRoomNumber()); // ✅ บันทึกเลขห้องลงในบิล
                billing.setMonth(monthString);
                System.out.println("🆕 สร้างบิลใหม่ของห้อง " + room.getRoomNumber());
            }
    
            // ✅ คำนวณค่ามิเตอร์น้ำและไฟของเดือนปัจจุบัน
            double waterMeter = room.getWaterMeters().stream()
                    .filter(meter -> YearMonth.from(meter.getRecordDate()).equals(targetMonth))
                    .map(WaterMeter::getMeterValue)
                    .reduce((first, second) -> second)
                    .orElse(0.0);
    
            double electricMeter = room.getElectricMeters().stream()
                    .filter(meter -> YearMonth.from(meter.getRecordDate()).equals(targetMonth))
                    .map(ElectricMeter::getMeterValue)
                    .reduce((first, second) -> second)
                    .orElse(0.0);
    
            // ✅ คำนวณค่าใช้จ่าย
            double waterUnitPrice = 8;
            double electricUnitPrice = 17;
    
            BigDecimal waterBill = BigDecimal.valueOf(waterMeter * waterUnitPrice);
            BigDecimal electricBill = BigDecimal.valueOf(electricMeter * electricUnitPrice);
            BigDecimal totalBill = waterBill.add(electricBill);
    
            // ✅ บันทึกค่าใหม่ลงบิล
            billing.setWaterBill(waterBill);
            billing.setElectricBill(electricBill);
            billing.setTotalBill(totalBill);
    
            // ✅ บันทึกหรืออัปเดตบิลลง Database
            billingRepository.save(billing);
            updatedBillings.add(billing);
        }
    
        return updatedBillings;
    }
    
    @Override
    public List<Billing> getBillingRecordsForCurrentMonth() {
        // เพิ่มโค้ดเพื่อดึงข้อมูลบิลของห้องทั้งหมดในเดือนปัจจุบัน
        // เช่น ดึงบิลทั้งหมดในเดือนมกราคม 2025
        return billingRepository.findAll(); // ดึงข้อมูลจริงจาก repository
    }

    @Override
    public Billing calculateBillingForRoom(Long roomId, String month) {
        // คำนวณค่าใช้จ่าย (มิเตอร์น้ำและไฟ) สำหรับห้องตามเดือนที่กำหนด
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        BigDecimal waterBill = BigDecimal.valueOf(100); // คำนวณจริง
        BigDecimal electricBill = BigDecimal.valueOf(200); // คำนวณจริง

        Billing billing = new Billing();
        billing.setRoom(room);
        billing.setMonth(month);
        billing.setWaterBill(waterBill);
        billing.setElectricBill(electricBill);
        billing.setTotalBill(waterBill.add(electricBill));

        return billing; // คืนค่าการคำนวณ
    }

    @Override
    public List<Billing> getBillingSummaryByMonth(String month) {
        // สรุปค่าใช้จ่ายทั้งหมดในเดือนนี้ (บิลรวม)
        return billingRepository.findByMonth(month); // ดึงบิลที่มีเดือนตรงกัน
    }
}
