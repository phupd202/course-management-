<template>
    <h1 style="margin-top: 50px; color: #B9B9B9">Vì sao nên chọn chúng tôi</h1>
    <div class = "list-card" style="margin-left: 20px; margin-right: 20px;">
        <div class="card">
            <img src="../assets/intro1.png" alt="Ảnh intro 1">
            <div style="margin-top: 20px; margin-bottom: 20px;">
                <h3 class = "text-card">Dịch vụ chất lượng cao</h3>
                <p class = "text-card">Học bổng dành riêng cho lớp Chất lượng cao</p>
                <p class = "text-card">Miễn phí sử dụng phòng học sáng tạo</p>
                <p class = "text-card">Miễn phí các chương trình ngoại khoá của BKAP</p>
            </div>
        </div>
        <div class="card">
            <img src="../assets/intro2.png" alt="Ảnh intro 2">
            <div style="margin-top: 20px; margin-bottom: 20px">
                <h3 class = "text-card">Làm trước học sau</h3>
                <p class = "text-card">75% thực hành, bỏ túi ít nhất 6 sản phẩm thực tế</p>
                <p class = "text-card">Thực hành sâu và liên tục cùng giảng viên doanh nghiệp</p>
                <p class = "text-card">Tham gia dự án tại doanh nghiệp ngay trong quá trình học</p>
            </div>
        </div>
        <div class="card" id = "card-hover">
            <img src="../assets/intro3.png" alt="Ảnh intro 3">
            <div style="margin-top: 20px; margin-bottom: 20px">
                <h3 class="text-card">Tiện ích hỗ trợ</h3>
                <p class = "text-card">Hỗ trợ 24/7</p>
                <p class = "text-card">Tặng khoá tiếng Anh, kỹ năng mềm cho IT Leader</p>
                <p class = "text-card">Bảo hành trọn đời, miễn phí cập nhật công nghệ mới</p>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts"></script>
<style>
 @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');
.list-card {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-gap: 20px;
    margin: 0 -50px; /* Tạo lề bên ngoài cách màn hình 20px */
}

.card {
    background-color: #fff;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 20px;
    border-radius: #fff;
    box-shadow: 0 0 10px rga(0, 0, 0, 0.1);
    transition: transform 1s ease;
    margin: 15px;
    /* không cho ảnh vượt ra ngoài */
    overflow: hidden;  
    border:none;
}

#card-hover {
    /* phóng to ảnh */
    transform: scale(1.05);
}

.card img {
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

.detail-btn {
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

.detail-btn:hover {
    background-color: #0056b3; /* Màu nền của nút khi hover */
}

.detail-btn:focus {
    outline: none; /* Loại bỏ đường viền xung quanh khi focus */
}

.detail-btn a{
    text-decoration: none;
    color: white;
    font-family: Roboto;
}

.text-card {
    color: #666;
    font-family: Roboto;
}

.div-detail {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>