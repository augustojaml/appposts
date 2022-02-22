import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthServices } from 'src/services/auth.service';
import { LocalStorageService } from 'src/services/local-storage.service';
import { API_CONFIG } from '../../../config/api.config';

interface ProfilePost {
  id: string;
  name: string;
  avatar: string;
}

@Component({
  selector: 'app-posts',
  templateUrl: './posts.page.html',
  styleUrls: ['./posts.page.scss'],
})
export class PostsPage implements OnInit {
  profilePost: ProfilePost = {
    id: '',
    name: '',
    avatar: '',
  };

  constructor(
    private localStorageService: LocalStorageService,
    private router: Router,
    private authService: AuthServices
  ) {}

  ionViewWillEnter() {
    if (!this.localStorageService.getLocalUser()) {
      this.router.navigate(['/signin']);
    }

    this.authService.findMe().subscribe(
      (response: any) => {
        this.profilePost.id = response.id;
        this.profilePost.name = response.name;
        this.profilePost.avatar = response?.avatar
          ? `${API_CONFIG.fileURL}/avatar/${response.avatar}`
          : null;
      },
      (error) => {
        this.localStorageService.setLocalUser(null);
        this.router.navigate(['/signin']);
      }
    );
  }

  ngOnInit() {}
}
