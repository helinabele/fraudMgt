import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OutstandingQuarterlySummaryReportComponent } from './outstanding-quarterly-summary-report.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('OutstandingQuarterlySummaryReportComponent', () => {
  let component: OutstandingQuarterlySummaryReportComponent;
  let fixture: ComponentFixture<OutstandingQuarterlySummaryReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ OutstandingQuarterlySummaryReportComponent, HttpClientTestingModule ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OutstandingQuarterlySummaryReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
