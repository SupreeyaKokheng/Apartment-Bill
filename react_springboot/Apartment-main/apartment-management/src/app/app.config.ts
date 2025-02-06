import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';
import { ReactiveFormsModule } from '@angular/forms';
import { ApiService } from './shared/api.service';

export const appConfig: ApplicationConfig = {
  providers: [
    ApiService,
    provideRouter(routes), // เชื่อม Routing
    provideHttpClient(), // สำหรับเรียก API
    importProvidersFrom(ReactiveFormsModule), // ✅ เพิ่มตรงนี้
  ],
};
