package com.apartment.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;

    private String month;

    private BigDecimal waterBill; // ค่าใช้จ่ายน้ำ
    private BigDecimal electricBill; // ค่าใช้จ่ายไฟฟ้า
    private BigDecimal totalBill; // รวมค่าใช้จ่ายทั้งหมด

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getWaterBill() {
        return waterBill;
    }

    public void setWaterBill(BigDecimal waterBill) {
        this.waterBill = waterBill;
    }

    public BigDecimal getElectricBill() {
        return electricBill;
    }

    public void setElectricBill(BigDecimal electricBill) {
        this.electricBill = electricBill;
    }

    public BigDecimal getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(BigDecimal totalBill) {
        this.totalBill = totalBill;
    }
}
