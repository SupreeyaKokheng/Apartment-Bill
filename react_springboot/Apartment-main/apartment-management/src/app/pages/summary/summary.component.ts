import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../shared/api.service';
import { MatTableModule } from '@angular/material/table';


@Component({
  selector: 'app-summary',
  standalone: true,
  imports: [
    MatTableModule, // ใช้สำหรับ <table mat-table>
  ],
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css']
})
export class SummaryComponent implements OnInit {
  summaryData: any[] = [];
  displayedColumns: string[] = ['room', 'water', 'electric', 'total'];
  selectedMonth: string = this.getCurrentMonth(); // ค่าเริ่มต้นเป็นเดือนปัจจุบัน
  //today = new Date().toLocaleDateString('th-TH', { year: 'numeric', month: 'long' });

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.fetchSummary();
  }

/** ดึงข้อมูลสรุปของเดือนที่เลือก */
fetchSummary(): void {
  console.log(`🔍 กำลังดึงข้อมูลของเดือน: ${this.selectedMonth}`);
  this.apiService.getSummary(this.selectedMonth).subscribe((data) => {
    console.log("📊 ข้อมูลที่โหลด:", data);
    
    if (data.length === 0) {
      // 🔥 ถ้าไม่มีบิลในเดือนนี้ → โหลดรายชื่อห้องแล้วสร้างรายการใหม่
      this.apiService.getRooms().subscribe((rooms) => {
        console.log("🛠 ห้องที่โหลดจาก API:", rooms); // ตรวจสอบว่ามี roomNumber หรือไม่
        this.summaryData = rooms.map((room: any) => ({
          roomNumber: room.roomNumber, // ✅ ใช้ room.roomNumber เท่านั้นถ้า room เป็น Object
          waterBill: 0,
          electricBill: 0,
          totalBill: 0,
          month: this.selectedMonth,
        }));
        
      });
    } else {
      // ✅ ถ้ามีบิล → ใช้ข้อมูลที่ API ส่งมา
      this.summaryData = data;
    }
  });
}


   /** เมื่อผู้ใช้เลือกเดือนใหม่ */
   onMonthChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.selectedMonth = input.value;
    this.fetchSummary(); // โหลดข้อมูลใหม่
  }

  /** คืนค่าเดือนปัจจุบันในรูปแบบ YYYY-MM */
  private getCurrentMonth(): string {
    const today = new Date();
    return `${today.getFullYear()}-${String(today.getMonth() + 1).padStart(2, '0')}`;
  }
  

 
}
