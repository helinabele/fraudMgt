import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWhistleBlowerReport } from '../whistle-blower-report.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-whistle-blower-report-detail',
  templateUrl: './whistle-blower-report-detail.component.html',
})
export class WhistleBlowerReportDetailComponent implements OnInit {
  whistleBlowerReport: IWhistleBlowerReport | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ whistleBlowerReport }) => {
      this.whistleBlowerReport = whistleBlowerReport;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
