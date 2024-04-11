<template>
    <header>
        <HomeHeader></HomeHeader>
        <HomeSidebar></HomeSidebar>
    </header>

    <main style="margin-top: 58px; margin-left: 60px">
        <div class="container py-4">
            <h2 class="mb-4 text-general">Chi tiết lớp học</h2> <br>
            <div class="row d-flex justify-content-center mb-30 list-subject">
                <h3 style="margin-bottom: 50px;" class = "text-general">Danh sách môn học</h3>
                <div class="col-md-10">
                    <div class="accordion" id="accordionExample">
                        <!-- subject -->
                        <div class="accordion-item" v-for="(subject, index) in subjects" :key = "index">
                            <h2 class="accordion-header" id="headingOne">
                                <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                    {{ subject.nameSubject }}
                                    <div class = "label-assignment">
                                        <span v-if = "subject.nameLecturer===null" class="badge bg-warning text-dark ms-10"> Chưa phân công</span>
                                        <span v-else class="badge bg-success ms-2"> Đã phân công</span>
                                    </div>
                                </button>
                            </h2>
                            <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne"
                                data-bs-parent="#accordionExample">
                                <div class="accordion-body">
                                    <p><strong>Ngày bắt đầu: {{ subject.beginDate }}</strong></p>
                                    <p><strong>Ngày kết thúc: {{ subject.endDate }}</strong></p>
                                    <p><strong>Thời gian: Thứ {{ subject.dayOfWeek + 1}} - {{ subject.beginTime }} -{{ subject.endTime }}</strong></p>
                                    <p><strong>Thời gian học dự kiến: {{ subject.estimate }} tháng</strong></p>
                                    <p v-if="subject.nameLecturer == null"><strong>Giảng viên phụ trách: Chưa phân công</strong></p>
                                    <p v-else><strong>Giảng viên: {{ subject.nameLecturer }} - {{ subject.phoneLecturer }}</strong></p>
                                    <p v-if = "subject.nameLecturer == null"> <strong>Trống</strong></p>
                                    <p v-else> <strong> Email: {{ subject.emailLecturer }} </strong></p>
                                    <!-- data: subjectId, lecturerId -->
                                </div>
                            </div>
                        </div>
                        <!-- End subject -->
                    </div>
                </div>
            </div>


            <div class="row justify-content-center mt-50">
                <div class="col-md-6 assign-form">
                    <h3 class="text-center text-general" style="margin-top: 50px;">Phân công môn học</h3>
                    <form>
                        <div class="mb-3">
                            <label for="nameCourse" class="form-label">Tên môn học:</label>
                            <select class="form-select" id="nameCourse" v-model = "assignmentRequest.subjectId">
                                <!-- option môn học -->
                                <option value="" selected disable hidden>Chọn môn học</option>
                                <option v-for="(subject, index) in subjects" :key="index" :value="subject.subjectId"> {{ subject.nameSubject }}</option>
                            </select>
                        </div>

                        <div class="mb-3 d-flex">
                            <div class="me-3 flex-grow-1">
                                <label for="beginDate" class="form-label">Ngày bắt đầu:</label>
                                <input type="datetime-local" class="form-control" id="beginDate" v-model = " assignmentRequest.beginDate">
                            </div>
                            <div class="flex-grow-1">
                                <label for="endDate" class="form-label">Ngày kết thúc:</label>
                                <input type="datetime-local" class="form-control" id="endDate" v-model = " assignmentRequest.endDate">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="dayOfWeek" class="form-label">Thứ:</label>
                            <select class="form-select" id="dayOfWeek" v-model = " assignmentRequest.dayOfWeek">
                                <option value="0">Chủ Nhật</option>
                                <option value="1">Thứ 2</option>
                                <option value="2">Thứ 3</option>
                                <option value="3">Thứ 4</option>
                                <option value="4">Thứ 5</option>
                                <option value="5">Thứ 6</option>
                                <option value="6">Thứ 7</option>
                            </select>
                        </div>
                        <div class="mb-3 d-flex">
                            <div class="me-3 flex-grow-1">
                                <label for="beginTime" class="form-label">Giờ bắt đầu:</label>
                                <input type="time" class="form-control" id="beginTime" v-model=" assignmentRequest.beginTime">
                            </div>
                            <div class="flex-grow-1">
                                <label for="endTime" class="form-label">Giờ kết thúc:</label>
                                <input type="time" class="form-control" id="endTime" v-model = " assignmentRequest.endTime">
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary" @click="showSelected" style = "width: 150px;">Tìm giảng viên</button>
                        
                        <!-- if click "Tìm giảng viên" -> show this select -->
                    </form>

                    <div class="mb-3" v-show="isShowSelected">
                        <label for="nameLecturer" class="form-label">Giảng viên phù hợp:</label>
                            <select class="form-select" v-model = "assignmentRequest.lecturerId">
                                <option value="" selected disable hidden>Chọn giảng viên phù hợp</option>
                                <option v-for = "(lecturer, index) in lecturers" :key = "index" :value="lecturer.lecturerId">{{ lecturer.nameLecturer }}</option>
                            </select> <br>
                        <button class="btn btn-primary" style = "width: 150px;" @click="saveAssignmentWrapper">Phân công</button>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>

