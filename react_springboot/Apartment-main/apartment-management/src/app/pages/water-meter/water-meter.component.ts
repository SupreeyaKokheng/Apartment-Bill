import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { ApiService } from '../../shared/api.service';

interface WaterMeterRecord {
  roomId: number;
  meterValue: number;
  recordDate: string;
}

@Component({
  selector: 'app-water-meter',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './water-meter.component.html',
  styleUrls: ['./water-meter.component.css'],
})
export class WaterMeterComponent implements OnInit {
  waterMeterForm: FormGroup;
  rooms: any[] = [];
  today = new Date().toLocaleDateString('th-TH', { year: 'numeric', month: 'long', day: 'numeric' });
  date = new Date().toISOString().slice(0, 10);

  pendingData: WaterMeterRecord[] = []; // ✅ อัปเดตตลอดเวลาที่พิมพ์

  constructor(private fb: FormBuilder, private apiService: ApiService) {
    this.waterMeterForm = this.fb.group({});
  }

  ngOnInit(): void {
    this.fetchRooms();
  }

  fetchRooms(): void {
    this.apiService.getRooms().subscribe((data) => {
      this.rooms = data;
      this.initializeForm();
    });
  }

  initializeForm(): void {
    this.waterMeterForm = this.fb.group(
      this.rooms.reduce((acc, room, i) => {
        acc[`room${i}`] = [''];
        return acc;
      }, {})
    );
  }

  /**
   * ✅ อัปเดตข้อมูลใน `pendingData` ทุกครั้งที่มีการพิมพ์
   */
  updatePendingData(index: number): void {
    const rawData = this.waterMeterForm.value;
    const room = this.rooms[index];
    const newValue = rawData[`room${index}`];

    if (room && newValue.trim() !== '') {
      const updatedData: WaterMeterRecord = {
        roomId: room.id,
        meterValue: Number(newValue),
        recordDate: this.date,
      };

      // ✅ ค้นหา Index ที่มีอยู่แล้ว และอัปเดตข้อมูล
      const existingIndex = this.pendingData.findIndex((item) => item.roomId === updatedData.roomId);
      if (existingIndex === -1) {
        this.pendingData.push(updatedData);
      } else {
        this.pendingData[existingIndex] = updatedData;
      }
      console.log('📌 ข้อมูลที่กำลังบันทึก:', this.pendingData);
    }
  }

  /**
   * ✅ เมื่อกด `Enter` ให้โฟกัสไปยัง Input ถัดไป
   */
  handleEnter(event: Event, index: number): void {
    const keyboardEvent = event as KeyboardEvent;
    keyboardEvent.preventDefault();

    const nextIndex = index + 1;
    if (nextIndex < this.rooms.length) {
      setTimeout(() => {
        const nextInput = document.getElementById(`room${nextIndex}`) as HTMLInputElement;
        if (nextInput) {
          nextInput.focus();
        }
      }, 100);
    }
  }

  /**
   * ✅ กดปุ่ม `Submit` เพื่อส่งข้อมูลไป API
   */
  submitWaterMeter(): void {
    if (this.pendingData.length > 0) {
      console.log('📢 กำลังส่งข้อมูลไปยัง API:', JSON.stringify(this.pendingData, null, 2));

      this.apiService.saveWaterMeterData(this.pendingData).subscribe(
        (response) => {
          console.log('✅ บันทึกสำเร็จ:', response);
          alert('บันทึกข้อมูลสำเร็จ!');
          this.pendingData = []; // เคลียร์ค่าที่บันทึกแล้ว
          this.waterMeterForm.reset(); // ล้างฟอร์ม
        },
        (error) => {
          console.error('❌ เกิดข้อผิดพลาด:', error);
          alert('เกิดข้อผิดพลาด: ' + error.message);
        }
      );
    } else {
      alert('ไม่มีข้อมูลที่ต้องบันทึก');
    }
  }
}
