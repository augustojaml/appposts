import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PostDTO } from 'src/dto/post.dto';
import { AuthServices } from 'src/services/auth.service';
import { LocalStorageService } from 'src/services/local-storage.service';
import { PostsServices } from 'src/services/posts.services';
import { API_CONFIG } from '../../../config/api.config';

import Time from 'time-value';
import { LoadingController } from '@ionic/angular';

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

  apiConfig = API_CONFIG;

  posts: PostDTO[] = [];

  isLoading = true;

  isOpen = false;

  constructor(
    private localStorageService: LocalStorageService,
    private router: Router,
    private authService: AuthServices,
    private postsServices: PostsServices,
    public loadingCtrl: LoadingController
  ) {}

  findAllPosts(): void {
    this.postsServices.findAll().subscribe((response: PostDTO[]) => {
      this.posts = response;
      this.isLoading = false;
    });
  }

  ionViewWillEnter() {
    this.isLoading = true;
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

    this.findAllPosts();
  }

  newPost() {
    this.router.navigate(['/new-post']);
  }

  ngOnInit() {}
}
