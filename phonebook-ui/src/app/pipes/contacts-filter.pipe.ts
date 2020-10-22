import { Pipe, PipeTransform } from '@angular/core';
import {Contact} from "../model/contact";




@Pipe({
  name: 'contactsFilter'
})
export class ContactsFilterPipe implements PipeTransform {

  transform(contacts: Contact[], search: string = ''): Contact[] {
    if (!search.trim()) {
      return contacts;
    }

    return contacts.filter(contact => {
      return contact.firstName.toLowerCase().indexOf(search.toLowerCase()) !== -1 || contact.lastName.toLowerCase().indexOf(search.toLowerCase()) !== -1;
    });
  }

}
