import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MonthlyFraudAttemptComponent } from './monthly-fraud-attempt.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('MonthlyFraudAttemptComponent', () => {
  let component: MonthlyFraudAttemptComponent;
  let fixture: ComponentFixture<MonthlyFraudAttemptComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ MonthlyFraudAttemptComponent, HttpClientTestingModule ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MonthlyFraudAttemptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
