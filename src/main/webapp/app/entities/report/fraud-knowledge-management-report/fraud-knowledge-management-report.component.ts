import { Component, OnInit } from '@angular/core';
import { IFraudKnowledgeManagement } from 'app/entities/fraud-knowledge-management/fraud-knowledge-management.model';
import { FraudKnowledgeManagementService } from 'app/entities/fraud-knowledge-management/service/fraud-knowledge-management.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FraudKnowledgeManagementSortField } from '../fraud-knowledge-management-sort-field.type';


@Component({
  selector: 'jhi-fraud-knowledge-management-report',
  standalone: true,
  templateUrl: './fraud-knowledge-management-report.component.html',
  styleUrls: ['./fraud-knowledge-management-report.component.scss'],
  imports: [CommonModule, ReactiveFormsModule],
})
export class FraudKnowledgeManagementReportComponent implements OnInit {
  fraudKnowledgeManagements?: IFraudKnowledgeManagement[];
  searchForm: FormGroup;
  pageSize = 10;
  currentPage = 1;
  totalItems = 0;
  sortField: FraudKnowledgeManagementSortField = '';
  isLoading = false;
  
  public propertyMapping = {
    reportNumber: 1,
    fraudIncident: 2,
    actualIncident: 3,
    attemptIncident: 4,
    reasonForFailure: 5
  };

  constructor(
    private formBuilder: FormBuilder,
    private fraudKnowledgeManagementService: FraudKnowledgeManagementService
  ) {
    this.searchForm = this.formBuilder.group({
      search: [''],
      property: ['reportNumber']
    });
  }

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
  
  // search(): void {
  //   const search = this.searchForm.value.search;
  //   const property = this.searchForm.value.property;
  //   this.fraudKnowledgeManagementService
  //     .searchFraudKnowledgeManagements(search, property, this.currentPage - 1, this.pageSize.toString())
  //     .subscribe((response) => {
  //       this.fraudKnowledgeManagements = response.content;
  //       this.totalItems = response.totalElements;
  //     });
  // }
  load(): void{
    this.ngOnInit();
  }
  // search(): void {
  //   const search = this.searchForm.value.search;
  //   const property = this.searchForm.value.property;
  //   this.fraudKnowledgeManagementService
  //     .searchFraudKnowledgeManagements(search, property, this.currentPage - 1, this.pageSize.toString())
  //     .subscribe((response) => {
  //       this.fraudKnowledgeManagements = response.content;
  //       this.totalItems = response.totalElements;
  //     });
  // }

  /* searchByReportNumber(): void {
    const search = this.searchForm.value.search;
    const property = this.propertyMapping["reportNumber"];
    this.fraudKnowledgeManagementService
      .searchFraudKnowledgeManagements(search, property, this.currentPage - 1, this.pageSize.toString())
      .subscribe((response) => {
        this.fraudKnowledgeManagements = response.content;
        this.totalItems = response.totalElements;
      });
  }

  searchByFraudIncident(): void {
    const search = this.searchForm.value.search;
    const property = this.propertyMapping["fraudIncident"];
    this.fraudKnowledgeManagementService
      .searchFraudKnowledgeManagements(search, property, this.currentPage - 1, this.pageSize.toString())
      .subscribe((response) => {
        this.fraudKnowledgeManagements = response.content;
        this.totalItems = response.totalElements;
      });
  }

  searchByActualIncident(): void {
    const search = this.searchForm.value.search;
    const property = this.propertyMapping["actualIncident"];
    this.fraudKnowledgeManagementService
      .searchFraudKnowledgeManagements(search, property, this.currentPage - 1, this.pageSize.toString())
      .subscribe((response) => {
        this.fraudKnowledgeManagements = response.content;
        this.totalItems = response.totalElements;
      });
  }

  searchByAttemptIncident(): void {
    const search = this.searchForm.value.search;
    const property = this.propertyMapping["attemptIncident"];
    this.fraudKnowledgeManagementService
      .searchFraudKnowledgeManagements(search, property, this.currentPage - 1, this.pageSize.toString())
      .subscribe((response) => {
        this.fraudKnowledgeManagements = response.content;
        this.totalItems = response.totalElements;
      });
  }

  searchByReasonForFailure(): void {
    const search = this.searchForm.value.search;
    const property = this.propertyMapping["reasonForFailure"];
    this.fraudKnowledgeManagementService
      .searchFraudKnowledgeManagements(search, property, this.currentPage - 1, this.pageSize.toString())
      .subscribe((response) => {
        this.fraudKnowledgeManagements = response.content;
        this.totalItems = response.totalElements;
      });
  }

  cancelSearch(): void {
    this.searchForm.reset();
    this.search();
  } */
  onPageChange(event: any): void {
    this.currentPage = Number(event.pageIndex) + 1;
    this.pageSize = event.pageSize;
    //this.search();
  }

}