import { SafeResourceUrl } from '@angular/platform-browser';

interface SendPostDTO {
  title: string;
  body: string;
  image?: string | SafeResourceUrl;
  file?: Blob;
  fileName?: string;
}

export { SendPostDTO };
