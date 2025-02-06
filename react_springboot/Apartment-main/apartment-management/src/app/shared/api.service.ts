import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  login(data: any): Observable<any> {
    console.log('📢 Data ที่กำลังส่งไปยัง API:', JSON.stringify(data, null, 2));
    return this.http.post(`${this.baseUrl}/auth/login`, data);
  }
  logout(): void {
    localStorage.removeItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    return !!this.getToken(); // ✅ ตรวจสอบว่ามี Token หรือไม่
  }

  getRooms(): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/rooms`);
  }

  saveWaterMeterData(data: any): Observable<any> {
    console.log('📢 Data ที่กำลังส่งไปยัง API:', JSON.stringify(data, null, 2));

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    return this.http.post(`${this.baseUrl}/meters/water`, data, {
      headers,
      responseType: 'text',
    });
  }

  saveElectricMeterData(data: any): Observable<any> {
    console.log('📢 Data ที่กำลังส่งไปยัง API:', JSON.stringify(data, null, 2));

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    return this.http.post(`${this.baseUrl}/meters/electric`, data, {
      headers,
      responseType: 'text',
    });
  }

  generateBills(): Observable<any> {
    return this.http.post(`${this.baseUrl}/billing/generate`, {});
  }

  getSummary(month: string): Observable<any> {
    //return this.http.get(`${this.baseUrl}/summary`);
    return this.http.get(`${this.baseUrl}/billing/summary?month=${month}`);
  }

  getBilling(): Observable<any> {
    return this.http.get(`${this.baseUrl}/billing`);
  }
}
