import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TeamLeadDetailComponent } from './team-lead-detail.component';

describe('TeamLead Management Detail Component', () => {
  let comp: TeamLeadDetailComponent;
  let fixture: ComponentFixture<TeamLeadDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TeamLeadDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ teamLead: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(TeamLeadDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TeamLeadDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load teamLead on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.teamLead).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
