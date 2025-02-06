import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';
import { ReactiveFormsModule } from '@angular/forms';


export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes), // เชื่อม Routing
    provideHttpClient(),    // สำหรับเรียก API
    // importProvidersFrom(ReactiveFormsModule) // ✅ เพิ่มตรงนี้
 
  ]
};
