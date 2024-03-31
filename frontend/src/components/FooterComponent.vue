<template>
    <div class="footer-course" style="margin-top: 30px;">
        <div class="connect-us-footer">
            <h3>Liên hệ</h3>
            <ul class = "list-item">
                <li>Hotline: 08164186147</li>
                <li>Email: tuyensinh@bachkhoa-aptech.edu.vn</li>
            </ul>
        </div>

        <div class="about-us-footer">
            <h3>Về BKAP</h3>
            <ul class = "list-item">
                <li>Liên hệ</li>
                <li>Tuyển sinh</li>
                <li>Tuyển dụng</li>
            </ul>
        </div>

        <div class="course-footer">
            <h3>Các khoá học</h3>
            <ul class = "list-item">
                <li style="color: #333333; font-size: 20px;;">Lập trình Java Web</li>
                <li style="color: #333333; font-size: 20px;;" >Lập trình viên quốc tế</li>
                <li style="color: #333333; font-size: 20px;;">Quản trị mạng và bảo mật</li>
            </ul>
        </div>
    </div>

    <div class = "copyright">
        Copyright ©2024. All Rights Reserved. — Designed with love by phupd1@newwave.vn
    </div>
</template>

<script setup lang="ts">

</script>

<style>
 @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');
.footer-course {
    display: flex;
    padding: 20px;
}

.connect-us-footer,
.about-us-footer,
.course-footer {
    flex: 1;
    padding: 0 20px; /* Thêm padding nếu muốn tạo khoảng cách giữa các phần tử */
    text-align: left
}

.list-item {
    list-style-type: none
}

.copyright {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #333333;
    height: 50px;
    max-height: 50px;
    color: white; 
    font-family: Arial, sans-serif; 
    font-size: 14px; 
    text-align: center; 
    padding: 10px; 
}

.footer-course {
    background-color: #e0e0e0;
}

.connect-us-footer,
.about-us-footer,
.course-footer p, h3 {
    color: #333333;
    font-family: Roboto;
}

.connect-us-footer,
.about-us-footer,
.course-footer p {
    font-size: 20px;
}

</style>