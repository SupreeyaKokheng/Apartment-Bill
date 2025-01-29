package com.apartment.management.service.impl;

import com.apartment.management.model.Billing;
import com.apartment.management.model.Room;
import com.apartment.management.repository.BillingRepository;
import com.apartment.management.repository.RoomRepository;
import com.apartment.management.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Billing> generateBillingForCurrentMonth() {
        // เพิ่มโค้ดเพื่อสร้างบิลสำหรับเดือนปัจจุบัน
        // เช่น คำนวณค่าใช้จ่ายทั้งหมดสำหรับทุกห้องในเดือนปัจจุบัน
        List<Room> rooms = roomRepository.findAll();
        for (Room room : rooms) {
            // คำนวณค่าใช้จ่ายในแต่ละห้องจากมิเตอร์น้ำและไฟ
            BigDecimal waterBill = BigDecimal.valueOf(10); // คำนวณจริง
            BigDecimal electricBill = BigDecimal.valueOf(200); // คำนวณจริง

            Billing billing = new Billing();
            billing.setRoom(room);
            billing.setMonth("2025-02"); // ใช้เดือนที่ต้องการ
            billing.setWaterBill(waterBill);
            billing.setElectricBill(electricBill);
            billing.setTotalBill(waterBill.add(electricBill));

            billingRepository.save(billing); // บันทึกบิล
        }
        return billingRepository.findAll(); // คืนค่าบิลทั้งหมด
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
