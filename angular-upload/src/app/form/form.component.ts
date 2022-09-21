import { File } from './../shared/interfaces/file';
import { Component, ChangeDetectorRef, OnInit, AfterContentChecked } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { FilesService } from '../files.service';
import { Router } from '@angular/router';
import { NameRoute } from '../shared/classes/name-route';



@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit, AfterContentChecked {

  file: File = {} as File;
  error:string = "";
  active: boolean = false;



  constructor(private service: FilesService, private cdr: ChangeDetectorRef, private router: Router, private nameRoute: NameRoute) { }

  
  ngOnInit(): void {
    // if(!this.service.status){
    if(this.service.readCookies("token") == ""){
      let route:string = this.nameRoute.getRoute("login");
      this.router.navigate([route]);
    }
  }
  
  ngAfterContentChecked(): void {
    this.cdr.detectChanges();
    this.active = false;
  }

  fg:FormGroup = new FormGroup({
    name: new FormControl('', [Validators.maxLength(11), Validators.required]),
    file: new FormControl('', Validators.required),
  });

  send(): void{
    if(this.fg.valid){

      // this.file.name = this.fg.controls['name'].value!;
      const formData = new FormData();
      this.error = "";
      
      // formData.append("name",this.fg.get("name")?.value!);
      // formData.append("file",this.fg.get('file')?.value!);
      formData.append("name",this.fg.controls['name'].value!);
      formData.append("file",this.fg.controls['file'].value!);

      let sub:Subscription = this.service.insert(formData).subscribe({
        next:(f)=>{
          this.file = f;
        },
        error:(e)=>{
          if(e['status'] == 501){
            this.error = e['error'];
          }else{
            this.error = "Erro ao tentar inserir. Numero: " + e['status'];
          }
          console.log(e['error']);
        },
        complete:()=>{
          this.error = "Inserido com sucesso";
          this.active = true;
          this.fg.reset();
          sub.unsubscribe();
        }
      });
    }else{
      this.error = "Há campos em branco";
    }
  }

  getFileInputForm(e:Event): void{
    console.log((e.target as HTMLFormElement)['files']);
    this.fg.controls['file'].setValue((e.target as HTMLFormElement)['files'][0]);
    
  }

}
