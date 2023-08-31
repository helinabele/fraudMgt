import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ExternalEmployeeDetailComponent } from './external-employee-detail.component';

describe('ExternalEmployee Management Detail Component', () => {
  let comp: ExternalEmployeeDetailComponent;
  let fixture: ComponentFixture<ExternalEmployeeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExternalEmployeeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ externalEmployee: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ExternalEmployeeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ExternalEmployeeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load externalEmployee on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.externalEmployee).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
