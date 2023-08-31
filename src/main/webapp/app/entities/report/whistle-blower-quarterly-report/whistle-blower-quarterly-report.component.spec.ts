import { ComponentFixture, TestBed } from "@angular/core/testing";
import { WhistleBlowerQuarterlyReportComponent } from "./whistle-blower-quarterly-report.component"
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe('WhistleBlowerQuarterlyReportComponent', () => {
    let component: WhistleBlowerQuarterlyReportComponent;
    let fixture: ComponentFixture<WhistleBlowerQuarterlyReportComponent>

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [ WhistleBlowerQuarterlyReportComponent, HttpClientTestingModule]
        })
        .compileComponents();

        fixture = TestBed.createComponent(WhistleBlowerQuarterlyReportComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
})
