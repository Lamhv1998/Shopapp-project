import { Component } from '@angular/core';

@Component({
  selector: 'app-detail-product',
  templateUrl: './detail-product.component.html',
  styleUrls: ['./detail-product.component.scss'],
})
export class DetailProductComponent {
  changeImage(element: HTMLImageElement): void {
    const mainProductImage = document.getElementById(
      'main_product_image'
    ) as HTMLImageElement;
    if (mainProductImage) {
      mainProductImage.src = element.src;
    }
  }
}
