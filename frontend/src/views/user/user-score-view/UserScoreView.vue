<template>
     <header>
        <HomeSidebar></HomeSidebar>
        <HomeHeader></HomeHeader>
    </header>

    <main style="margin-top: 58px; margin-left: 60px">
        <div class="container pt-4">
            <h3>Điểm thi</h3> <br>
            <div class = "row d-flex justify-content-center">
                <div class="col-md-6">
                    <select class="form-select mb-3" aria-label="Default select example" id="ad-course-select1" v-model = "selectedClassroom">
                        <option selected>Chọn khoá học</option>
                        <option v-for="(classroom, index) in classrooms" :key = "index" :value="classroom.classroomId">{{ classroom.nameCourse }} - Thời gian học: {{ formatDateTime(classroom.beginDate) }} - {{ formatDateTime(classroom.endDate) }}</option>
                    </select>

                    <button class = "btn btn-primary" @click="getMyScore()">Lọc</button>
                </div>   
            </div>

            <table class="table table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col">STT</th>
                        <th scope="col">Tên môn</th>
                        <th scope="col">Điểm</th>
                        <th scope="col">Trạng thái</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(score, index) in scores" :key="index">
                        <th scope="row">{{ index + 1 }}</th>
                        <td>{{ score.nameSubject }}</td>
                        <td>{{ score.score }}</td>
                        <td>
                            <span v-if="score.status === 'Trượt'" class="badge bg-danger">Trượt</span>
                            <span v-else-if="score.status === 'Tốt'" class="badge bg-success">Tốt</span>
                            <span v-else class="badge bg-warning text-dark">{{ score.status }}</span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </main>
</template>

<script setup lang = "ts">
import HomeHeader from '@/components/HomeHeader.vue';
import HomeSidebar from '@/components/HomeSidebar.vue';
import { Score } from '@/interface/Score';
import { computed, onMounted, ref } from 'vue';
import { getScore } from '@/service/user/ScoreService';
import { useStore } from 'vuex';
import { ClassroomOfCourse } from '@/interface/ClassroomOfCourse';
import { formatDateTime } from '@/helpers/timehelpers';
import { getMyCourses } from '@/service/user/ClassroomService';

const store = useStore();
const jwtToken = computed(() => store.getters.getAccessToken)
const scores = ref<Score[]>([]);
const classrooms = ref<ClassroomOfCourse[]>([]);
let selectedClassroom:number = -1;


// get Score 
const getMyScore = async () => {
    try {
        const response = await getScore(jwtToken.value, selectedClassroom);
        scores.value = response;
        console.log("selectedClassroom: " + selectedClassroom)
    } catch(error) {
        console.log("Eror when get score")
    }
}

onMounted(async() => {
    try {
        const response = await getMyCourses(jwtToken.value);
        classrooms.value = response;
    } catch(error) {
        console.log("Failure when get classroom")
    }
})
</script>