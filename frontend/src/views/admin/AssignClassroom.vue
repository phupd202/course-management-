<template>
    <header>
        <HomeHeader></HomeHeader>
        <HomeSidebar></HomeSidebar>
    </header>

    <main style="margin-top: 58px">

        <div class="container pt-4">
            <!-- Các tab -->
            <nav>
                <div class="nav nav-tabs" id="nav-tab" role="tablist">
                    <button class="nav-link active" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-home"
                        type="button" role="tab" aria-controls="nav-home" aria-selected="true">
                        <h5>Phân công giảng dạy</h5>
                    </button>
                    <button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-profile"
                        type="button" role="tab" aria-controls="nav-profile" aria-selected="false">
                        <h5>Xếp lịch học</h5>
                    </button>
                </div>
            </nav>

            <h1>Bảng phân công giảng dạy</h1>
            <!-- Phân công giảng dạy -->
            <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
                <div class="list-subject">
                    <table class="table table-bordered table-hover">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">STT</th>
                                <th scope="col">Môn học</th>
                                <th scope="col">Ngày bắt đầu</th>
                                <th scope="col">Ngày kết thúc</th>
                                <th scope="col">Giờ bắt đầu</th>
                                <th scope="col">Giờ kết thúc</th>
                                <th scope="col">Thứ</th>
                                <th scope="col">Giảng viên</th>
                                <th scope="col">Trạng thái</th>
                                <th scope="col">Xác nhận</th>
                            </tr>
                        </thead>
                        <!-- render subject -->
                        <tbody>
                            <tr v-for="(subject, index) in subjects" :key="index">
                                <th scope="row" class = "align-middle">{{ index + 1 }}</th>
                                <td class = "align-middle">{{ subject.nameSubject}}</td>
                                <td class = "align-middle">
                                    <input v-if = "subject.beginDate !== null" type="datetime-local" class="form-control" :value="subject.beginDate">
                                    <input v-else type="datetime-local" class="form-control" v-model = "assignmentRequest.beginDate">
                                </td>
                                <td class = "align-middle">
                                    <input v-if = "subject.endDate !== null" type="datetime-local" class="form-control" :value="subject.endDate">
                                    <input v-else type="datetime-local" class="form-control" v-model = "assignmentRequest.endDate">
                                </td>

                                <td class = "align-middle">
                                    <input v-if = "subject.beginTime !== null" type="time" class="form-control" :value="subject.beginTime">
                                    <input v-else type="time" class="form-control" v-model = "assignmentRequest.beginTime">
                                </td>
                                <td class = "align-middle">
                                    <input v-if = "subject.endTime !== null" type="time" class="form-control" :value="subject.endTime">
                                    <input v-else type="time" class="form-control" v-model="assignmentRequest.endTime">
                                </td>
                                
                                <td class="align-middle">
                                <select v-if="subject.dayOfWeek !== null && subject.dayOfWeek !== undefined" class="form-select" style="min-width: 90px;">
                                    <option :value="subject.dayOfWeek">{{ getDayOfWeekName(subject.dayOfWeek) }}</option>
                                </select>

                                <select v-else class="form-select" style="min-width: 90px;" v-model="assignmentRequest.dayOfWeek">
                                    <option value="2">Thứ 2</option>
                                    <option value="3">Thứ 3</option>
                                    <option value="4">Thứ 4</option>
                                    <option value="5">Thứ 5</option>
                                    <option value="6">Thứ 6</option>
                                </select>
                                </td>

                                <td class = "align-middle">
                                    <select class="form-select" v-if ="subject.nameLecturer !== null" style="min-width: 150px;">
                                        <option :value="subject.lecturerId">{{ subject.nameLecturer }}</option>
                                    </select>

                                    <select v-else class="form-select" style="min-width: 150px;" @change="getLecturerFree">
                                        <option>Không có</option>
                                        <option v-for = "(lecturer, idx) in lecturers" :key = "idx"  :value = "lecturer.lecturerId"> {{ lecturer.nameLecturer }}</option>
                                    </select>

                                </td>
                                <td class = "align-middle">
                                    <span v-if="!subject.isClosed" class="badge bg-success">{{ getStatus(subject.isClosed) }}</span>
                                    <span v-else class="badge bg-danger">{{ getStatus(subject.isClosed) }}</span>
                                </td>

                                <td class = "align-middle">
                                    <span v-if = "isAssigned(subject.beginDate, subject.endDate, subject.beginTime, subject.endTime, subject.dayOfWeek)" class="badge bg-info text-dark">Đã phân công</span>
                                    <button v-else class="btn btn-info">Phân công</button>
                                </td>

                                <td>
                                    <button @click="getLecturerFree">Test thử</button>
                                </td>

                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Xếp lịch học -->
            <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                <!-- <div class="container pt-4">
                    <FullCalendar :options='calendarOptions' class="custom-calendar" />
                </div> -->
            </div>

        </div>
    </main>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue';
