<template>

    <div class="container d-flex justify-content-center flex-column">
        <div class="row">
            <div class="col-md-12">
                <h3 style="color: #8B4513" class="fw-bold mb-3">Chi tiết khoá học</h3>
                <div class="arcodition-heading">
                    <h3 class="fw-bold mb-3" style="color: #8B4513">Tên khoá học: {{ course?.nameCourse }}</h3>
                    <h3 class="fw-bold mb-3 d-flex justify-content-start" style="color: #8B4513">Nội dung khoá học:</h3>
                </div>
                <!-- Content of the course -->
                <div class="accordion" id="accordionExample">
                    <div class="accordion-item" v-for="(arcodition, index) in arcoditions" :key="index">
                        <h2 class="accordion-header" id="headingOne">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                <h4 class = "fw-bold">{{ arcodition.title }}</h4>
                            </button>
                        </h2>
                        <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne"
                            data-bs-parent="#accordionExample">
                            <div class="accordion-body">
                                <h5 style="color: #555555; margin-left: 60px" v-html="arcodition.content.replace(/\n/g, '<br>')" class = "text-start"></h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div> <br>
        <div class="row d-flex justify-content-center">
            <div class="col-md-8">
                <hr>
                <h3 class="fw-bold">Đăng ký khoá học ngay để nhận nhiều ưu đãi hấp dẫn</h3>
                <div class="card" id = "id-card-form">
                    <div class="card-body">
                        <form id = "id-form">
                            <div class="mb-3">
                                <label for="nameId" class="form-label fw-bold"><h5>Họ và tên: </h5></label>
                                <input type="text" class="form-control" id="nameId" v-model="register.name" placeholder="Nhập họ và tên...">
                            </div>
                            <div class="mb-3">
                                <label for="emailId" class="form-label fw-bold"><h5>Email: </h5></label>
                                <input type="email" class="form-control" id="emailId" v-model="register.email" placeholder="Nhập email...">
                            </div>
                            <div class="mb-3">
                                <label for="addressId" class="form-label fw-bold"><h5>Địa chỉ: </h5></label>
                                <input type="text" class="form-control" id="addressId" v-model="register.address" placeholder="Nhập địa chỉ...">
                            </div>
                            <div class="mb-3">
                                <label for="phoneId" class="form-label fw-bold"><h5>Số điện thoại: </h5></label>
                                <input type="tel" class="form-control" id="phoneId" v-model="register.phone"
                                    placeholder="Nhập số điện thoại...">
                            </div>
                            <div class="mb-3">
                                <label for="select-classroom" class="form-label input-register fw-bold"><h5>Chọn lớp học: </h5></label>
                                <select class="form-select" id="select-classroom" v-model="register.classroomId">
                                    <option value="" disabled selected hidden>Chọn lớp học</option>
                                    <option v-for="(classroom, index) in classroomOfCourse" :key="index"
                                        :value="classroom.classroomId">
                                        {{ classroom.nameCourse }} - Thời gian học: {{ classroom.beginDate }} - {{
                                            classroom.endDate }}
                                    </option>
                                </select>
                            </div>
                            <button type="button" class="btn btn-primary" id = "id-btn-register"
                                @click="sendMailRegister(register.classroomId, courseId, register)">Đăng ký</button>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { CourseResponse } from '@/interface/CourseResponse';
import { Arcodition } from '@/interface/home/Arcodition';
import { ClassroomOfCourse } from '@/interface/home/ClassroomOfCourse';
import { getCourseById } from '@/interface/home/CourseService';
import { Register } from '@/interface/home/Register';
import { getClassroom, processText, sendMailRegister } from '@/service/home/DetailCourseService';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const courseId = Number(route.query.courseId);
const arcoditions = ref<Arcodition[]>([]);
const classroomOfCourse = ref<ClassroomOfCourse[]>([]);
const register = ref<Register>({
    name: '',
    phone: '',
    address: '',
    email: '',
    classroomId: -1,
    courseId: -1
});

const course = ref<CourseResponse>({
    courseId: courseId,
    nameCourse: '',
    createdAt: '',
    numClass: 0,
    isClosed: false,
    url: '',
    description: '',
    price: 0,
});

onMounted(async () => {
    console.log("courseId after onMounted: ", courseId);
    try {
        const courseData = await getCourseById(courseId);
        course.value = courseData;
        const dataArcodition = await processText(course.value.description); // process description
        arcoditions.value = dataArcodition;
        console.log("Nội dung khoá học sau xử lý là: ", arcoditions)
        const classrooms = await getClassroom(courseId); // get classroom of course
        classroomOfCourse.value = classrooms;

        // print log
        console.log("courseId: ", courseId);
        console.log("course: ", course.value)
    } catch (error) {
        console.error("Failure when get course", error);
        console.log("courseId: ", courseId);
        console.log("course: ", course.value)
    }
});
</script>
<style>
@import url(DetailCourseGuestView.css);
</style>