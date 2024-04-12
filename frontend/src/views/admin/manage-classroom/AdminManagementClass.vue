<template>
    <header>
        <HomeSidebar></HomeSidebar>
        <HomeHeader></HomeHeader>
    </header>

    <!-- Content -->
    <main style="margin-top: 58px; margin-left: 60px;">
        <!-- content -->
        <div class="container pt-4">
            <h3>Quản lý lớp học</h3><br>
            <div class="row">
                <!-- Bộ lọc -->
                <div class="col-8">
                    <select class="form-select form-select-lg" aria-label="Default select example"
                        v-model="selectedCourseId" @change="handleChange">
                        <option :value="null">Tuỳ chọn khoá học</option>
                        <option v-for="course in selectCourses" :value="course.courseId" :key="course.courseId">
                            {{ course.nameCourse }}
                        </option>
                    </select>
                    <br>
                    <button class="btn btn-primary" @click="getClassroomsMain">Xem</button>
                </div>

                <!-- Button tạo lớp học -->
                <div class="col-4">
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                        Tạo lớp học +
                    </button>
                </div>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Tạo lớp học mới</h5>
                        </div>
                        <!-- Nhập thông tin -->
                        <div class="modal-body">
                            <div class="form-group row">
                                <label for="codeClassId" class="col-sm-3 col-form-label">Mã lớp học:</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="codeClassId"
                                        v-model="createClassroom.codeClassroom">
                                </div>
                            </div><br>

                            <!-- Ngày kết thúc, bắt đầu -->
                            <div class="form-group row">
                                <label for="beginDateId" class="col-sm-3 col-form-label">Ngày bắt đầu:</label>
                                <div class="col-sm-9">
                                    <input type="date" id="beginDateId" class="form-control"
                                        v-model="createClassroom.beginDate">
                                </div>
                            </div><br>

                            <div class="form-group row">
                                <label for="endDateId" class="col-sm-3 col-form-label">Ngày kết thúc: </label>
                                <div class="col-sm-9">
                                    <input type="date" id="endDateId" class="form-control"
                                        v-model="createClassroom.endDate">
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"
                                @click="postClassroomMain">Tạo lớp</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- CÁC TAB -->
            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#home"
                        type="button" role="tab" aria-controls="home" aria-selected="true">Danh sách lớp</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile"
                        type="button" role="tab" aria-controls="profile" aria-selected="false">Quản lý trạng thái
                        lớp học</button>
                </li>
            </ul>
            <!-- Danh sách lớp học -->
            <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                <div class="row">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Mã lớp</th>
                                <th scope="col">Ngày bắt đầu</th>
                                <th scope="col">Ngày kết thúc</th>
                                <th scope="col">Thời gian học dự kiến</th>
                                <th scope="col">Số lượng sinh viên</th>
                                <th scope="col">Trạng thái</th>
                                <!-- Action -->
                                <th scope="col"></th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(classroom, index) in classrooms" :key="index">
                                <th scope="row">{{ index + 1 }}</th>
                                <td>{{ classroom.codeClassroom }}</td>
                                <td>{{ formatDateTime(classroom.beginDate) }}</td>
                                <td>{{ formatDateTime(classroom.endDate) }}</td>
                                <td>{{ classroom.sumPeriod }} tháng</td>
                                <td>{{ classroom.sumStudent }} học viên</td>
                                <td>
                                    <!-- <span class="badge bg-warning text-dark" v-if="classroom.isFinished">Đã xong</span>
                                    <span class="badge bg-success" v-else>Đang diễn ra</span> -->
                                </td>

                                <!-- Action -->
                                <td>
                                    <button type="button" data-bs-toggle="modal"
                                        data-bs-target="#listStudent"
                                        @click="getEnrollmentOfClassroom(classroom.classroomId)">
                                        <i class="fas fa-bars"></i>
                                    </button>

                                    <!-- Modal -->
                                    <div class="modal fade" id="listStudent" tabindex="-1"
                                        aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel" style="color: #555555">Danh sách sinh viên
                                                    </h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                                </div>
                                                <!-- Danh sách sinh viên -->
                                                <div class="modal-body">
                                                    <table class="table">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">STT</th>
                                                                <th scope="col">Email</th>
                                                                <th scope="col">SĐT</th>
                                                                <th scope="col">Địa chỉ</th>
                                                                <!-- <th scope="col">Ngày đăng ký</th> -->
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr v-for="(enrollment, index) in listEnrollmentOfClassroom"
                                                                :key="index">
                                                                <th scope="row">{{ index + 1 }}</th>
                                                                <td>{{ enrollment.email }}</td>
                                                                <td>{{ enrollment.phone }}</td>
                                                                <td>{{ enrollment.address }}</td>
                                                                <!-- <td>{{ enrollment.enrollmentDay }}</td> -->
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary"
                                                        data-bs-dismiss="modal">Đóng</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                              
                                <!-- Phân công -->
                                <td>
                                    <router-link :to="{ name: 'adminAssignClassroom', params: { classroomId: classroom.classroomId }}" class="btn btn-primary">Chi tiết</router-link>
                                </td>
                                <router-view></router-view>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Quản lý trạng thái lớp học -->
            <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                <div class="card" style="width: 30rem;">
                    <img class="card-img-top" src="https://static.unica.vn/upload/images/2024/02/cat-ghep-Banner-Poster-san-pham-don-giang-bang-Photoshop.jpg_m_1709088443.jpg" alt="Card image cap">

                    <div class="card-body">
                        <h5 class="card-title">Lớp Java 1</h5>
                        <p class="card-text">Descritpion</p>
                        <div class = "card-text">
                            <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">Môn học</th>
                                    <th scope="col">Lịch học</th>
                                    <th scope="col">Ngày thi</th>
                                    <th scope="col">Giảng viên</th>
                                    <th scope="col">Trạng thái</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th>1</th>
                                    <th>Lịch học</th>
                                    <th>Ngày thi</th>
                                    <td>Giảng viên</td>  
                                    <td>Trạng thái</td> 
                                </tr>
                            </tbody>
                            </table>
                        </div>
                    </div>
                   
                    <div class="card-body">
                        <button class = "btn btn-warnings">Đóng lớp</button>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>

