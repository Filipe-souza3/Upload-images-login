import { ListFilesModule } from './../list-files/list-files.module';
import { FormGroup, FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormComponent } from './form.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [
    FormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ListFilesModule
  ],
  exports: [
    FormComponent
  ]
})
export class FormModule { }
