import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup } from '@angular/forms';

// Interface สำหรับโครงสร้างของฟอร์ม
interface ElectricMeterForm {
  [key: string]: any; // key เป็น string, value เป็น any
}

@Component({
  selector: 'app-electric-meter',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './electric-meter.component.html',
  styleUrls: ['./electric-meter.component.css'],
})
export class ElectricMeterComponent {
  electricMeterForm: FormGroup;
  rooms = Array.from({ length: 20 }, (_, i) => `Room ${i + 1}`); // สร้าง 20 ห้อง
  today = new Date().toLocaleDateString('th-TH', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  }); // แสดงวันที่ภาษาไทย

  date = new Date().toISOString().slice(0, 7); // วันที่ในรูปแบบ YYYY-MM

  constructor(private fb: FormBuilder) {
    // สร้าง FormGroup แบบไดนามิก
    this.electricMeterForm = this.fb.group(
      this.rooms.reduce((acc: ElectricMeterForm, _, i) => {
        acc[`room${i}`] = ['']; // เพิ่มฟิลด์แต่ละห้องในฟอร์ม
        return acc;
      }, {} as ElectricMeterForm) // กำหนด acc เป็น ElectricMeterForm
    );
  }

  // ฟังก์ชันแปลงข้อมูลฟอร์มเป็น array ของ object เฉพาะห้องที่มีการเปลี่ยนแปลง
  getFormattedData(): Array<{ roomNumber: string; reading: string; recordDate: string }> {
    const rawData = this.electricMeterForm.value; // ดึงข้อมูลจากฟอร์ม
    return this.rooms
      .map((room, index) => ({
        roomNumber: `${index + 1}`, // หมายเลขห้อง
        reading: rawData[`room${index}`], // ค่าที่กรอก
        recordDate: this.date, // วันที่บันทึก
      }))
      .filter((item) => item.reading.trim() !== ''); // กรองเฉพาะค่าที่ไม่ว่างเปล่า
  }

  submitElectricMeter(): void {
    const formattedData = this.getFormattedData(); // แปลงข้อมูล
    console.log('ข้อมูลที่เปลี่ยนแปลง:', formattedData); // แสดงเฉพาะข้อมูลที่เปลี่ยนแปลง
    alert('บันทึกข้อมูลสำเร็จ!');
  }
}