// import FullCalendar from '@fullcalendar/vue3';
// import dayGridPlugin from '@fullcalendar/daygrid';
import HomeHeader from '@/components/HomeHeader.vue';
import HomeSidebar from '@/components/HomeSidebar.vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import { getDayOfWeekName, getStatus} from '../../helpers/helpers';
import {isAssigned} from '../../helpers/assignhelper';
import { useStore } from 'vuex';
// const calendarOptions = ref({
//     plugins: [dayGridPlugin],
//     initialView: 'dayGridMonth',
//     weekends: false,
//     events: [],
//     scrollTime: '08:00:00'
// });

// Hàm để tạo sự kiện cho giờ học piano từ 8 giờ đến 10 giờ
// function createPianoEvents() {
//     const events = [];
//     // Thời gian bắt đầu và kết thúc cho giờ học piano
//     const startTime = new Date();
//     startTime.setHours(8, 0, 0); // 8 giờ sáng
//     const endTime = new Date();
//     endTime.setHours(10, 0, 0); // 10 giờ sáng

//     // Tạo sự kiện cho giờ học piano
//     events.push({ title: 'Giờ học piano', start: startTime, end: endTime });

//     return events;
// }

// get subjects
interface Subject {
    subjectId: number,
    nameSubject: string |null,
    estimate: number | null,
    beginDate: string,
    endDate: string, 
    beginTime: string, 
    endTime: string, 
    isClosed: boolean,
    dayOfWeek: number,
    lecturerId: number, 
    nameLecturer: string
}

const subjects = ref<Subject[]>([]);

const getSubject = async (classroomId: number) => {
    const response = axios.get<Subject[]>("http://localhost:8080/course-management/admin/assign-classroom/" + classroomId, {
        headers: {
            'Authorization': `Bearer ${jwtToken.value}`
        }
    })
    try {
        subjects.value = (await response).data;
        console.log("Sending data: classroomId = " + classroomId)
        console.log("Received data: subjecs = " + subjects.value)
        console.log("Data: " + subjects.value)
    } catch (error) {
        console.log("Get subject failure!!", error)
        console.log(error);
        console.log("Data: " + subjects.value)
        console.log("subjects[0]" + subjects.value[0]);

    }
}

// -----------------------------Recommened Lecturer----------------
interface AssignmentRequest{
    beginDate: string,
    endDate: string, 
    beginTime: string, 
    endTime: string, 
    dayOfWeek: number
}

const assignmentRequest = ref<AssignmentRequest>({
    beginDate: '', 
    endDate: '',
    beginTime: '',
    endTime: '', 
    dayOfWeek: -1
});

interface Lecturer {
    lecturerId: number, 
    nameLecturer: string
}
const store = useStore();
const jwtToken = computed(() => store.getters.getAccessToken)

const lecturers = ref<Lecturer[]>([]);

const getLecturerFree = async () => {
    try {
        const response = axios.post<Lecturer[]>("http://localhost:8080/course-management/admin/assign-classroom/get-lecturer-free", assignmentRequest.value, {
            headers: {
            'Authorization': `Bearer ${jwtToken.value}`
            }  
        });
        lecturers.value = (await response).data;
        console.log("Get free lecturer successful")
        console.log("Đã gọi hàm getLecturer")
        console.log("Lecturer: ", lecturers.value)
    } catch(error) {
        console.log("Encountered error at get free lecturer: " + error)
        console.log("Đã gọi hàm getLecturer")
        console.log("Lecturer: ", lecturers.value)
    }
}
// const refreshAssignmentRequest = (assignmentRequest : AssignmentRequest) => {
//     assignmentRequest.beginDate='', 
//     assignmentRequest.endDate = '', 
//     assignmentRequest.beginTime = '', 
//     assignmentRequest.endTime = '', 
//     assignmentRequest.dayOfWeek = -1;
// }

watch([assignmentRequest.value.beginDate, assignmentRequest.value.endDate, assignmentRequest.value.beginTime, assignmentRequest.value.endTime, assignmentRequest.value.dayOfWeek], () => {
    if (
        assignmentRequest.value.beginDate !== null &&
        assignmentRequest.value.endDate !== null &&
        assignmentRequest.value.beginTime !== null &&
        assignmentRequest.value.endTime !== null &&
        assignmentRequest.value.dayOfWeek !== -1
    ) {
        getLecturerFree();
    }
});

// ------------------------------
// const selectedAssignment = ref<AssignmentRequest[]>([])
const route = useRoute();
onMounted(() => {
    const classroomId = Number(route.params.classroomId);
    getSubject(classroomId);
});

</script>

<style>
.custom-calendar {
    width: 100%;
    height: 500px;
    overflow-x: auto;
    /* Thêm cuộn ngang */
}

.bg-nws {
    background-color: #003E83;
}
</style>