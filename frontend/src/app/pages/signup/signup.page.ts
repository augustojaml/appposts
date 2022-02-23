import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastController } from '@ionic/angular';
import { CreateUserDTO } from 'src/dto/user.dto';
import { UsersServices } from 'src/services/users.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.page.html',
  styleUrls: ['./signup.page.scss'],
})
export class SignupPage implements OnInit {
  createUser: CreateUserDTO = {
    name: '',
    email: '',
    password: '',
  };

  constructor(
    private route: Router,
    private usersService: UsersServices,
    private toastController: ToastController
  ) {}

  registrationCompleted() {
    this.usersService.createUser(this.createUser).subscribe(
      (response) => {
        this.toast('Cadastro', 'Usuário cadastro com sucesso');
        this.route.navigate(['/registration-completed']);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  async toast(toastHeader: string, message: string, type?: 'success') {
    const toast = await this.toastController.create({
      header: toastHeader,
      message: '<strong>' + message + '</strong>',
      position: 'top',
      animated: true,
      color: type,
      duration: 2000,
    });
    toast.present();
  }

  ngOnInit() {}
}
