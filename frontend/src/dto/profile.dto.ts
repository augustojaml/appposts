import { SafeResourceUrl } from '@angular/platform-browser';

interface ProfileDTO {
  id: string;
  name: string;
  email: string;
  avatar: string | SafeResourceUrl;
  file?: Blob;
  fileName?: string;
}

export { ProfileDTO };
