import { Component, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { Router, RouterModule, NavigationEnd } from '@angular/router';
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs/operators';
import { ApiService } from '../../shared/api.service'; // ✅ นำเข้า ApiService

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
  ],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css'],
})
export class LayoutComponent {
  @ViewChild('sidenav') sidenav!: MatSidenav;

  constructor(private router: Router, private apiService: ApiService) {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        if (this.sidenav) {
          this.sidenav.close();
        }
      });
  }

  /** ✅ ยิง API และเปลี่ยนหน้า */
  handleSummaryClick(): void {
    console.log('🔄 กำลังสร้างบิล...');

    // ✅ ยิง API `generateBills()`
    this.apiService.generateBills().subscribe(
      response => {
        console.log('✅ สร้างบิลสำเร็จ:', response);
        
        // ✅ หลังจากยิง API เสร็จแล้วให้เปลี่ยนหน้าไป "/summary"
        this.router.navigate(['/summary']);
      },
      error => {
        console.error('❌ เกิดข้อผิดพลาดในการสร้างบิล:', error);
        alert('เกิดข้อผิดพลาดในการโหลดบิล!');
      }
    );
  }
}
