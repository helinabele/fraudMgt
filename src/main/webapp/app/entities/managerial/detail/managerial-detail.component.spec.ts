import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ManagerialDetailComponent } from './managerial-detail.component';

describe('Managerial Management Detail Component', () => {
  let comp: ManagerialDetailComponent;
  let fixture: ComponentFixture<ManagerialDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagerialDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ managerial: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ManagerialDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ManagerialDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load managerial on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.managerial).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
