import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElectricMeterComponent } from './electric-meter.component';

describe('ElectricMeterComponent', () => {
  let component: ElectricMeterComponent;
  let fixture: ComponentFixture<ElectricMeterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ElectricMeterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElectricMeterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
