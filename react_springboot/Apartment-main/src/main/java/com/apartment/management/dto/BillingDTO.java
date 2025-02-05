package com.apartment.management.dto;

import com.apartment.management.model.Billing;
import java.time.LocalDate;

public class BillingDTO {
    private Long roomId;
    private String roomNumber;
    private Double waterMeterValue;
    private Double electricMeterValue;
    private Double waterCharge;
    private Double electricCharge;
    private Double otherCharges;
    private Double totalAmount;
    private LocalDate billingMonth;

    public BillingDTO(Billing billing) {
        this.roomId = billing.getRoom() != null ? billing.getRoom().getId() : null;
        this.roomNumber = billing.getRoom() != null ? billing.getRoom().getRoomNumber() : "N/A"; // ✅ กัน `null`
        this.waterCharge = billing.getWaterBill() != null ? billing.getWaterBill().doubleValue() : 0.0;
        this.electricCharge = billing.getElectricBill() != null ? billing.getElectricBill().doubleValue() : 0.0;
        this.totalAmount = billing.getTotalBill() != null ? billing.getTotalBill().doubleValue() : 0.0;
        this.billingMonth = LocalDate.parse(billing.getMonth() + "-01"); // ✅ แปลง String เป็น LocalDate
    }

    // ✅ Getters & Setters
    public Long getRoomId() {
        return roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Double getWaterCharge() {
        return waterCharge;
    }

    public Double getElectricCharge() {
        return electricCharge;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getBillingMonth() {
        return billingMonth;
    }

    @Override
    public String toString() {
        return "BillingDTO{" +
                "roomId=" + roomId +
                ", roomNumber='" + roomNumber + '\'' +
                ", waterCharge=" + waterCharge +
                ", electricCharge=" + electricCharge +
                ", totalAmount=" + totalAmount +
                ", billingMonth=" + billingMonth +
                '}';
    }
}
