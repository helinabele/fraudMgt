import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFraudKnowledgeManagement } from '../fraud-knowledge-management.model';

@Component({
  selector: 'jhi-fraud-knowledge-management-detail',
  templateUrl: './fraud-knowledge-management-detail.component.html',
})
export class FraudKnowledgeManagementDetailComponent implements OnInit {
  fraudKnowledgeManagement: IFraudKnowledgeManagement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fraudKnowledgeManagement }) => {
      this.fraudKnowledgeManagement = fraudKnowledgeManagement;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
