import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {Contact} from "../model/contact";


@Injectable({providedIn: 'root'})
export class ContactsService {
  private readonly contactPath = '/api/contact/';
  private readonly country_code = '/api/country_code';
  private readonly contactPhonePath = '/api/phone/';
  private readonly contactAddressPath = '/api/address/';
  private readonly contactEmailPath = '/api/email/';
  public contacts: Contact[] = []


  constructor(private http: HttpClient) {
  }


  addAddress(item, contactId: number): Observable<Contact> {
    return this.http
      .post<Contact>(this.contactAddressPath, {
        contactId: contactId,
        zip: item.zip,
        country: item.country,
        city: item.city,
        street: item.street
      })
  }

  addContact(contact: Contact): Observable<Contact> {

    return this.http
      .post<Contact>(this.contactPath, contact)
  }

  addEmail(contact, contactId: number): Observable<Contact> {
    return this.http
      .post<Contact>(this.contactEmailPath, {
        contactId: contactId,
        email: contact.email,
      })
  }

  addPhone(contact: Contact, contactId: number): Observable<Contact> {
    return this.http
      .post<Contact>(this.contactPhonePath, {
        contactId: contactId,
        countryCode: contact.countryCode,
        phoneNumber: contact.phoneNumber,
      })
  }

  getAddress(id): Observable<Contact> {
    return this.http
      .get<Contact>(this.contactAddressPath + '/' + id)
  }

  getAllContacts(): Observable<Contact[]> {
    return this.http.get<Contact[]>(this.contactPath)
      .pipe(tap(contacts => this.contacts = contacts));
  }

  getContactById(id): Observable<Contact> {
    return this.http
      .get<Contact>(this.contactPath + '/' + id)
  }

  getCountry_code(): Observable<Contact> {
    return this.http
      .get<Contact>(this.country_code)
  }

  getEmail(id): Observable<Contact> {
    return this.http
      .get<Contact>(this.contactEmailPath + '/' + id)
  }

  getPhone(id): Observable<Contact> {
    return this.http
      .get<Contact>(this.contactPhonePath + '/' + id)
  }


  removeAddress(id: number) {
    return this.http.delete(this.contactAddressPath + id)

  }

  removeContact(id: number) {
    return this.http.delete(this.contactPath + id)

  }

  removeEmail(id: number) {
    return this.http.delete(this.contactEmailPath + id)

  }

  removePhone(id: number) {
    return this.http.delete(this.contactPhonePath + id)

  }

  updateAddress(contact, contactId, addressId) {
    return this.http
      .put<Contact>(this.contactAddressPath, {
        id: addressId,
        zip: contact.zip,
        country: contact.country,
        city: contact.city,
        street: contact.street,
        contactId: contactId
      })
  }

  updateContact(contact, contactId,) {
    return this.http
      .put<Contact>(this.contactPath, {
        id: contactId,
        firstName: contact.firstName,
        lastName: contact.lastName,
        description: contact.description,
      })
  }

  updateEmail(contact,contactId, emailId) {
    return this.http
      .put<Contact>(this.contactEmailPath, {
        id: emailId,
        email: contact.email,
        contactId: contactId
      })
  }


  updatePhone(contact, country_code,contactId, phoneId ) {
    return this.http
      .put<Contact>(this.contactPhonePath, {
        id: phoneId,
        countryCode: country_code,
        phoneNumber: contact.phoneNumber,
        contactId: contactId,
      })
  }
}

