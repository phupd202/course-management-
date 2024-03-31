<template>
    <div class="container-fluid d-flex justify-content-center align-items-center vh-100">
        <div class="col-4 content">
            <div class="card-body my-auto">
                <div class="text-center">
                    <h1>Đăng nhập</h1>
                    <p>Đăng nhập với tài khoản của bạn</p>
                </div>

                <form class="mt-4 form-login" @submit.prevent="login">
                    <div class="mb-3">
                        <input type="text" v-model="signinData.email" class="form-control" placeholder="Email" required @input = "validateEmail">
                        <div v-if="errors.email" class="text-danger">{{ errors.email }}</div>
                    </div>

                    <div class="mb-3">
                        <input type="password" v-model="signinData.password" class="form-control" placeholder="Mật khẩu" required @input = "validatePassword">
                        <div v-if="errors.password" class="text-danger">{{ errors.password }}</div>
                    </div>

                    <div class = "d-flex justify-content-start">
                        <div class="form-check">
                            <input id="_dm-loginCheck" class="form-check-input" type="checkbox">
                            <label for="_dm-loginCheck" class="form-check-label">
                                Ghi nhớ
                            </label>
                        </div>
                    </div>

                    <div class="d-grid mt-5">
                        <button class="btn" type="submit">Đăng nhập</button>
                    </div>
                </form>

                <div class="d-flex justify-content-between mt-4">
                    <router-link to="/course-management/signup" class="btn-link text-decoration-none" >Đăng ký</router-link>
                    <router-link to="/course-management/forgot-password" class="btn-link text-decoration-none">Quên mật khẩu</router-link>
                </div>
            </div>
        </div>
    </div>

</template>

<script setup lang="ts">
import router from '@/router';
import { computed } from 'vue';
import { useStore } from 'vuex';
// import { computed } from 'vue';

interface SigninData {
  email: string;
  password: string;
}

const signinData: SigninData = {
  email: '',
  password: '',
};


const errors: { [key: string]: string } = {};
const validateEmail = () => {
  errors.email = '';
  if (!signinData.email) {
    errors.email = 'Vui lòng nhập địa chỉ email.';
  } else if (!/^\S+@gmail\.com$/.test(signinData.email)) {
    errors.email = 'Địa chỉ email phải kết thúc bằng @gmail.com';
  }
};

const validatePassword = () => {
  errors.password = '';
  if (!signinData.password) {
    errors.password = 'Vui lòng nhập mật khẩu.';
  } else if (
    !/(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}/.test(
      signinData.password
    )
  ) {
    errors.password =
      'Mật khẩu phải có ít nhất 8 ký tự, 1 ký tự in hoa, 1 ký tự số, và 1 ký tự đặc biệt.';
  }
};

const store = useStore();

// const isLoggedIn = computed(() => store.state.isLoggedIn);
interface ResponseSignin {
  id: number | null, 
  email: string | null, 
  roles: string[] | null, 
  accessToken: string | null, 
  tokenType: string | null
}

const login = async () => {
  try {
    const response: ResponseSignin = await store.dispatch('login', signinData);

    if (response) {
      console.log('Đăng nhập thành công');
      console.log('Dữ liệu trả về: ' + response.email);

      const roles = computed(() => store.getters.getRoles)
      console.log("Mảng roles store: " + store.getters.getRoles)
      console.log("Mảng roles: " + roles.value)

      if (Array.isArray(roles.value) && roles.value.length === 1) {
        if(roles.value[0] === "USER") {
          router.push({ name: 'home' });
        } else if(roles.value[0] === "ADMIN") {
          router.push({ name: 'adminCourse' });
        } else if(roles.value[0] === "LECTURER") {
          router.push({ name: 'lecturerScore' });
        } else {
          alert("Vai trò không hợp lệ")
        }
      } else {
        console.error('Vai trò không hợp lệ');
      }
    } else {
      console.error('Đăng nhập thất bại:', response);
    }
  } catch (error) {
    console.error('Lỗi khi đăng nhập:', error);
  }
};

// const logout = async () => {
//   try {
//     await store.dispatch('logout');
//     console.log('Đăng xuất thành công');
//   } catch (error) {
//     console.error('Lỗi khi đăng xuất:', error);
//   }
// };
</script>

<style>
.btn {
    width: 120px;
    height: 40px;
    background-color: #003E83;
    color: #fff;
}

.btn:hover {
    color: #003E83;
    background-color: #4BAAFE;
}

* {
    font-family: 'Nunito', sans-serif; 
}

.container-fluid {
    background-color: #4BAAFE;
}

.content {
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Thêm đổ bóng nhẹ */
    padding: 20px; /* Thêm padding để nội dung không sát mép */
}

.content h1 {
    font-size: 30px;
    color: #003E83;

}
</style>