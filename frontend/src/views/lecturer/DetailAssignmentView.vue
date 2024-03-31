<template>
    <header>
        <HomeHeader></HomeHeader>
        <HomeSidebar></HomeSidebar>
    </header>

    <!-- Content -->
    <main style="margin-top: 70px;">
        <div class="container pt-4">
            <h3>Danh sách sinh viên tham gia</h3>
            
            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">STT</th>
                            <th scope="col">Tên học viên</th>
                            <th scope="col">Ngày sinh</th>
                            <th scope="col">Địa chỉ</th>
                            <th scope="col">Điểm thi</th>
                            <th scope="col">Đánh giá</th>
                            <th scope="col">Xuất Excel</th>
                            <th scope="col">Nhập Excel</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(enrollment, index) in enrollments" :key="index">
                            <td>{{ index + 1 }}</td>
                            <td>{{ enrollment.name }}</td>
                            <td>{{ enrollment.birthday }}</td>
                            <td>{{ enrollment.address }}</td>
                            <td>
                                <input type="text" v-model = "currentScore.score" class="form-control transparent-input mx-auto text-center" style="width: 60px;" >
                            </td>
                            <td>
                                <input type="text" v-model = "currentScore.status" class="form-control transparent-input mx-auto text-center" style="width: 60px;">
                            </td>
                            <td>
                                <button class="btn btn-success" @click="addScore(enrollment.enrollmentId)">Xác nhận</button>
                            </td>
                            <td>
                                <button class="btn btn-primary">Nhập</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <!-- Button nhập điểm -->
            <div>
                <button class = "btn btn-primary" @click="sendScoreEnrollment">Hoàn Tất</button>

                <button id = "btn-excel" class="btn btn-success">Xuất Excel</button>
            </div>
        </div>
    </main>     
</template>

<script setup lang="ts">
// exceljs file-saver
import HomeHeader from '@/components/HomeHeader.vue';
import HomeSidebar from '@/components/HomeSidebar.vue';
import store from '@/store/store';
import axios from 'axios';
import { computed, onMounted, ref} from 'vue';
import {useRoute } from 'vue-router';

const route = useRoute();
const jwtToken = computed(() => store.getters.getAccessToken)

interface Enrollment {
    enrollmentId: number,
    name: string | null, 
    address: string | null,
    birthday: string | null
}

const enrollments = ref<Enrollment[]>([]);

const getEnrollmentOfClass = async () => {

    try {
        const response = await axios.get<Enrollment[]>("http://localhost:8080/course-management/lecturer/get-enrollment", {
            params: {
                classroomId: route.query.classroomId
            }, 
            headers: {
                'Authorization': `Bearer ${jwtToken.value}`
            }
        });

        enrollments.value = response.data;
        console.log("Lấy danh sách enrollment thành công");
    } catch(error) {
        console.log("Có lỗi xảy ra khi lấy danh sách enrollment", error);
    }
};

interface Score {
    enrollmentId: number, 
    score: number, 
    status: string,
}

// ----------------------------------Nhập điểm
const currentScore = ref({ score: null, status: '' });

const scores = ref<Score[]>([]);

const addScore = (enrollmentId: number) => {
    const scoreValue = currentScore.value.score;
    if (scoreValue !== null && !isNaN(scoreValue)) {
        const scoreNumber = Number(scoreValue);

        const scoreToAdd: Score = {
            enrollmentId: enrollmentId,
            score: scoreNumber,
            status: currentScore.value.status,
        };

        scores.value.push(scoreToAdd);
        currentScore.value = { score: null, status: '' };
    } else {
        console.error("Giá trị điểm không hợp lệ");
    }
}

// ---------------------------------
const sendScoreEnrollment = async() => {
    // gán enrollmentId chó score
    if (scores.value.length > 0) {
        enrollments.value.forEach((enrollment, index) => {
            if (index < scores.value.length) {
                scores.value[index].enrollmentId = enrollment.enrollmentId as number;
            }
        });
    }

    console.log("Dữ liệu điểm gửi lên server" + scores.value)
    try {
        const response = axios.post("http://localhost:8080/course-management/lecturer/send-score", {
            params: {
                classroomId: route.query.classroomId,
                subjectId: route.query.subjectId
            }, 
            headers: {
                'Authorization': `Bearer ${jwtToken.value}`
            }, scores 
        });

        console.log("Nhập điểm thành công", response)
        alert("Nhập điểm thành công!!");
        console.log("Dữ liệu điểm gửi lên server" + scores.value)
    } catch(error) {
        console.log("Nhập điểm không thành công", error)
        alert("Nhập điểm không thành công!")
        console.log("Dữ liệu điểm gửi lên server" + scores.value)
    }
}

// // Xuất excel
// const exportToExcel = (data: any, filename: string) => {
//     const ws = XLSX.utils.json_to_sheet(data);
//     const wb = XLSX.utils.book_new();
//     XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
//     XLSX.writeFile(wb, filename + '.xlsx');
// }

// const exportData = () => {
//     exportToExcel(enrollments, "danh_sach_sinh_vien")
// }
// --------------------mount

onMounted(() => {
    getEnrollmentOfClass();
});
</script>

<style>
#btn-excel {
    margin-left: 50px;
}
</style>