<template>
<PageLayout>
        <template v-slot:content>
            <div class="d-flex justify-content-center">
                <div class="header-calendar d-flex align-content-center justify-content-center">
                    <h3>Danh sách môn học được phân công</h3>
                </div>
            </div>
            <div class="content__boxed">
                <div class="content__wrap">
                    <table class="table table-light">
                        <thead>
                            <tr>
                                <th scope="col">STT</th>
                                <th scope="col">Môn học</th>
                                <th scope="col">Mã lớp</th>
                                <th scope="col">Thứ</th>
                                <th scope="col">Giờ học</th>
                                <th scope="col">Ngày bắt đầu</th>
                                <th scope="col">Ngày kết thúc</th>
                                <th scope="col">Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(assignmentLecturer, index) in assignmentLecturers" :key="index">
                                <th scope="row"> {{ index + 1 }}</th>
                                <td>{{ assignmentLecturer.nameSubject }}</td>
                                <td>{{ assignmentLecturer.codeClassroom }}</td>
                                <td>Thứ {{ assignmentLecturer.dayOfWeek + 1}}</td>
                                <td>{{ assignmentLecturer.beginTime }} - {{ assignmentLecturer.endTime }}</td>
                                <td>{{ formatDateTime(assignmentLecturer.beginDate) }}</td>
                                <td>{{ formatDateTime(assignmentLecturer.endDate) }}</td>
                                <td>
                                    <router-link :to="'/course-management/lecturer/list-question/' + assignmentLecturer.subjectId" class="btn btn-primary btn-sm mr-2">
                                                Ngân hàng câu hỏi
                                    </router-link>
                                    <router-link :to="'/course-management/lecturer/create-exam/' + assignmentLecturer.subjectId" class="btn btn-primary btn-sm mr-2" style="margin-left: 10px;">
                                                Tạo đề ôn tập
                                    </router-link>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </template>
    </PageLayout>
</template>
<script lang="ts" setup>
import { AssignmentLecturer } from '@/interface/lecturer/AssignmentLecturer';
import { computed, onMounted, ref } from 'vue';
import { formatDateTime } from '@/helpers/timehelpers';
import store from '@/store/store';
import axios from 'axios';
import PageLayout from '@/layout/PageLayout.vue';

const jwtToken = computed(() => store.getters.getAccessToken)
const assignmentLecturers = ref<AssignmentLecturer[]>([])

const getAssignmentLecturer = async () => {
    try {
        const response = await axios.get<AssignmentLecturer[]>("http://localhost:8080/course-management/lecturer/get-all-assignment", {
        headers: {
            'Authorization': `Bearer ${jwtToken.value}`
        }
        });
        assignmentLecturers.value = (await response).data;
        console.log("Get assignment successfull!")
    } catch(error) {
        console.log("error in while get assignment!", error)
    }
}

onMounted(() => {
    getAssignmentLecturer();
})
</script>

<style>
@import url(ExamAndReview.css);
</style>