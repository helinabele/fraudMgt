import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FraudKnowledgeManagementReportComponent } from './fraud-knowledge-management-report.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('FraudKnowledgeManagementReportComponent', () => {
  let component: FraudKnowledgeManagementReportComponent;
  let fixture: ComponentFixture<FraudKnowledgeManagementReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ],
      imports: [HttpClientTestingModule],
      providers: [HttpClientTestingModule],
    })
    .compileComponents();

    fixture = TestBed.createComponent(FraudKnowledgeManagementReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FraudKnowledgeManagementReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
