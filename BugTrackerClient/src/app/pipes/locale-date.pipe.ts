import {Pipe, PipeTransform} from "@angular/core";
import {AppConstants} from "../constants/app-constants";

@Pipe({
  name: 'localedate'
})
export class LocaleDatePipe implements PipeTransform {
  transform(date: Date, ...args: any[]): string {
    if (date == null) {
      return 'N/A';
    }
    return date.toLocaleDateString(AppConstants.LOCALES_ARGUMENT, AppConstants.DATE_TIME_FORMAT_OPTIONS);
  }

}
