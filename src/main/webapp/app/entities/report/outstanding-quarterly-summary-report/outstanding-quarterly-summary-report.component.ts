import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IFraudKnowledgeManagement } from 'app/entities/fraud-knowledge-management/fraud-knowledge-management.model';
import { FraudKnowledgeManagementService } from 'app/entities/fraud-knowledge-management/service/fraud-knowledge-management.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FraudKnowledgeManagementSortField } from '../fraud-knowledge-management-sort-field.type';

@Component({
  selector: 'jhi-outstanding-quarterly-summary-report',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './outstanding-quarterly-summary-report.component.html',
  styleUrls: ['./outstanding-quarterly-summary-report.component.scss'],
  providers: [FormBuilder],
})
export class OutstandingQuarterlySummaryReportComponent implements OnInit {
  outstandingFraud?: IFraudKnowledgeManagement[];
  searchForm: FormGroup;
  totalItems = 0;
  pageSize = 10;
  currentPage = 1;
  sortField: FraudKnowledgeManagementSortField = '';
  isLoading = false;

  constructor(
    private formBuilder: FormBuilder,
    private outstandingFraudsService: FraudKnowledgeManagementService,

  ) {
    this.searchForm = this.formBuilder.group({
      search: [''],
      property: ['reportNumber']
    });
  }

  ngOnInit(): void {
    this.outstandingFraudsService
      .getFraudKnowledgeManagements()
      .subscribe(data => {
        this.outstandingFraud = data
      })
  }
/*   search(): void {
    const search = this.searchForm.value.search;
    const property = this.searchForm.value.property;
    this.outstandingFraudsService
      .searchFraudKnowledgeManagements(search, property, this.currentPage - 1, this.pageSize.toString())
      .subscribe((response) => {
        this.outstandingFraud = response.content;
        this.totalItems = response.totalElements;
      });
  } 
  cancelSearch(): void {
    this.searchForm.reset();
    this.search();
  }*/

  load(): void {
    this.ngOnInit();
  }
}