<script setup lang="ts">
import { computed, onMounted, ref} from 'vue';
import HomeHeader from '@/components/HomeHeader.vue';
import HomeSidebar from '@/components/HomeSidebar.vue';
import { useRoute } from 'vue-router';
import { useStore } from 'vuex';
import { SubjectOfClass } from '@/interface/admin/SubjectOfClass';
import { getLecturerFree, getSubject, saveAssignment } from '@/service/admin/AssignmentClassroomService';
import { AssignmentRequest } from '@/interface/admin/AssignmentRequest';
import { Lecturer } from '@/interface/admin/Lecturer';

// store
const store = useStore();
const jwtToken = computed(() => store.getters.getAccessToken)
const subjects = ref<SubjectOfClass[]>([]);

// param
const route = useRoute();
const classroomId = Number(route.params.classroomId);

// data
const lecturers = ref<Lecturer[]>([]);
const isShowSelected = ref(false);

const assignmentRequest = ref<AssignmentRequest>({
    beginDate: '',
    endDate: '',
    beginTime: '',
    endTime: '',
    dayOfWeek: -1, 
    subjectId: -1, 
    lecturerId: -1,
    classroomId: -1
});

const showSelected = (event : Event) => {
  event.preventDefault(); 
  isShowSelected.value = !isShowSelected.value; 
  fetchedFreeLecturer()
}

// const hideSelected = () => {
//     isShowSelected.value = false;
// }

const refreshForm = () => {
    assignmentRequest.value.beginDate = '',
    assignmentRequest.value.endDate = '',
    assignmentRequest.value.beginTime = '',
    assignmentRequest.value.endTime = '',
    assignmentRequest.value.dayOfWeek = -1, 
    assignmentRequest.value.subjectId = -1, 
    assignmentRequest.value.lecturerId = -1,
    assignmentRequest.value.classroomId = -1
}

const fetchedFreeLecturer = async () => {
    const fetchedLecturer = await getLecturerFree(jwtToken.value, assignmentRequest.value);
    lecturers.value = fetchedLecturer;
}

const saveAssignmentWrapper = async () => {
    assignmentRequest.value.classroomId = classroomId;
    try {
        await saveAssignment(jwtToken.value, assignmentRequest.value);

        // notification
        alert("Phân công thành công!")

        // reload data
        const fetchedSubject = await getSubject(classroomId, jwtToken.value);
        subjects.value = fetchedSubject;

        // refresh form
        refreshForm();
    } catch (error) {
        console.log("Error: ", error)
        alert("Phân công thất bại!")
        throw error;
    }
}

onMounted(async () => {
    // fetch subject of class
    const fetchedSubject = await getSubject(classroomId, jwtToken.value);
    subjects.value = fetchedSubject;
});
</script>

<style>
@import url(AssignClassroom.css);
</style>