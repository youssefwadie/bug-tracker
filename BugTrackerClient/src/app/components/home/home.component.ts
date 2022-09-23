import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  active: string = 'dashboard';

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    console.log('home...');
    this.setCurrentPage();
  }


  private setCurrentPage(): void {
    const currentPath = this.route.snapshot.url[0]?.path;
    if (currentPath == null) return;
    if (currentPath === "dashboard" || currentPath === "tickets" || currentPath === "administration") {
      this.active = currentPath;
    }
  }

}
