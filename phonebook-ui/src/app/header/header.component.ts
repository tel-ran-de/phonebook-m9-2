import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn :boolean
  constructor() {
  }

  ngOnInit(): void {
    if (localStorage.getItem('email')){
      this.isLoggedIn = true;
    }
  }

}
