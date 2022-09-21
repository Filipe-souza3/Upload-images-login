import { FilesService } from './../files.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { File } from '../shared/interfaces/file';

import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-find-file',
  templateUrl: './find-file.component.html',
  styleUrls: ['./find-file.component.css']
})
export class FindFileComponent implements OnInit {

  private id: number = 0;
  file: File = { } as File;
  error: string = "";
  loading :boolean = false;
  card: boolean = true;

  constructor(private route: ActivatedRoute, private service: FilesService, private domSanitizer: DomSanitizer) { }
  

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.error = "";

    if(this.id !== null && this.id > 0){
      this.getFilebyId(this.id);
    }else{
      this.error = "Identificador invalido.";
    }
  }

  private getFilebyId(id:number):void{
    this.error = "";
    this.loading = true;
    let token: string = this.service.readCookies('token');
    this.service.findFileById(id, token).subscribe({
      next:(f)=>{
        this.file = f;
        this.card = false;
      },
      error:(e)=>{
        if(e['status'] == 501){
          this.error = e['error'];
        }else{
          this.error = "Erro ao tentar encontrar o conteudo. Numero: "+e['status'];
        }
        console.log(e);
        this.loading = false;
        this.card = false;
      },
      complete:()=>{
        this.loading = false;
        this.card = true;
        // this.file.path = this.domSanitizer.bypassSecurityTrustResourceUrl("data:image/jpg;base64,"+this.file.path);
      }
    });
  }
}
