import { TestBed } from '@angular/core/testing';

import { WeeklyFraudReportComponent } from './weekly-fraud-report.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('WeeklyFraudReportComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ],
      imports: [HttpClientTestingModule],
      providers: [HttpClientTestingModule],
    }).compileComponents();
  });

  it('should create the component', () => {
    const fixture = TestBed.createComponent(WeeklyFraudReportComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});