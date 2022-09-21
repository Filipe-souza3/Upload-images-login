import { LoginModule } from './login/login.module';
import { ListFilesModule } from './list-files/list-files.module';
import { FormModule } from './form/form.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { FindFileModule } from './find-file/find-file.module';
import { NameRoute } from './shared/classes/name-route';
import { LoadingModule } from './shared/loading/loading.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,

    //my modules
    FormModule,
    ListFilesModule,
    HttpClientModule,
    FindFileModule,
    LoginModule,
    LoadingModule
  ],
  providers: [ // classes e outros, static - somente um para o projeto inteiro
    NameRoute
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
