import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getRooms(): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/rooms`);
  }  

  saveWaterMeterData(data: any): Observable<any> {
    console.log("📢 Data ที่กำลังส่งไปยัง API:", JSON.stringify(data, null, 2));
  
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
  
    return this.http.post(`${this.baseUrl}/meters/water`, data, { headers, responseType: 'text' });
  }
  

  saveElectricMeterData(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/electric-meter`, data);
  }

  getSummary(): Observable<any> {
    return this.http.get(`${this.baseUrl}/summary`);
  }

  getBilling(): Observable<any> {
    return this.http.get(`${this.baseUrl}/billing`);
  }
}
