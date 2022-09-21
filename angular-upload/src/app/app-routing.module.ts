import { LoadingComponent } from './shared/loading/loading.component';
import { LoginComponent } from './login/login.component';
import { ListFilesComponent } from './list-files/list-files.component';
import { FormComponent } from './form/form.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FindFileComponent } from './find-file/find-file.component';

const routes: Routes = [
  {path:'arquivos',component:FormComponent, data:{name:'index'}},
  {path:'arquivos/lista', component:ListFilesComponent,  data:{name:'lista'}},
  {path:'arquivos/procurar/:id',component:FindFileComponent,  data:{name:'procurar', cleanPath:"arquivos/procurar/"}},
  {path:'login', component:LoginComponent, data:{name:'login'}},
  {path:'', redirectTo:'login', pathMatch:'full', data:{name:'redirect'}},
  // {path:'arquivos/delete', component:BtnDeleteComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
