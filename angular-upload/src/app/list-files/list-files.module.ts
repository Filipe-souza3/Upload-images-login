import { BtnDeleteModule } from './../btn-delete/btn-delete.module';
import { ListFilesComponent } from './list-files.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingModule } from '../shared/loading/loading.module';



@NgModule({
  declarations: [
    ListFilesComponent
  ],
  imports: [
    CommonModule,
    BtnDeleteModule,
    LoadingModule
  ],
  exports:[
    ListFilesComponent
  ]
})
export class ListFilesModule { }
