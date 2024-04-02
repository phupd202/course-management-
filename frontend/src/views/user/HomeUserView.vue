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
            <img src="../../assets/intro1.png">
            <div class="carousel-caption d-none d-md-block">
               <h3>Khoá học lập trình Java</h3>
               <p>Cung cấp các kiến thức cơ bản về lập trình Java.</p>
            </div>
         </div>
         <div class="carousel-item">
            <img src="../../assets/intro2.png">
            <div class="carousel-caption d-none d-md-block">
               <h3>Khoá học lập trình Java</h3>
               <p>Cung cấp các kiến thức cơ bản về lập trình Java.</p>
            </div>
         </div>
         <div class="carousel-item">
            <img src="../../assets/intro3.png">
            <div class="carousel-caption d-none d-md-block">
               <h3>Khoá học lập trình Java</h3>
               <p>Cung cấp các kiến thức cơ bản về lập trình Java.</p>
            </div>
         </div>
      </div>
   </div>
   <div class = "form-register" style="margin-top: 30px; margin-right: 30px;">
      <h2>Đăng ký nhận tư vấn ngay!!!</h2>
      <div class="container d-flex justify-content-center">
         <form class = "col-9" @submit.prevent="sendForm">
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
                  <option disabled value="">Chọn khoá học</option>
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
import axios from 'axios';

interface CourseResponse {
   courseId: number, 
   nameCourse: string, 
   createdAt: string, 
   numClass: number, 
   isClosed: boolean, 
   url: string, 
   description: string
}

const courseResponses = ref<CourseResponse[]>([]); 

const getAllCourse = async() => {
   try {
      const response = axios.get<CourseResponse[]>("http://localhost:8080/course-management/get-all-courses"); 
      courseResponses.value = (await response).data;
      console.log("Get all courses successfull!")
   } catch(error) {
      console.log("Encountered error while get course: ", error)
   }

}

// Send request to serve to send email
interface FormRequest {
   name: string, 
   email: string, 
   phone: string, 
   address: string,
   courseId: number

}

const formRequest = ref<FormRequest>({
   name: '', 
   email: '',
   phone: '',
   address: '', 
   courseId: -1,
})

const sendForm = async () => {
   console.log("Dữ liệu gửi đi: " + formRequest.value)
   try {
      const response = await axios.post<FormRequest>("http://localhost:8080/course-management/interest-course", formRequest.value)
      console.log("Dữ liệu gửi đi: ", response.data);
      const jsonData = "{\"__v_isShallow\":false,\"dep\":{},\"__v_isRef\":true,\"_rawValue\":{\"name\":\"Phạm Duy Phú\",\"email\":\"phamduyphu_t65@gmail.com\"}}";
      const data = JSON.parse(jsonData);
      console.log(data)

      console.log("Yêu cầu đã thành công");
   } catch(error) {
      console.error("Gặp lỗi khi đăng ký:", error)
      const jsonData = "{\"__v_isShallow\":false,\"dep\":{},\"__v_isRef\":true,\"_rawValue\":{\"name\":\"Phạm Duy Phú\",\"email\":\"phamduyphu_t65@gmail.com\"}}";
      const data = JSON.parse(jsonData);
      console.log(data)
   }
}

onMounted(() => {
   getAllCourse();
})
</script>

<style>
.a-news {
   text-decoration: none
}

.carousel-item img {
    height: 500px; /* Điều chỉnh chiều cao của ảnh */
    object-fit: cover; /* Đảm bảo ảnh không bị biến dạng */
    width: 100%;
}

.form-label {
   display: block;
   text-align: left; 
}
.sales-btn {
     padding: 10px 20px;
     font-size: 16px;
     font-weight: bold;
     color: #D3D3D3;
     background-color: #003E83;
     border-radius: 5px;
     border-color: #fff;
     cursor: pointer;
     transition: background-color 0.3s;
     width: 250px;
     height: 60px;
     font-size: 25px;
 }
 
.sales-btn:hover {
     background-color: white;
     color: #003E83;
}

</style>