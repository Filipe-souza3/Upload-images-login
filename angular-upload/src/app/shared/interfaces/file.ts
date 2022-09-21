import { SafeResourceUrl } from "@angular/platform-browser";

export interface File {
    id?: number,
    name: string,
    path: SafeResourceUrl,
    datetimeString?: string,
    files?: File[];

}
