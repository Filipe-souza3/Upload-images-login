import { NameRoute } from './../shared/classes/name-route';
import { FilesService } from './../files.service';
import { FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { User } from '../shared/interfaces/user';
import { delay, isObservable, Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  login: FormControl = new FormControl('', [Validators.required]);
  senha: FormControl = new FormControl('', [Validators.required]);
  user: User = { } as User;
  loginCheck: string = "";
  error: string = "";
  loading: boolean = false;

  constructor(private service: FilesService, private router: Router, private nameRoute:NameRoute) { }
  
//  delay
// await new Promise(f => setTimeout(f, 1000))

  ngOnInit(): void {
  }

  send(): void{
    this.loading = true;
    if(this.login.valid && this.senha.valid){
      this.user.name = this.login.value!;
      this.user.password =this.senha.value!;

      let sub:Subscription = this.service.login(this.user).subscribe({
        next:(resp)=>{
          this.loginCheck = resp;
        },
        error:(e)=>{
          if(e['status'] == 401){
            this.error = e['error'];
          }else{
            this.error = "Erro ao tentar logar. Numero: "+e['status'];
          }
          console.log(e['error']);
          this.loading = false;
        },
        complete:()=>{   
          console.log(this.service.readCookies("token"));
          this.loading = false;

          if(this.loginCheck === "success"){
            this.service.status = true;
            let route = this.nameRoute.getRoute("index");
            this.router.navigate([route]);
            
          }else{
            this.error = "Campo em branco";
          }
        }
      });
    }else{
      this.loading = false;
      this.error = "Há campos em branco";
    }
    
  }


}
