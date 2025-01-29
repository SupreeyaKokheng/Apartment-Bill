package com.apartment.management.dto;

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

    // Getters and Setters
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Double getWaterMeterValue() {
        return waterMeterValue;
    }

    public void setWaterMeterValue(Double waterMeterValue) {
        this.waterMeterValue = waterMeterValue;
    }

    public Double getElectricMeterValue() {
        return electricMeterValue;
    }

    public void setElectricMeterValue(Double electricMeterValue) {
        this.electricMeterValue = electricMeterValue;
    }

    public Double getWaterCharge() {
        return waterCharge;
    }

    public void setWaterCharge(Double waterCharge) {
        this.waterCharge = waterCharge;
    }

    public Double getElectricCharge() {
        return electricCharge;
    }

    public void setElectricCharge(Double electricCharge) {
        this.electricCharge = electricCharge;
    }

    public Double getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(Double otherCharges) {
        this.otherCharges = otherCharges;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getBillingMonth() {
        return billingMonth;
    }

    public void setBillingMonth(LocalDate billingMonth) {
        this.billingMonth = billingMonth;
    }

    // Additional Method to Calculate Total Amount
    public void calculateTotalAmount() {
        this.totalAmount = (waterCharge != null ? waterCharge : 0) +
                (electricCharge != null ? electricCharge : 0) +
                (otherCharges != null ? otherCharges : 0);
    }

    @Override
    public String toString() {
        return "BillingDTO{" +
                "roomId=" + roomId +
                ", roomNumber='" + roomNumber + '\'' +
                ", waterMeterValue=" + waterMeterValue +
                ", electricMeterValue=" + electricMeterValue +
                ", waterCharge=" + waterCharge +
                ", electricCharge=" + electricCharge +
                ", otherCharges=" + otherCharges +
                ", totalAmount=" + totalAmount +
                ", billingMonth=" + billingMonth +
                '}';
    }
}
