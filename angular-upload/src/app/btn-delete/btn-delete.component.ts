import { FilesService } from './../files.service';
import { Component, Input, OnInit, OnChanges, Output, EventEmitter } from '@angular/core';
import { File } from '../shared/interfaces/file';

@Component({
  selector: 'app-btn-delete',
  templateUrl: './btn-delete.component.html',
  styleUrls: ['./btn-delete.component.css']
})
export class BtnDeleteComponent implements OnInit {

  @Input('fileId') fileId:number = 0;
  @Output('refresh') refresh:EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output('errorDel') errorDel:EventEmitter<string> = new EventEmitter<string>();
  file: File = {} as File;
  error: string = "";

  constructor(private service: FilesService) { }


  ngOnInit(): void {
  }

  deleteFile(){
    // this.refresh
    let token: string = this.service.readCookies('token');

    this.service.deleteFile(this.fileId, token).subscribe({
      next:(f)=>{
        this.file = f;
      },
      error:(e)=>{
        if(e['status'] == 501){
          this.errorDel.emit(e['error']);
        }else{
          this.errorDel.emit("Erro ao deletar. Numero: "+e['status']);
        }
      },
      complete:()=>{
        this.error = "";
        this.fileId = 0;
        this.refresh.emit(true);
      }
    });
    this.refresh.emit(false);
  }

}
