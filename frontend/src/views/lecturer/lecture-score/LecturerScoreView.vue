<template>
    <header>
        <HomeSidebar></HomeSidebar>
        <HomeHeader></HomeHeader>
    </header>

    <main style="margin-top: 70px;">
        <!-- content -->
        <div class="container pt-4">
            <h3>Danh sách lớp đang giảng dạy</h3>
            <div class="list-item" style="margin-top: 30px;">
                <div class="row">
                    <div class="col-md-4" v-for="(assignmentLecturer, idx) in assignmentLecturers" :key="idx">
                        <div class="card shadow-lg" style="width: 100%;">
                            <div class="card-header text-white" id = "header-card">
                                <h5 class="card-title mb-0"> {{ assignmentLecturer.nameSubject }} - {{ assignmentLecturer.codeClassroom }}</h5>
                            </div>
                            <div class="card-body">
                                <div class="description">
                                    <p class="card-text"><strong>Lịch học:</strong> Thứ {{ assignmentLecturer.dayOfWeek  + 1}} - ({{ assignmentLecturer.beginTime }} - {{ assignmentLecturer.endTime }})</p>
                                    <p class="card-text"><strong>Thời gian:</strong> Từ {{ formatDateTime(assignmentLecturer.beginDate)  }} - {{ formatDateTime(assignmentLecturer.endDate) }}</p>
                                </div>
                                <div class="text-center mt-4">
                                    <router-link class="btn btn-info btn-sm" :to="{ path: '/course-management/lecturer/detail-assignment', query: { classroomId: assignmentLecturer.classroomId , subjectId: assignmentLecturer.subjectId} }">
                                        Nhập điểm
                                    </router-link>
                                </div>
                                <RouterView></RouterView>
                            </div>
                        </div>
                        <br>
                    </div>
                </div>
            </div>
        </div>
    </main> 
</template>

<script setup lang= "ts">
import HomeHeader from '@/components/HomeHeader.vue';
import HomeSidebar from '@/components/HomeSidebar.vue';
import store from '@/store/store';
import axios from 'axios';
import { computed, onMounted, ref } from 'vue';
import { formatDateTime } from '@/helpers/timehelpers';

interface AssignmentLecturer {
    classroomId: number, 
    beginDate: string, 
    endDate: string , 
    beginTime: string | null, 
    endTime: string | null, 
    codeClassroom: string | null, 
    nameSubject: string | null, 
    dayOfWeek: number,
    subjectId: number
}

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

// --------------Nhập điểm---------------
// Input: classroomId, 

</script>

<style>
#header-card {
    background-color: #003E83;;
}
</style>