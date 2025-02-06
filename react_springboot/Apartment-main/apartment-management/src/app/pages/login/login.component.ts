import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  loading = false;
  errorMessage = '';

  constructor(private fb: FormBuilder) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.loading = true;
      console.log('Login Data:', this.loginForm.value);
      
      // สมมติว่า Login API สำเร็จ
      setTimeout(() => {
        this.loading = false;
        alert('Login Success!');
      }, 1500);
    } else {
      this.errorMessage = 'Please enter username and password';
    }
  }
}
