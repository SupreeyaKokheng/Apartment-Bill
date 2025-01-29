package com.apartment.management.controller;

import com.apartment.management.model.Billing;
import com.apartment.management.service.BillingService;
import com.apartment.management.service.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @Autowired
    private MeterService meterService;

    // สร้างใบแจ้งหนี้สำหรับทุกห้องในเดือนปัจจุบัน
    @PostMapping("/generate")
    public ResponseEntity<List<Billing>> generateBillsForCurrentMonth() {
        List<Billing> billings = billingService.generateBillingForCurrentMonth();
        if (billings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(billings, HttpStatus.CREATED);
    }

    // ดึงข้อมูลใบแจ้งหนี้ของห้องทั้งหมดในเดือนปัจจุบัน
    @GetMapping("/current")
    public ResponseEntity<List<Billing>> getCurrentMonthBills() {
        List<Billing> billings = billingService.getBillingRecordsForCurrentMonth();
        if (billings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(billings, HttpStatus.OK);
    }

    // คำนวณค่าใช้จ่าย (มิเตอร์น้ำและไฟ) สำหรับห้องตามเดือน
    @GetMapping("/{roomId}/{month}")
    public ResponseEntity<Billing> getBillingForRoomByMonth(
            @PathVariable Long roomId,
            @PathVariable String month) {
        Billing billing = billingService.calculateBillingForRoom(roomId, month);
        if (billing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(billing, HttpStatus.OK);
    }

    // สรุปค่าใช้จ่ายทั้งหมดในเดือนนี้ (บิลรวม)
    @GetMapping("/summary/{month}")
    public ResponseEntity<List<Billing>> getBillingSummaryByMonth(@PathVariable String month) {
        List<Billing> billings = billingService.getBillingSummaryByMonth(month);
        if (billings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(billings, HttpStatus.OK);
    }
}
