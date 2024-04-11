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
                            <th scope="col">Điện thoại</th>
                            <th scope="col">Email</th>
                            <th scope="col">Địa chỉ</th>
                            <th scope="col">Điểm thi</th>
                            <th scope="col">Xếp loại</th>
                            <th scope="col">Nhập Excel</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(enrollment, index) in enrollments" :key="index">
                            <td>{{ index + 1 }}</td>
                            <td>{{ enrollment.name }}</td>
                            <td>{{ enrollment.phone }}</td>
                            <td>{{ enrollment.email }}</td>
                            <td>{{ enrollment.address }}</td>
                            <td>
                                <input type="text" v-if="enrollment.score !== null" v-model = "enrollment.score" class="form-control transparent-input mx-auto text-center" style="width: 60px;" >
                                <input type="text" v-else v-model = "enrollment.score" class="form-control transparent-input mx-auto text-center" style="width: 60px;" placeholder="none">
                            </td>
                            <td>
                                <input type="text" v-if="enrollment.status !== null" v-model = "enrollment.status" class="form-control transparent-input mx-auto text-center" style="width: 60px;">
                                <input type="text" v-else v-model = "enrollment.status" class="form-control transparent-input mx-auto text-center" style="width: 60px;" placeholder="none">
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
                <button class = "btn btn-primary" @click="sendListScore()">Hoàn Tất</button>
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
import { computed, onMounted, ref} from 'vue';
import {useRoute } from 'vue-router';

import { UpdateScore } from '@/interface/lecturer/UpdateScore';
import { Enrollment } from '@/interface/lecturer/Enrollment';
import { getEnrollmentOfClass, sendScore } from '@/service/lecturer/ScoreService';

const route = useRoute();
const jwtToken = computed(() => store.getters.getAccessToken)

const classroomId = Number(route.query.classroomId);
const subjectId = Number(route.query.subjectId);
const enrollments = ref<Enrollment[]>([]);

const sendListScore = async () => {
    // check data
    if (enrollments.value.length === 0) {
        console.log("No enrollments to send.");
        return;
    }

    const scores: number[] = [];
    const enrollmentIds: number[] = [];
    const statues: string[] = [];

    enrollments.value.forEach(enrollment => {
        scores.push(Number(enrollment.score));
        enrollmentIds.push(Number(enrollment.enrollmentId));
        statues.push(String(enrollment.status));
    });

    const updateScore: UpdateScore = {
        classroomId: classroomId, 
        subjectId: subjectId, 
        scores: scores,
        enrollmentIds: enrollmentIds, 
        statues: statues
    };

    const confirmMessage = "Vui lòng kiểm tra kỹ lại trước khi nhập vào hệ thống? Nhấn OK để gửi dữ liệu lên hệ thống.";
   
    if(updateScore !== null && confirm(confirmMessage)) {
        try {
            const response = await sendScore(updateScore, jwtToken.value);
            alert("Điểm của học viên đã được cập nhật thành công!!")
            console.log("enrollments: ", enrollments.value)
            console.log("updateScore.value.statues, ", updateScore.statues)
            console.log("updateScore.value: ", updateScore)
            console.log("Sending score is successfully!", response)
        } catch(error) {
            console.log("Having a error while update score");
            alert("Không thể cập nhật điểm của học viên! Vui lòng kiểm tra lại")
            console.log("enrollments: ", enrollments.value)
            console.log("updateScore.value.statues, ", updateScore.statues)
            console.log("updateScore.value: ", updateScore)
            throw error;
        }
    }
}

// fecth data from enrollments into updateScore
onMounted(async() => {
    try {
        const response = await getEnrollmentOfClass(classroomId, subjectId, jwtToken.value);
        enrollments.value = response;
        console.log("Get enrollments successfully");
    } catch(error) {
        console.log("Having a error when get enrollment")
        throw error;
    }
})
</script>

<style>
@import url(DetailAssignmentView.css);
</style>