import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SendPostDTO } from 'src/dto/send-post.dto';
import { Camera, CameraResultType } from '@capacitor/camera';
import { DomSanitizer } from '@angular/platform-browser';
import { PostsServices } from 'src/services/posts.services';

@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.page.html',
  styleUrls: ['./new-post.page.scss'],
})
export class NewPostPage implements OnInit {
  post: SendPostDTO = {
    title: '',
    body: '',
  };

  constructor(
    private router: Router,
    private postsService: PostsServices,
    private sanitizer: DomSanitizer
  ) {}

  async takePicture() {
    const image = await Camera.getPhoto({
      quality: 20,
      allowEditing: true,
      resultType: CameraResultType.Uri,
      width: 500,
    });

    this.post.file = await fetch(image.webPath).then((img) => img.blob());
    this.post.fileName = `${new Date().getTime()}.${image.format}`;
    this.post.image = image.webPath;
  }

  backToPost() {
    this.router.navigate(['/posts']);
  }

  sendPost() {
    this.postsService.createPost(this.post).subscribe(
      (response) => {
        this.router.navigate(['/posts']);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  removePicture() {
    this.post.image = undefined;
  }

  ngOnInit() {}
}
