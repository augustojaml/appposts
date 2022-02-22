import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastController } from '@ionic/angular';
import { CredentialDTO } from 'src/dto/credential.dto';
import { AuthServices } from 'src/services/auth.service';
import { LocalStorageService } from 'src/services/local-storage.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.page.html',
  styleUrls: ['./signin.page.scss'],
})
export class SignInPage implements OnInit {
  credential: CredentialDTO = {
    email: 'brawziin@gmail.com',
    password: '1234',
  };

  constructor(
    private route: Router,
    private toastController: ToastController,
    private authService: AuthServices
  ) {}

  async signIn() {
    this.authService.login(this.credential).subscribe(
      (response) => {
        this.authService.setLocalUser(response.headers.get('Authorization'));
        this.route.navigate(['/posts']);
      },
      (error) =>
        this.toast('Error de autenticação', JSON.parse(error.error).message)
    );
  }

  async toast(toastHeader: string, error: string) {
    const toast = await this.toastController.create({
      header: toastHeader,
      message: '<strong>' + error + '</strong>',
      position: 'top',
      animated: true,
      color: 'danger',
      duration: 2000,
    });
    toast.present();
  }

  signUp() {
    this.route.navigate(['/signup']);
  }

  ngOnInit() {}
}
