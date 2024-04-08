<template>
    <div class="container-fluid d-flex justify-content-center align-items-center vh-100">
        <div class="col-4 content">
            <div class="card-body my-auto">
                <div class="text-center">
                    <h1 class="h3">Đăng ký</h1>
                    <p>Đăng ký ngay để học các khoá học bạn yêu thích</p>
                </div>

                <form class="mt-4 form-login" @submit.prevent="submitForm">
                    <div class="mb-3">
                        <input type="text" v-model="signupData.email" class="form-control" placeholder="Email" @blur="validateEmail" required autofocus>
                        <div v-if="errors.email" class="text-danger">{{ errors.email }}</div>
                    </div>

                    <div class="mb-3">
                        <input type="text" v-model="signupData.phone" class="form-control" @blur="validatePhone" placeholder="Số điện thoại" required autofocus>
                        <div v-if="errors.phone" class="text-danger">{{ errors.phone }}</div>
                    </div>

                    <div class="mb-3">
                        <input type="text" v-model="signupData.address" class="form-control"  @blur="validateAddress" placeholder="Địa chỉ" required autofocus>
                        <div v-if="errors.address" class="text-danger">{{ errors.address }}</div>
                    </div>

                    <div class="mb-3">
                        <input type="password" v-model="signupData.password" class="form-control" @blur="validatePassword" required placeholder="Mật khẩu">
                        <div v-if="errors.password" class="text-danger">{{ errors.password }}</div>
                    </div>

                    <div class="mb-3">
                        <input type="password" v-model="signupData.confirmPassword" class="form-control" @blur="validateConfirmPassword" placeholder="Xác nhận mật khẩu" required>
                        <div v-if="errors.confirmPassword" class="text-danger">{{ errors.confirmPassword }}</div>
                    </div>

                    <div class="d-grid mt-5 d-flex justify-content-center">
                        <button class="btn" type="submit">Đăng ký</button>
                    </div>
                </form>

                <!-- Liên kết -->
                <div class="d-flex justify-content-between mt-4">
                    <router-link to="/course-management/signin" class="btn-link text-decoration-none">Đăng nhập</router-link>
                    <router-link to="/forgot-password" class="btn-link text-decoration-none">Quên mật khẩu</router-link>
                </div>
            </div>
        </div>
    </div>

</template>
<script lang="ts">
import { defineComponent } from 'vue';
import axios from 'axios';
import SignupData from '@/interface/auth/SignunData';
export default defineComponent({
  data() {
    return {
      signupData: {
        email: '',
        phone: '',
        address: '',
        password: '',
        confirmPassword: '',
      } as SignupData,
      errors: {} as {[key: string]: string}
    };
  },
  methods: {
    async submitForm() {
      try {
        const response = await axios.post('http://localhost:8080/course-management/signup', this.signupData); 
        console.log('Phản hồi từ backend:', response.data);
        this.$router.push({ name: 'signin' }); 
      } catch (error) {
        console.log('Lỗi khi gửi yêu cầu đăng ký:');
        this.$router.push({ name: 'error' }); 
      }
    },
    validateEmail() {
        this.errors.email = '';
        if (!this.signupData.email) {
            this.errors.email = 'Vui lòng nhập địa chỉ email.';
        } else if (!/^\S+@gmail\.com$/.test(this.signupData.email)) {
            this.errors.email = 'Địa chỉ email phải kết thúc bằng @gmail.com';
        }
    }, 
    validatePhone() {
        this.errors.phone = '';
        if (!this.signupData.phone) {
            this.errors.phone = 'Vui lòng nhập số điện thoại.';
        } else if (!/^\d{10,15}$/.test(this.signupData.phone)) {
            this.errors.phone = 'Số điện thoại phải gồm từ 10 đến 15 chữ số.';
        }
    }, 
    validateAddress() {
      this.errors.address = '';
      if (!this.signupData.address || typeof this.signupData.address !== 'string') {
        this.errors.address = 'Địa chỉ không hợp lệ. Vui lòng nhập địa chỉ.';
      }
    },
    validatePassword() {
        this.errors.password = '';
        if (!this.signupData.password) {
            this.errors.password = 'Vui lòng nhập mật khẩu.';
        } else if (
            !/(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}/.test(this.signupData.password)
        ) {
            this.errors.password =
            'Mật khẩu phải có ít nhất 8 ký tự, 1 ký tự in hoa, 1 ký tự số, và 1 ký tự đặc biệt.';
        }
    }, 
    validateConfirmPassword() {
        this.errors.confirmPassword = '';
        if (!this.signupData.confirmPassword) {
            this.errors.confirmPassword = 'Vui lòng xác nhận mật khẩu.';
        } else if (this.signupData.password !== this.signupData.confirmPassword) {
            this.errors.confirmPassword = 'Mật khẩu nhập lại không trùng khớp.';
        }
    }
  },
});
</script>

<style>
    .btn {
        width: 120px;
        height: 40px;
        background-color: #003E83;
        color: #fff;
    }


    .btn:hover {
        background-color: #4BAAFE;
        color: #003E83;
    }

    * {
        font-family: serif;
    }

    .container-fluid {
        background-color: #4BAAFE;
    }

    .content {
        background-color: #fff;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        /* Thêm đổ bóng nhẹ */
        padding: 20px;
        /* Thêm padding để nội dung không sát mép */
    }

    .content h1 {
        color: #333;
        font-size: 24px;
    }
</style>