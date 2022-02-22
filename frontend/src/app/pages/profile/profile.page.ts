import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { API_CONFIG } from 'src/config/api.config';
import { ProfileDTO } from 'src/dto/profile.dto';
import { AuthServices } from 'src/services/auth.service';
import { LocalStorageService } from 'src/services/local-storage.service';

import { Camera, CameraResultType } from '@capacitor/camera';
import { UsersServices } from 'src/services/users.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {
  profile: ProfileDTO = {
    id: '',
    name: '',
    email: '',
    avatar: '',
  };

  constructor(
    private router: Router,
    private location: Location,
    private authService: AuthServices,
    private localStorageService: LocalStorageService,
    private usersService: UsersServices,
    private sanitizer: DomSanitizer
  ) {}

  updateProfile() {
    this.usersService.updateUser(this.profile).subscribe(
      (response) => {
        console.log(response);
      },
      (error) => {
        console.log(error);
      }
    );
    // this.router.navigate(['/signin']);
  }

  goBack() {
    this.location.back();
  }

  logOut() {
    this.authService.logout();
    this.router.navigate(['/signin']);
  }

  ionViewWillEnter() {
    this.authService.findMe().subscribe(
      (response: ProfileDTO) => {
        this.profile.id = response.id;
        this.profile.name = response.name;
        this.profile.email = response.email;
        this.profile.avatar = response?.avatar
          ? `${API_CONFIG.fileURL}/avatar/${response.avatar}`
          : null;
      },
      (error) => {
        this.localStorageService.setLocalUser(null);
        this.router.navigate(['/signin']);
      }
    );
  }

  async takePicture() {
    const image = await Camera.getPhoto({
      quality: 90,
      allowEditing: true,
      resultType: CameraResultType.Uri,
    });

    this.profile.file = image.path;
    this.profile.avatar = image.webPath;
  }

  ngOnInit() {}
}
