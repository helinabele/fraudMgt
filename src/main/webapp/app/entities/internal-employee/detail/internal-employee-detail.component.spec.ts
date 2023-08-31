import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InternalEmployeeDetailComponent } from './internal-employee-detail.component';

describe('InternalEmployee Management Detail Component', () => {
  let comp: InternalEmployeeDetailComponent;
  let fixture: ComponentFixture<InternalEmployeeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternalEmployeeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ internalEmployee: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(InternalEmployeeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InternalEmployeeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load internalEmployee on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.internalEmployee).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
