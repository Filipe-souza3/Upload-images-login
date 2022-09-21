import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class NameRoute {
    
    constructor(private router: Router) { };

    getRoute(routeName: string): string{
        let route:any = this.router.config.find((route) => routeName === route['data']!['name']!);
        if(route['data']['cleanPath']){
            return route['data']['cleanPath'];
        }else{
            return route['path'];
        }
        
    }
}
