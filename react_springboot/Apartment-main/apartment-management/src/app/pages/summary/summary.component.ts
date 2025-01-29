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
  today = new Date().toLocaleDateString('th-TH', { year: 'numeric', month: 'long', day: 'numeric' });

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.fetchSummary();
  }

  fetchSummary(): void {
    this.apiService.getRooms().subscribe((data) => {
      this.summaryData = data.map((room: any) => {
        const billing = room.billings.length > 0 ? room.billings[0] : null;
        return {
          roomNumber: room.roomNumber, 
          waterBill: billing ? billing.waterBill : 0,
          electricBill: billing ? billing.electricBill : 0,
          totalBill: billing ? billing.totalBill : 0
        };
      });
  
      console.log("📢 ข้อมูลที่จัดรูปแบบแล้ว:", this.summaryData);
    });
  }
  

 
}
