<template>
    <header>
        <HomeSidebar></HomeSidebar>
        <HomeHeader></HomeHeader>
    </header>

    <!-- Content -->
    <main style="margin-top: 58px;">
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
                    <button class="btn btn-primary" @click="getClassrooms">Xem</button>
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
                                @click="postClassroom">Tạo lớp</button>
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
                                <td scope="col">Trạng thái</td>
                                <!-- Action -->
                                <td scope="col"></td>
                                <td scope="col"></td>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(classroom, index) in classrooms" :key="index">
                                <th scope="row">{{ index + 1 }}</th>
                                <td>{{ classroom.codeClassroom }}</td>
                                <td>{{ classroom.beginDate }}</td>
                                <td>{{ classroom.endDate }}</td>
                                <td>{{ classroom.sumPeriod }} tháng</td>
                                <td>{{ classroom.sumStudent }} học viên</td>
                                <td>Trạng thái her</td>

                                <!-- Action -->
                                <td>
                                    <button type="button" class="btn list-btn" data-bs-toggle="modal"
                                        data-bs-target="#listStudent"
                                        @click="getEnrollmentOfClassroom(classroom.classroomId)"></button>


                                    <!-- Modal -->
                                    <div class="modal fade" id="listStudent" tabindex="-1"
                                        aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Danh sách sinh viên
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
                                                                <th scope="col">Ngày đăng ký</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr v-for="(enrollment, index) in listEnrollmentOfClassroom"
                                                                :key="index">
                                                                <th scope="row">{{ index + 1 }}</th>
                                                                <td>{{ enrollment.email }}</td>
                                                                <td>{{ enrollment.phone }}</td>
                                                                <td>{{ enrollment.address }}</td>
                                                                <td>{{ enrollment.enrollmentDay }}</td>
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
                                <!-- Sửa đổi -->
                                <td> 
                                    <button type="button" class="edit-btn" data-bs-toggle="modal" data-bs-target="#exampleModal"></button>
                                
                                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Tạo lớp học mới</h5>
                                                </div>
                                                <!-- Nhập thông tin -->
                                                <div class="modal-body">
                                                    <!-- Ngày kết thúc, bắt đầu -->
                                                    <div class="form-group row">
                                                        <label for="beginDateId" class="col-sm-3 col-form-label">Ngày bắt đầu:</label>
                                                        <div class="col-sm-9">
                                                            <input type="date" id="beginDateId" class="form-control">
                                                        </div>
                                                    </div><br>

                                                    <div class="form-group row">
                                                        <label for="endDateId" class="col-sm-3 col-form-label">Ngày kết thúc: </label>
                                                        <div class="col-sm-9">
                                                            <input type="date" id="endDateId" class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"
                                                        >Cập nhật</button>
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
import { ref, onMounted, computed } from 'vue';
import { useStore } from 'vuex';

interface SelectCourse {
    courseId: number;
    nameCourse: string | null;
}
const store = useStore();
const jwtToken = computed(() => store.getters.getAccessToken)

const selectCourses = ref<SelectCourse[]>([]);

//Lưu Id khoá học
const selectedCourseId = ref<number | null>(null);

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

const getOptionSelect = async () => {
    try {
        const response = await axios.get<SelectCourse[]>('http://localhost:8080/course-management/admin/management-class/select-course', {
            headers: {
            'Authorization': `Bearer ${jwtToken.value}`
            }
        });
        selectCourses.value = response.data;
        console.log('Dữ liệu đã được lấy thành công:', selectCourses.value);
        console.log("CourseId: " + selectedCourseId.value)
    } catch (error) {
        console.error('Lỗi khi lấy dữ liệu:', error);
    }
}

// Tạo lớp học 
interface CreateClassroom {
    courseId: number | null;
    codeClassroom: string;
    beginDate: string;
    endDate: string;
}

const createClassroom = ref<CreateClassroom>({
    courseId: null,
    codeClassroom: '',
    beginDate: '',
    endDate: ''
});

// post classroom 
const postClassroom = async () => {
    try {
        const response = await axios.post("http://localhost:8080/course-management/admin/management-class/add-classroom", createClassroom.value, {
            headers: {
            'Authorization': `Bearer ${jwtToken.value}`
            }
        });

        alert("Thêm môn lớp học thành công!!!")
        getClassrooms();
        console.log('Dữ liệu đã được gửi thành công:', response.data);
        console.log('Dữ liệu gửi đi là: ', createClassroom)
    } catch (error: any) {
        if (error && error.response && error.response.data && error.response.data.message === 'Mã lớp đã bị trùng') {
            console.error('Mã lớp đã bị trùng');
            alert("Mã lớp đã tồn tại trong cơ sở dữ liệu")
            console.log('Dữ liệu gửi đi là: ', createClassroom)
            console.log("postClassroom, CourseId: ", createClassroom)
        } else {
            console.error('Lỗi khi tạo lớp:', error);
            alert("Lỗi khi tạo lớp")
            console.log('Dữ liệu gửi đi là: ', createClassroom);
        }
    }
};

// -------------------Danh sách khoá học------------------------
interface Classroom {
    classroomId: number | null,
    beginDate: string,
    endDate: string,
    codeClassroom: string,
    sumPeriod: number | null,
    sumStudent: number | null
}

const classrooms = ref<Classroom[]>([]);

const getClassrooms = async () => {
    try {
        const response = axios.get<Classroom[]>("http://localhost:8080/course-management/admin/management-class/" + selectedCourseId.value, {
            headers: {
            'Authorization': `Bearer ${jwtToken.value}`
            }
        });
        classrooms.value = (await response).data
        console.log(classrooms)
    } catch (error) {
        console.log("Có lỗi khi lấy danh sách lớp học")
    }
}

// ------------------List enrollment-------------------
interface Enrollment {
    userId: number | null,
    email: string | null,
    address: string | null,
    enrollmentDay: string | null,
    phone: string | null,
}

const listEnrollmentOfClassroom = ref<Enrollment[]>([]);

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


onMounted(getOptionSelect);


</script>

<style>
.list-btn {
    background-image: url('/src/assets/list.png');
    background-size: cover;
    width: 40px;
    height: 40px;
    background-color: #28a745;
    border: none;
    cursor: pointer;
    border-radius: 5px;
    transition: background-color 0.3s ease;
}

.list-btn:hover {
    background-color: #218838;
    ;
}

.modal-dialog {
    width: 800px;
    max-width: 800px;
}

#edit-course-btn {
    width: 150px;
    height: 40px;
  }

.edit-btn {
    background-image: url('/src/assets/edit.png');
    background-size: cover;
    width: 40px;
    height: 40px;
    background-color: #ffc107;
    border: none;
    cursor: pointer;
    border-radius: 5px;
    transition: background-color 0.3s ease;
}
</style>