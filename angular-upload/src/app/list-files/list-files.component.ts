import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { FilesService } from './../files.service';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';

import { DomSanitizer } from '@angular/platform-browser';
import { File } from '../shared/interfaces/file';
import { Router } from '@angular/router';
import { NameRoute } from '../shared/classes/name-route';

@Component({
  selector: 'app-list-files',
  templateUrl: './list-files.component.html',
  styleUrls: ['./list-files.component.css']
})
export class ListFilesComponent implements OnInit, OnChanges {

  @Input('active') active: boolean = false;
  files: File = {} as File;
  error: string = "";
  loading:boolean = false;

  constructor(private domSanitizer: DomSanitizer, private service: FilesService, private router: Router, private nameRoute: NameRoute) { }

  ngOnChanges(change: SimpleChanges){
    if(this.active == true){
      this.getFilesList();
      // this.getFilesListBase64();
      this.active = false;
    }
  }


  ngOnInit(): void {
    this.getFilesList();
  }


  getFilesList(): void{
    let token: string = this.service.readCookies("token");
    this.loading = true;
    this.error = "";
    
    let sub: Subscription = this.service.getFilesList(token).subscribe({
      next:(f)=>{
        this.files! = f;
      },
      error:(e)=>{
        if(e['status'] == 401){
          this.error = e.headers.get('error');
        }else{
          this.error = "Erro ao encontrar os conteudos. Numero: "+e['status'];
        }
        console.log(e['error']);
        this.loading = false;
      },
      complete:()=>{
        sub.unsubscribe();
        this.active = false;
        this.loading = false;
      }
    });
  }


  getFilesListBase64(): void{
    let token: string = this.service.readCookies("token");
    let sub: Subscription = this.service.getFilesListBase64(token).subscribe({
      next:(f)=>{
        this.files! = f;
      },
      error:()=>{
        this.error = "Erro ao pegar  o conteudo";
      },
      complete:()=>{
        this.error = "";
        this.files.files?.forEach((f)=>{
          f.path = this.domSanitizer.bypassSecurityTrustResourceUrl("data:image/jpg;base64,"+f.path);
        });
        sub.unsubscribe();
        this.active = false;
      }
    });
  }

  //OUTPUT DEL usado para dar refresh ao deletar imagem
  refresh(refresh: boolean): void{
    if(refresh){
      this.getFilesList();
      // this.getFilesListBase64();
    }
  }

  //OUTPUT DEL mostra o erro caso nao consigo deletar
  errorDel(error: string){
    this.error = error;
  }

  redirectImage(imageId: number){
    let route: string = this.nameRoute.getRoute('procurar');
    console.log(route);
    this.router.navigate([route + imageId]);
  }

}
