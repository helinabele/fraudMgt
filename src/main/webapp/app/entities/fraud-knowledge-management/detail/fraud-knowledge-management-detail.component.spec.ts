import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FraudKnowledgeManagementDetailComponent } from './fraud-knowledge-management-detail.component';

describe('FraudKnowledgeManagement Management Detail Component', () => {
  let comp: FraudKnowledgeManagementDetailComponent;
  let fixture: ComponentFixture<FraudKnowledgeManagementDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FraudKnowledgeManagementDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ fraudKnowledgeManagement: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(FraudKnowledgeManagementDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FraudKnowledgeManagementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fraudKnowledgeManagement on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.fraudKnowledgeManagement).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
