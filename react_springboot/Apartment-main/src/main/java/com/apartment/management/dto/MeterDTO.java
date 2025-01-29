package com.apartment.management.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class MeterDTO {
    private Long roomId;
    private Double meterValue;

    @JsonFormat(pattern = "yyyy-MM-dd") // ✅ ทำให้ Spring Boot อ่าน `recordDate` จาก JSON
    private LocalDate recordDate;

    // Getters & Setters
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Double getMeterValue() {
        return meterValue;
    }

    public void setMeterValue(Double meterValue) {
        this.meterValue = meterValue;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }
}
