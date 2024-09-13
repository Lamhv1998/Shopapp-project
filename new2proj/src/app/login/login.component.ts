import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user/user.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  phoneNumber: string = '';
  password: string = '';
  constructor(private router: Router, private userService: UserService) {}
  onPhoneChange() {
    console.log(`Phone Type: ${this.phoneNumber}`);
  }

  // Hàm đăng nhập người dùng
  login() {
    const loginDTO = {
      phone_number: this.phoneNumber,
      password: this.password,
    };
    this.userService.login(loginDTO).subscribe({
      next: (response: any) => {
        // Xử lý token JWT từ response thay vì xử lý JSON
        const token = response.text ? response.text : response;
        console.log('JWT Token:', token);
        // alert('Login sucssecs');
        // Lưu trữ token JWT vào LocalStorage hoặc nơi khác
        localStorage.setItem('jwt_token', token);
        this.router.navigate(['/dashboard']);
      },
      complete: () => {
        console.log('Login process completed');
      },
      error: (error: any) => {
        console.error('Login failed', error);
        // alert('Cannot login. Wrong password or phone number');
      },
    });
    this.userService.getRole().subscribe({
      next: (response: any) => {
        console.log('Role của user', response);
      },
      complete: () => {
        console.log('Login process completed');
      },
      error: (error: any) => {
        console.error('get Role failed', error);
        alert('Cannot login. Role not exist');
      },
    });
  }
}
