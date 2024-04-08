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

import axios from 'axios';

interface CourseResponse {
   courseId: number, 
   nameCourse: string, 
   createdAt: string, 
   numClass: number, 
   isClosed: boolean, 
   url: string, 
   description: string,
   price: number,
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


.list-course {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-gap: 20px;
    margin: 0 -50px; /* Tạo lề bên ngoài cách màn hình 20px */
}

.card-course {
    background-color: #fff;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 20px;
    border-radius: #fff;
    box-shadow: 0 0 10px rga(0, 0, 0, 0.1);
    margin: 15px;
    /* không cho ảnh vượt ra ngoài */
    overflow: hidden;  
    border:none;
}

.card-course img {
    width: 100%;
    height: auto;
    border-radius: 10px;
    margin-bottom: 15px;
    transition: transform 0.8s ease;
}

.card-content {
    text-align: center;
}

.card-content h3 {
    font-size: 20px;
    margin-bottom: 10px;
    color: #666666;
}

.card-content p {
    font-size: 16px;
    line-height: 1.5;
    color:  #666666;
    margin-bottom: 8px;
}

.register-btn {
    padding: 10px 20px;
    font-size: 16px;
    font-weight: bold;
    color: #fff;
    background-color: #003E83; 
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s, color 0.3s;
    width: 150px;
    height: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
    text-decoration: none; /* Loại bỏ gạch chân mặc định */
}

.register-btn:hover {
    background-color: #0056b3; /* Màu nền của nút khi hover */
}

.register-btn:focus {
    outline: none; /* Loại bỏ đường viền xung quanh khi focus */
}

.text-card {
    color: #666;
    font-family: Roboto;
}

.register-div {
    display: flex;
    justify-content: center;
    align-items: center;
}

#h3-course {
   color: #FF69B4;;
}

#h2-sale {
   color: #FF69B4;
}

.text-course {
   color:  #6A5ACD;
}
</style>