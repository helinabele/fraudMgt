import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { IFraudKnowledgeManagement } from 'app/entities/fraud-knowledge-management/fraud-knowledge-management.model';
import { FraudKnowledgeManagementService } from 'app/entities/fraud-knowledge-management/service/fraud-knowledge-management.service';

@Component({
  selector: 'jhi-weekly-fraud-report',
  standalone: true,
  templateUrl: './weekly-fraud-report.component.html',
  styleUrls: ['./weekly-fraud-report.component.scss'],
  imports: [CommonModule, ReactiveFormsModule]
})
export class WeeklyFraudReportComponent implements OnInit {
  fraudKnowledgeManagements?: IFraudKnowledgeManagement[];
  isLoading = false;

  constructor(
    private formBuilder: FormBuilder,
    private fraudKnowledgeManagementService: FraudKnowledgeManagementService
  ) { }

  ngOnInit(): void {
    this.fraudKnowledgeManagementService
    .getFraudKnowledgeManagements()
    .subscribe(
      fraudKnowledgeManagements => {
        this.fraudKnowledgeManagements = fraudKnowledgeManagements;
      },
    );
    // this.search();
  }

  load(): void{
    this.ngOnInit;
  }
}
