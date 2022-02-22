import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'src/services/local-storage.service';
@Component({
  selector: 'app-splash',
  templateUrl: './splash.page.html',
  styleUrls: ['./splash.page.scss'],
})
export class SplashPage implements OnInit {
  constructor(
    private route: Router,
    private localStorageService: LocalStorageService
  ) {}

  ngOnInit() {
    setTimeout(() => {
      if (this.localStorageService.getLocalUser()) {
        this.route.navigate(['/posts']);
        return;
      }
      this.route.navigate(['/signin']);
    }, 5000);
  }
}
