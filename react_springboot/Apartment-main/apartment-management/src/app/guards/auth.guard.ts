import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { ApiService } from '../shared/api.service';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private apiService: ApiService, private router: Router) {}

  canActivate(): boolean {
    if (!this.apiService.isLoggedIn()) {
      alert('กรุณาเข้าสู่ระบบก่อน');
      this.router.navigate(['/login']); // ✅ เปลี่ยนไปหน้า Login ถ้ายังไม่ได้ Login
      return false;
    }
    return true;
  }
}
