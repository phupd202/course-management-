<template>
   <div>
      <SliderComponent></SliderComponent>
      <IntroComponent></IntroComponent>
   </div>

   <h2 style="margin-top: 10px; margin-bottom: 50px; font-family: Roboto">Các khoá học nổi bật</h2>
   <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-indicators">
         <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active"
            aria-current="true" aria-label="Slide 1"></button>
         <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1"
            aria-label="Slide 2"></button>
         <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2"
            aria-label="Slide 3"></button>
      </div>
      <div class="carousel-inner">
         <div class="carousel-item active">
            <img src="../img/intro1.png">
            <div class="carousel-caption d-none d-md-block">
               <h3>Khoá học lập trình Java</h3>
               <p>Cung cấp các kiến thức cơ bản về lập trình Java.</p>
            </div>
         </div>
         <div class="carousel-item">
            <img src="../img/intro2.png">
            <div class="carousel-caption d-none d-md-block">
               <h3>Khoá học lập trình Java</h3>
               <p>Cung cấp các kiến thức cơ bản về lập trình Java.</p>
            </div>
         </div>
         <div class="carousel-item">
            <img src="../img/intro3.png">
            <div class="carousel-caption d-none d-md-block">
               <h3>Khoá học lập trình Java</h3>
               <p>Cung cấp các kiến thức cơ bản về lập trình Java.</p>
            </div>
         </div>
      </div>
   </div>
   
   <h2 style="margin-top: 30px; margin-bottom: 20px;">Các khoá học của chúng tôi</h2>
   <div class = "list-course" style="margin-left: 20px; margin-right: 20px">
      <div class="card-course" v-for = "(course, index) in courseResponses" :key="index">
            <img :src="course.url" alt="Ảnh khoá học" style="max-width: 100%; max-height: 150px;">
            <div style="margin-top: 20px; margin-bottom: 20px;">
                <h3 class = "text-card" id = "h3-course" >{{ truncateText(course.nameCourse, 20) }}</h3>
                <h3>Giá: {{ currencyFilter(course.price) }}</h3>
                <p class = "text-card">{{ truncateText(course.description, 80) }}</p>
            </div>

            <div class="register-div">
               <router-link :to="{ name: 'detailCourseGuest', query: { courseId: Number(course.courseId) } }" class="btn btn-primary">
                Chi tiết
               </router-link>
            </div>
        </div>
   </div>
   <div class = "form-register" style="margin-top: 30px; margin-right: 30px;">
      <h2 id = "h2-sale">Đăng ký nhận tư vấn ngay!!!</h2>
      <div class="container d-flex justify-content-center">
         <form class = "col-9" @submit.prevent="sendForm(formRequest)">
            <div class="mb-3">
               <label for="full-name" class="form-label" >Họ và tên</label>
               <input type="text" class="form-control input-register" id="full-name" v-model="formRequest.name">
            </div>

            <div class="mb-3">
               <label for="email" class="form-label">Email</label>
               <input type="text" class="form-control input-register" id="email" v-model="formRequest.email">
            </div>

            <div class="mb-3">
               <label for="phone-number" class="form-label">Số điện thoại</label>
               <input type="text" class="form-control input-register" id="phone-number" v-model="formRequest.phone">
            </div>

            <div class="mb-3">
               <label for="address" class="form-label" >Địa chỉ</label>
               <input type="text" class="form-control input-register" id="address" v-model="formRequest.address">
            </div>

            <div class="mb-3">
               <label for="select-course" class="form-label input-register">Khoá học bạn quan tâm</label>
               <select class="form-select" id="select-course" v-model="formRequest.courseId">
                  <option disabled value="" selected>Chọn khoá học</option>
                  <option v-for="(courseResponse, idx) in courseResponses" :key="idx" :value="courseResponse.courseId">{{ courseResponse.nameCourse }}</option>
               </select>
            </div>

            <button type="submit" class="sales-btn">Nhận tư vấn</button>
         </form>
      </div>
   </div>

   <FooterComponent></FooterComponent>
</template>

<script setup lang="ts">
import SliderComponent from '@/components/SliderComponent.vue';
import IntroComponent from '@/components/IntroComponent.vue';
import FooterComponent from '@/components/FooterComponent.vue';
import { onMounted, ref } from 'vue';
import { truncateText } from '@/helpers/texthelper';
import { currencyFilter } from '@/helpers/pricehelper';
import { FormRequest } from '@/interface/home/FormRequest';
import { CourseResponse } from '@/interface/home/CourseResponse';
import { getAllCourse, sendForm } from '@/service/home/HomeUserService';

const courseResponses = ref<CourseResponse[]>([]); 
const formRequest = ref<FormRequest>({
   name: '', 
   email: '',
   phone: '',
   address: '', 
   courseId: -1,
})

onMounted(async () => {
   const courses = await getAllCourse();
   courseResponses.value = courses;
})
 
</script>
<style>
@import url(HomeUserView.css);
</style>