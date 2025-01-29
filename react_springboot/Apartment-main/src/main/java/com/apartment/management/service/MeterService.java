package com.apartment.management.service;

import com.apartment.management.model.ElectricMeter;
import com.apartment.management.model.WaterMeter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface MeterService {

    void saveWaterMeter(Long roomId, Double meterValue, LocalDate recordDate) throws Exception;

    void saveElectricMeter(Long roomId, Double meterValue, LocalDate recordDate) throws Exception;

    List<WaterMeter> getWaterMeterRecordsByRoomAndMonth(Long roomId, YearMonth month);

    List<ElectricMeter> getElectricMeterRecordsByRoomAndMonth(Long roomId, YearMonth month);

    List<ElectricMeter> getElectricMeterRecordsByMonth(YearMonth month);

    //List<?> getMeterRecordsByRoomNumber(String roomNumber) throws Exception; // เพิ่ม throws Exception
}
