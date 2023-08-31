import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IFraudKnowledgeManagement } from 'app/entities/fraud-knowledge-management/fraud-knowledge-management.model';
import { FraudKnowledgeManagementService } from 'app/entities/fraud-knowledge-management/service/fraud-knowledge-management.service';

@Component({
  selector: 'jhi-monthly-fraud-attempt',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './monthly-fraud-attempt.component.html',
  styleUrls: ['./monthly-fraud-attempt.component.scss'],
})
export class MonthlyFraudAttemptComponent implements OnInit {
fraudKnowledgeManagment?: IFraudKnowledgeManagement[];
isLoading = false;

  constructor(
    private fraudKnowledgeManagmentService: FraudKnowledgeManagementService
  ) { }

  ngOnInit(): void {
    this.fraudKnowledgeManagmentService.getFraudKnowledgeManagements()
    .subscribe(data => {
      this.fraudKnowledgeManagment = data
    })
  }

  load(): void{
    this.ngOnInit();
  }
}
