import { Routes } from '@angular/router';
import { WaterMeterComponent } from './pages/water-meter/water-meter.component';
import { ElectricMeterComponent } from './pages/electric-meter/electric-meter.component';
import { SummaryComponent } from './pages/summary/summary.component';
import { BillingComponent } from './pages/billing/billing.component';
import { LayoutComponent } from './shared/layout/layout.component';
import { LoginComponent } from './pages/login/login.component';

export const routes: Routes = [
    {
      path: '',
      component: LayoutComponent,
      children: [
        { path: 'water-meter', component: WaterMeterComponent },
        { path: 'electric-meter', component: ElectricMeterComponent },
        { path: 'summary', component: SummaryComponent },
        { path: 'billing', component: BillingComponent },
        { path: 'login', component: LoginComponent },
        { path: '', redirectTo: 'login', pathMatch: 'full' },
      ],
    },
  ];

