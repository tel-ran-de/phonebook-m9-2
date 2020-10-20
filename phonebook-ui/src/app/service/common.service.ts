import {Injectable} from '@angular/core';
import * as _ from 'lodash-es';

@Injectable({
  providedIn: 'root'
})
export class CommonService {
  constructor() { }

  getDropDownText(code, object){
    return _.filter(object, function (o) {
      return (_.includes(code, o.code));
    });
  }

}
