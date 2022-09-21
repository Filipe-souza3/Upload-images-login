import { FindFileComponent } from './find-file.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingModule } from '../shared/loading/loading.module';



@NgModule({
  declarations: [
    FindFileComponent
  ],
  imports: [
    CommonModule,
    LoadingModule
  ],
  exports:[
    FindFileComponent
  ]
})
export class FindFileModule { }
