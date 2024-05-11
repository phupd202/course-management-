<template>
  <section id="content" class="content">
    <div class="content__boxed w-100 min-vh-100 d-flex flex-column align-items-center justify-content-center">
      <div class="content__wrap">

        <!-- Login card -->
        <div class="card shadow-lg">
          <div class="card-body">
            <div class="text-center">
              <h1 class="h3">Đăng nhập</h1>
              <p>Đăng nhập ngay để xem khoá học của bạn!!!</p>
            </div>

            <form class="mt-4" @submit.prevent="login">

              <div class="mb-3">
                <input type="text" v-model="signinData.email" class="form-control" placeholder="Email" required @blur = "validateEmail">
                <div v-if="errors.email" class="text-danger">{{ errors.email }}</div>
              </div>

              <div class="mb-3">
                <input type="password" v-model="signinData.password" class="form-control" placeholder="Mật khẩu" required @blur = "validatePassword">
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

              <div class="d-flex justify-content-center">
                <button class="btn btn-primary" type="submit">Đăng nhập</button>
              </div>
            </form>

            <div class="d-flex justify-content-between mt-4">
              <router-link to="/course-management/signup" class="btn-link text-decoration-none" >Đăng ký</router-link>
              <router-link to="/course-management/forgot-password" class="btn-link text-decoration-none">Quên mật khẩu</router-link>
            </div>

            <div class="d-flex align-items-center justify-content-between border-top pt-3 mt-3">
              <h5 class="m-0">Đăng nhập với</h5>

              <!-- Social media buttons -->
              <div class="ms-3">
                <a href="#" class="btn btn-sm btn-icon btn-hover btn-primary text-inherit">
                  <i class="demo-psi-facebook fs-5"></i>
                </a>
                <a href="#" class="btn btn-sm btn-icon btn-hover btn-info text-inherit">
                  <i class="demo-psi-twitter fs-5"></i>
                </a>
                <a href="#" class="btn btn-sm btn-icon btn-hover btn-danger text-inherit">
                  <i class="demo-psi-google-plus fs-5"></i>
                </a>
                <a href="#" class="btn btn-sm btn-icon btn-hover btn-warning text-inherit">
                  <i class="demo-psi-instagram fs-5"></i>
                </a>
              </div>
              <!-- END : Social media buttons -->

            </div>

          </div>
        </div>
        <!-- END : Login card -->

        <!-- Show the background images container -->
        <div class="d-flex align-items-center justify-content-center gap-3 mt-4">
          <button class="btn btn-danger hstack gap-2" data-bs-toggle="offcanvas" data-bs-target="#_dm-boxedBgContent">
            <i class=" demo-psi-photos fs-4"></i>
            <span class="vr"></span>
            Trang chủ
          </button>
          <button class="btn btn-light" onclick="window.history.back()">Trở về</button>
        </div>
        <!-- END : Show the background images container -->

      </div>
    </div>
  </section>

</template>

<script setup lang="ts">
import router from '@/router';
import { computed, ref } from 'vue';
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

const errors = ref<{ [key: string]: string }>({
  email: '',
  password: ''
});

const validateEmail = () => {
  errors.value.email = '';
  if (!signinData.email) {
    errors.value.email = 'Vui lòng nhập địa chỉ email.';
  } else if (!/^\S+@gmail\.com$/.test(signinData.email)) {
    errors.value.email = 'Địa chỉ email phải kết thúc bằng @gmail.com';
  }
};

const validatePassword = () => {
  errors.value.password = '';
  if (!signinData.password) {
    errors.value.password = 'Vui lòng nhập mật khẩu.';
  } else if (
    !/(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}/.test(
      signinData.password
    )
  ) {
    errors.value.password =
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
        if (roles.value[0] === "USER") {
          router.push({ name: 'userMyCourse' });
        } else if (roles.value[0] === "ADMIN") {
          router.push({ name: 'adminCourse' });
        } else if (roles.value[0] === "LECTURER") {
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
</style>