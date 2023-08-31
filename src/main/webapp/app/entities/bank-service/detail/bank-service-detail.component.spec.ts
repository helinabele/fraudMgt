import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankServiceDetailComponent } from './bank-service-detail.component';

describe('BankService Management Detail Component', () => {
  let comp: BankServiceDetailComponent;
  let fixture: ComponentFixture<BankServiceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BankServiceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bankService: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(BankServiceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BankServiceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bankService on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bankService).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
