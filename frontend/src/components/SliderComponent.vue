<template>
    <div class = "introduce">
         <!-- link -->
         <div class = "list-nav-link">
             <div class = "list-link">
                 <div class = "left-link group-link" style="margin-top: 20px;">
                     <div class = "text-item">Khoá học</div>
                     <div class = "text-item">Công nghệ</div>
                 </div>
                 <div class = "center-link group-link text-item" style="margin-top: 20px; color: white;" >Trang chủ</div>
 
                 <div class = "right-link group-link" style="margin-top: 20px;">
                     <div class = "text-item">Về chúng tôi</div>
                     <div class = "text-item">Liên hệ</div>
                 </div>
             </div>
         </div>
 
         <!-- Introduce -->
         <div class = "content-introduce">
             <div class = "item-introduce" :class="{ 'active': isActive }">
                 <h1 class = "slider text-color" style="font-size: 50px;">Về chúng tôi</h1>
                 <p class = "text-intro slider text-color" style="font-size: 25px;">Trung tâm với hơn 15 kinh nghiệm cung cấp các khoá học online, offline hàng đầu tại Việt Nam</p>
                 <button class = "signup-btn text-color">Đăng ký ngay</button>
             </div>
         </div>

    </div>
 </template>
 
 <script setup lang="ts">
import { ref, onMounted } from 'vue';

const isActive = ref(false);
 
onMounted(() => {
     setTimeout(() => {
         isActive.value = true;
     }, 800);
 });
 </script>

 <style>
 @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');
 .introduce {
     background-image: url("../assets/intro.jpg");
     background-size: cover; 
     background-position: center; 
     height: 100vh; 
     width: 100%;
     background-color: rgba(0, 0, 0, 0.3);
 }
 
 .list-link {
     display: grid;
     grid-template-columns: repeat(7, auto); /* Chia grid container thành 7 cột có kích thước tự động */
     gap: 80px;
 }
 
 .left-link {
     grid-column-start: 1; 
     grid-column-end: 4; 
     display: flex;
     flex-direction: row;
     justify-content: center;
     gap:80px;
 }
 
 .center-link {
     grid-column-start: 4; 
     grid-column-end: 5; 
 }
 
 .right-link {
     grid-column-start: 5; 
     grid-column-end: 8; 
     display: flex;
     flex-direction: row;
     justify-content: center;
     gap:80px;
 }
 
 .text-item {
     font-size: 25px;
     font-family: Roboto;
     text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
 }
 
 .text-item:hover {
     color:white;
 }
 
* {
     margin: 0;
     padding: 0;
}
 
.content-introduce {
     height: 100vh;
     display: flex;
     flex-direction: row;
     align-items: center;
     justify-content: center
}
 
.item-introduce {
     opacity: 0;
     transform: translateY(20px);
     transition: opacity 0.8s ease, transform 0.8s ease;
 }
 
 .item-introduce.active {
     opacity: 1;
     transform: translateY(0);
 }
 
 .signup-btn {
     padding: 10px 20px;
     font-size: 16px;
     font-weight: bold;
     color: #D3D3D3;
     background-color: transparent; /* Đặt nền trong suốt */
     border-radius: 5px;
     border-color: #fff;
     cursor: pointer;
     transition: background-color 0.3s;
     width: 250px;
     height: 60px;
     font-size: 25px;
 }
 
 .signup-btn:hover {
     background-color: white;
     color: #555555;
 }
 
 .introduce h1,p, div {
     color: #D3D3D3;
 }

.text-color {
color: #D3D3D3;
}
 
 </style>