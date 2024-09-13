import { Component, OnInit } from '@angular/core';

interface Product {
  id: number;
  name: string;
  description: string;
  imageUrl: string;
  price: number;
}
@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss',
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  constructor() {}
  ngOnInit(): void {
    this.products = [
      {
        id: 1,
        name: 'Product 1',
        description: 'Description for Product 1',
        imageUrl: 'https://via.placeholder.com/150',
        price: 100,
      },
      {
        id: 2,
        name: 'Product 2',
        description: 'Description for Product 2',
        imageUrl: 'https://via.placeholder.com/150',
        price: 200,
      },
      {
        id: 3,
        name: 'Product 3',
        description: 'Description for Product 3',
        imageUrl: 'https://via.placeholder.com/150',
        price: 300,
      },
      {
        id: 4,
        name: 'Product 4',
        description: 'Description for Product 3',
        imageUrl: 'https://via.placeholder.com/150',
        price: 300,
      },
      {
        id: 5,
        name: 'Product 5',
        description: 'Description for Product 3',
        imageUrl: 'https://via.placeholder.com/150',
        price: 300,
      },
      {
        id: 6,
        name: 'Product 6',
        description: 'Description for Product 3',
        imageUrl: 'https://via.placeholder.com/150',
        price: 300,
      },
      {
        id: 7,
        name: 'Product 7',
        description: 'Description for Product 3',
        imageUrl: 'https://via.placeholder.com/150',
        price: 300,
      },
      {
        id: 8,
        name: 'Product 8',
        description: 'Description for Product 3',
        imageUrl: 'https://via.placeholder.com/150',
        price: 300,
      },
    ];
  }
}
