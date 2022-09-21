import { LoginComponent } from './login.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoadingModule } from '../shared/loading/loading.module';


@NgModule({
  declarations: [
    LoginComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,

    LoadingModule
  ],
  exports:[ // quem importa o modulo so encherga quem esta aqui dentro
    LoginComponent
  ]
})
export class LoginModule { }
