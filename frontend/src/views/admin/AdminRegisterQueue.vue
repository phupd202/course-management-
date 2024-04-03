<template>
    <header>
        <HomeSidebar></HomeSidebar>
        <HomeHeader></HomeHeader>
    </header>

    <main style="margin-top: 58px; margin-left: 60px;">
        <div class="container pt-4">
            <h2 style="color: rgb(68, 68, 68);">Đơn đăng ký</h2> <br>
            <div class="container">
                <div class="row justify-content-center"> <!-- Sử dụng lớp justify-content-center để canh giữa -->
                    <div class="col-md-6"> <!-- Sử dụng col-md-6 để giới hạn chiều rộng -->
                        <div class="row">
                            <div class="col-md-6">
                                <select class="form-select mb-3" aria-label="Default select example" id="ad-course-select1" v-model = "selectedCourse" @change="fetchClassroom">
                                    <option selected>.......</option>
                                    <option v-for = "(course, index) in allCourses" :key = "index" :value = "course.courseId">{{ course.nameCourse }}</option>
                                </select>
                            </div>

                            <div class="col-md-6">
                                <select class="form-select mb-3" aria-label="Default select example" id="ad-course-select2" v-model = "selectedClassroom">
                                    <option selected>......</option>
                                    <option v-for = "(classsroom, index) in classrooms" :key = "index" :value = "classsroom.classroomId"> {{ classsroom.nameCourse }}: {{ classsroom.beginDate }} - {{ classsroom.endDate }}</option>
                                </select>
                            </div>
                            <button class="btn btn-primary d-block mx-auto" @click="fetchRegister">Lọc</button> <br>
                        </div>
                    </div>
                </div>
            </div>

            <br>
            <div class = "row">
                <table class="table">
                <thead>
                    <tr>
                        <th scope="col">STT</th>
                        <th scope="col">Họ và tên</th>
                        <th scope="col">Số điện thoại</th>
                        <th scope="col">Email</th>
                        <th scope="col">Địa chỉ</th>
                        <th scope="col">Thêm vào lớp</th>
                        <th scope="col">Tạo tài khoản</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(register, index) in registers" :key = "index">
                        <th scope="row">{{ index + 1 }}</th>
                        <td>{{ register.name }}</td>
                        <td>{{ register.phone }}</td>
                        <td>{{ register.email }}</td>
                        <td>{{ register.address}}</td>
                        <td>
                            <input type="checkbox" v-if="register.addedClassroom" checked @change="addClasroom(register.registerId)" v-model="register.addedClassroom">
                            <input type = "checkbox" v-else @change="addClasroom(register.registerId)"  v-model="checkboxAdd" >
                        </td>
                        <td>
                            <span v-if="register.createdAccount" class="badge rounded-pill bg-success">Đã tạo tài khoản</span>
                            <button v-else class="btn btn-primary" style="margin-left: 20px" @click="createAccountLogin(register.email, register.phone, register.registerId)">Tạo tài khoản</button>
                        </td>
                    </tr>
                </tbody>
                </table>
            </div>
        </div>
    </main>
</template>

<script setup lang="ts">
import HomeHeader from '@/components/HomeHeader.vue';
import HomeSidebar from '@/components/HomeSidebar.vue';
import {getAllCourses, getClassroom} from '../../service/home/CourseService';
import { CourseResponse } from '@/interface/CourseResponse';
import { computed, onMounted, ref } from 'vue';
import { ClassroomOfCourse } from '@/interface/ClassroomOfCourse';
import {getRegister} from '../../service/admin/RegisterService';
import { RegisterResponse } from '@/interface/RegisterResponse';
import axios from 'axios';
import { CreateAccount } from '@/interface/CreateAccount';
import { useStore } from 'vuex';

const allCourses = ref<CourseResponse[]>([]);
let classrooms = ref<ClassroomOfCourse[]>([])
let selectedCourse = ref<number>(-1);
let selectedClassroom = ref<number>(-1);
let registers = ref<RegisterResponse[]>([]);
let checkboxAdd:boolean = false;
const store = useStore();
const jwtToken = computed(() => store.getters.getAccessToken)

const createAccountLogin = async(email : string, phone: string, registerId: number) => {
    // create data to send server
    const createAccount = {
        email: email,
        phone: phone,
        classroomId: selectedClassroom.value,
        registerId: registerId
    };

    try {
        const response = axios.post<CreateAccount>("http://localhost:8080/course-management/admin/create-account", createAccount, {
            headers: {
            'Authorization': `Bearer ${jwtToken.value}`
            }
        })
        console.log("Create account successfully!", response)
        alert("Gửi mail tạo tài khoản thành công!")
        fetchRegister();
    } catch(error) {
        console.log("Error while create account")
    }
}

const addClasroom = async(registerId : number) => {
    try {
        if(checkboxAdd !== null) {
            const response = axios.post<boolean>("http://localhost:8080/course-management/admin/add-register/" + registerId, checkboxAdd.toString())
            console.log("Add successfully", (await response).data)
            alert("Cập nhật thành công!")
            checkboxAdd = false;
        } else {
            alert("Vui lòng chọn lại!");
        }
    } catch(error) {
        console.log("Error while add classroom")
    }
}

const fetchRegister = async () => {
    try {
        const fetchedRegister = await getRegister(selectedClassroom.value, selectedCourse.value);
        registers.value = fetchedRegister;
    } catch (error) {
        console.error("Error fetching register:", error);
    }
}

const fetchClassroom = async () => {
    try {
        const fetchedClassroom = await getClassroom(selectedCourse.value);
        console.log("selectedCourse" + selectedCourse.value)
        classrooms.value = fetchedClassroom;
        console.log("Fetched all classrooms:", classrooms.value);
    } catch(error) {
        console.log("selectedCourse" + selectedCourse.value)
        console.error("Error fetching classroom:", error);
    }
};

onMounted(async () => {
    try {
        const courses = await getAllCourses();
        allCourses.value = courses;
        console.log("Fetched all courses:", courses);
    } catch(error) {
        console.error("Error fetching courses:", error);
    }
}
);
</script>