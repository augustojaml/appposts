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
import { AlertController, ToastController } from '@ionic/angular';

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
    private sanitizer: DomSanitizer,
    public alertController: AlertController
  ) {}

  updateProfile() {
    this.usersService.updateUser(this.profile).subscribe(
      (response: any) => {
        this.alertUpdateProfile();
      },
      (error) => {
        console.log(error);
      }
    );
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
      quality: 30,
      allowEditing: true,
      resultType: CameraResultType.Uri,
    });

    this.profile.file = await fetch(image.webPath).then((img) => img.blob());
    this.profile.fileName = `${new Date().getTime()}.${image.format}`;
    this.profile.avatar = this.sanitizer.bypassSecurityTrustResourceUrl(
      image.webPath
    );
  }

  async alertUpdateProfile() {
    const alert = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Perfil atualizado!',
      message: 'Seu perfil foi atualizado com sucesso',
      buttons: [
        {
          text: 'Ok',
          id: 'confirm-button',
          handler: () => {
            this.router.navigate(['/posts']);
          },
        },
      ],
    });

    await alert.present();
  }

  ngOnInit() {}
}
