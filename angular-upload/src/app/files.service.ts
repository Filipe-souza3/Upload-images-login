
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { File } from './shared/interfaces/file';
import { User } from './shared/interfaces/user';
@Injectable({
  providedIn: 'root'
})
export class FilesService {

  constructor(private http: HttpClient) { }

  status = false;
  private url: string = "http://localhost:8080/UploadFiles/api/up";
  private httpOptions = {
    // headers: new HttpHeaders({
    //   'Content-Type': 'application/json',
    //   'responseType': 'text'
    // }),

    //pegar string como responda da requisição
    'responseType': 'text' as 'json',
    //aparecer cookies navegador
    withCredentials: true
  };

  insert(file: FormData): Observable<File> {
    return this.http.post<File>(this.url, file);
  }

  findFileById(id: number, token: string): Observable<File> {
    let url: string = this.url + "/show/" + id;
    return this.http.get<File>(url,{headers: new HttpHeaders().set('token', token)});
  }

  getFilesList(token: string): Observable<File> {
    console.log(token);
    let url: string = this.url + "/list";
    return this.http.get<File>(url,{headers: new HttpHeaders().set('token', token)});
  }

  deleteFile(fileId: number, token: string): Observable<File> {
    let url: string = this.url + "/delete/" + fileId;
    return this.http.delete<File>(url,{headers: new HttpHeaders().set('token', token)});
  }

  login(user: User): Observable<any> {
    let url: string = this.url + "/login";
    return this.http.post<any>(url, user, this.httpOptions);
  }

  ///////////////////////BASE64//////////////////////////////////////
  findFileByIdBase64(id: number): Observable<File> {
    let url: string = this.url + "/showBase64/" + id;
    return this.http.get<File>(url);
  }

  getFilesListBase64(token: string): Observable<File> {
    console.log(token);
    let url: string = this.url + "/listBase64";
    return this.http.get<File>(url,{headers: new HttpHeaders().set('token', token)});
  }

  deleteFileBase64(fileId: number, token: string): Observable<File> {
    let url: string = this.url + "/delete/" + fileId;
    return this.http.delete<File>(url,{headers: new HttpHeaders().set('token', token)});
  }
  ///////////////////////BASE64//////////////////////////////////////


  readCookies(cookieName: string): string{
    let result:string ="";
    if(document.cookie != null && document.cookie != ""){
      let cookies: String[] = document.cookie.split("; ");
      cookies.forEach(
        (cookie)=>{
          let cookiesSplit: string[] = cookie.split("=",2);
          let a = cookiesSplit[0].toString();
          if(a == cookieName){
            console.log(cookiesSplit[1]);
            result = cookiesSplit[1];
          }
          console.log(cookiesSplit[0]);
        }
      );
    }
    return result;
  }

}