<script setup lang="ts">
import HomeHeader from '@/components/HomeHeader.vue';
import HomeSidebar from '@/components/HomeSidebar.vue';
import axios from 'axios';
import { formatDateTime } from '@/helpers/timehelpers';
import { ref, onMounted, computed } from 'vue';
import { useStore } from 'vuex';
import { Enrollment } from '@/interface/admin/Enrollment';
import { Classroom } from '@/interface/admin/Classroom';
import { CreateClassroom } from '@/interface/admin/CreateClassroom';
import { SelectCourse } from '@/interface/admin/SelectCourse';
import { getOptionSelect } from '@/service/admin/AdminManagementClass';
import { getClassrooms } from '@/service/admin/AdminManagementClass';
import { postClassroom } from '@/service/admin/AdminManagementClass';

const store = useStore();
const jwtToken = computed(() => store.getters.getAccessToken)
const selectCourses = ref<SelectCourse[]>([]);

// classroomId
const selectedCourseId = ref<number | null>(null);

// create course data
const createClassroom = ref<CreateClassroom>({
    courseId: null,
    codeClassroom: '',
    beginDate: '',
    endDate: ''
});

// get enrollment
const listEnrollmentOfClassroom = ref<Enrollment[]>([]);

const handleChange = (event: Event) => {
    const target = event.target as HTMLSelectElement;
    const selectedOption = target.value;
    const selectedId = parseInt(selectedOption, 10);
    console.log("handlechange đã được gọi")
    if (!isNaN(selectedId)) {
        selectedCourseId.value = selectedId;
        console.log("handleChange đã được gọi: , selectedCourseId = ", selectedCourseId.value)
        createClassroom.value.courseId = selectedCourseId.value;
    } else {
        console.error('Không có lựa chọn hoặc giá trị không hợp lệ');
    }
};

const getOptionSelectCourse = async () => {
    try {
        const optionCourse = await getOptionSelect(jwtToken.value);
        selectCourses.value = optionCourse;
    } catch(error) {
        console.log("Have a error when fetch course")
    }
}

// creat classroom
const postClassroomMain = async() => {
    try {
        postClassroom(jwtToken.value, createClassroom.value, selectedCourseId?.value ?? -1);
    } catch(error) {
        console.log("Have a error when post classroom!!")
    }
}
// post classroom 
// const postClassroom = async () => {
//     try {
//         const response = await axios.post("http://localhost:8080/course-management/admin/management-class/add-classroom", createClassroom.value, {
//             headers: {
//             'Authorization': `Bearer ${jwtToken.value}`
//             }
//         });

//         alert("Thêm môn lớp học thành công!!!")
//         getClassroomsMain();
//         console.log('Dữ liệu đã được gửi thành công:', response.data);
//         console.log('Dữ liệu gửi đi là: ', createClassroom)
//     } catch (error: any) {
//         if (error && error.response && error.response.data && error.response.data.message === 'Mã lớp đã bị trùng') {
//             console.error('Mã lớp đã bị trùng');
//             alert("Mã lớp đã tồn tại trong cơ sở dữ liệu")
//             console.log('Dữ liệu gửi đi là: ', createClassroom)
//             console.log("postClassroom, CourseId: ", createClassroom)
//         } else {
//             console.error('Lỗi khi tạo lớp:', error);
//             alert("Lỗi khi tạo lớp")
//             console.log('Dữ liệu gửi đi là: ', createClassroom);
//         }
//     }
// };

// get course render on select
const classrooms = ref<Classroom[]>([]);

const getClassroomsMain = async () => {
    try {
        const data = await getClassrooms(jwtToken.value, selectedCourseId?.value ?? -1);
        classrooms.value = data;
    } catch(error) {
        console.log('Have a error when fetch classroom: ', error)
    }
}

// get enrollment
const getEnrollmentOfClassroom = async (classroomId: number | null) => {
    try {
        if (classroomId === null) {
            console.error("classroomId is null.");
            return;
        }

        const response = await axios.get<Enrollment[]>(`http://localhost:8080/course-management/admin/management-class/get-students/${classroomId}`, {
            headers: {
            'Authorization': `Bearer ${jwtToken.value}`
            }
        });
        listEnrollmentOfClassroom.value = response.data;
        console.log("Dữ liệu nhận được: ", listEnrollmentOfClassroom.value);
    } catch (error) {
        console.error("Có lỗi khi lấy danh sách lớp học", error);
    }
}

onMounted(getOptionSelectCourse);
</script>

<style>
@import url(AdminManagementClass.css);
</style>