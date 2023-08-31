import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IWhistleBlowerReport } from '../whistle-blower-report.model';
import { WhistleBlowerReportService } from '../service/whistle-blower-report.service';

import { WhistleBlowerReportRoutingResolveService } from './whistle-blower-report-routing-resolve.service';

describe('WhistleBlowerReport routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: WhistleBlowerReportRoutingResolveService;
  let service: WhistleBlowerReportService;
  let resultWhistleBlowerReport: IWhistleBlowerReport | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(WhistleBlowerReportRoutingResolveService);
    service = TestBed.inject(WhistleBlowerReportService);
    resultWhistleBlowerReport = undefined;
  });

  describe('resolve', () => {
    it('should return IWhistleBlowerReport returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultWhistleBlowerReport = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultWhistleBlowerReport).toEqual({ id: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultWhistleBlowerReport = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultWhistleBlowerReport).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IWhistleBlowerReport>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultWhistleBlowerReport = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultWhistleBlowerReport).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
