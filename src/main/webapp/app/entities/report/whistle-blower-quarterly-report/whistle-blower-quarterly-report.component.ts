import { Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule } from "@angular/forms";
import { WhistleBlowerReportService } from "app/entities/whistle-blower-report/service/whistle-blower-report.service";
import { IWhistleBlowerReport } from "app/entities/whistle-blower-report/whistle-blower-report.model";

@Component({
    selector: 'jhi-whistle-blower-report',
    standalone: true,
    templateUrl: './whistle-blower-quarterly-report.component.html',
    styleUrls: ['./whistle-blower-quarterly-report.component.scss'],
    imports: [CommonModule, ReactiveFormsModule],
})
export class WhistleBlowerQuarterlyReportComponent implements OnInit {
    whistleBlowerReport?: IWhistleBlowerReport[];
    isLoading = false;

    constructor(
        private whistleBlowerReportService: WhistleBlowerReportService,) { }

    ngOnInit(): void {
        this.whistleBlowerReportService
            .getwhistleBlowerReports()
            .subscribe(data => {
                this.whistleBlowerReport = data
            })
    }

    load(): void {
        this.ngOnInit();
    }
}