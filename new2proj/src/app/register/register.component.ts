import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'], // Đổi thành styleUrls để đúng cú pháp
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm!: NgForm;
  phoneNumber: string;
  password: string;
  retypePassword: string;
  fullName: string;
  address: string;
  email: string;
  dayOfbirth: Date;
  age: number | null = null;
  passwordMismatch: boolean = false;

  constructor(private router: Router, private userService: UserService) {
    this.phoneNumber = '';
    this.password = '';
    this.retypePassword = '';
    this.fullName = '';
    this.address = '';
    this.email = '';
    this.dayOfbirth = new Date();
    this.dayOfbirth.setFullYear(this.dayOfbirth.getFullYear() - 18);
  }

  onPhoneChange() {
    console.log(`Phone Type: ${this.phoneNumber}`);
  }

  // Hàm đăng ký người dùng
  register() {
    this.checkMatchPassword(); // Kiểm tra mật khẩu khớp
    this.checkAge(); // Kiểm tra tuổi hợp lệ

    if (this.passwordMismatch || this.age === null || this.age < 18) {
      console.log('Password mismatch or age is not valid');
      return; // Không tiếp tục nếu có lỗi
    }
    const RegisterDTO = {
      fullname: this.fullName,
      phone_number: this.phoneNumber,
      address: this.address,
      password: this.password,
      retype_password: this.retypePassword,
      date_of_birth: this.dayOfbirth,
      facebook_account_id: 0,
      google_account_id: 0,
      roleId: 1,
    };
    this.userService.register(RegisterDTO).subscribe(
      (response) => {
        // Xử lý phản hồi JSON
        if (response.message) {
          console.log('User registered successfully:', response.message);
          this.router.navigate(['/login']);
        } else if (response.error) {
          console.error('Error registering user:', response.error);
        }
      },
      (error) => {
        console.error('Error registering user:', error);
      }
    );
  }

  // Kiểm tra mật khẩu khớp
  checkMatchPassword() {
    this.passwordMismatch = this.password !== this.retypePassword;
  }

  // Kiểm tra tuổi
  checkAge() {
    if (this.dayOfbirth) {
      const today = new Date();
      const birthDay = new Date(this.dayOfbirth);
      this.age =
        today.getFullYear() -
        birthDay.getFullYear() -
        (today.getMonth() < birthDay.getMonth() ||
        (today.getMonth() === birthDay.getMonth() &&
          today.getDate() < birthDay.getDate())
          ? 1
          : 0);
    } else {
      this.age = null;
    }
  }
}
