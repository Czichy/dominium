import { NgModule } from '@angular/core';
import { InventurComponent } from './inventur.component';
import { GruppeComponent } from './gruppe/gruppe.component';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from '../shared/shared.module';
import { PostenComponent } from './gruppe/posten/posten.component';
import { AppCovalentModuleModule } from '../shared/app-covalent-module.module';
import { InventarComponent } from './inventar/inventar.component';
import { InventurService } from './inventur.service';

@NgModule({
    imports: [
        HttpClientModule,
        AppCovalentModuleModule,
        SharedModule,
    ],
    declarations: [
        InventurComponent,
        GruppeComponent,
        PostenComponent,
        InventarComponent,
    ],
    providers: [InventurService],
    exports: [InventurComponent]
})
export class InventurModule {
}
