<template>

    <div class="container d-flex justify-content-center flex-column">
        <div class="row">
            <div class="col-md-12">
                <h3>Chi tiết khoá học</h3>
                <div class="arcodition-heading">
                    <img :src="course?.url" alt="Ảnh khoá học" style="min-width: 100%; height: 200px;">
                    <h3 class="text-primary fw-bold mb-3">Tên khoá học: {{ course?.nameCourse }}</h3>
                    <h3 class="text-secondary mb-3 d-flex justify-content-start">Nội dung khoá học:</h3>
                </div>
                <!-- Content of the course -->

                <div class="accordion" id="accordionExample">
                    <div class="accordion-item" v-for="(arcodition, index) in arcoditions" :key="index">
                        <h2 class="accordion-header" id="headingOne">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                <h4>{{ arcodition.title }}</h4>
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
            <div class="col-md-10">
                <hr>
                <h3>Đăng ký khoá học ngay để nhận nhiều ưu đãi hấp dẫn</h3>
                <form>
                    <div class="mb-3">
                        <label for="nameId" class="form-label"><h5>Họ và tên: </h5></label>
                        <input type="text" class="form-control" id="nameId" v-model="register.name" placeholder="Nhập họ và tên...">
                    </div>
                    <div class="mb-3">
                        <label for="emailId" class="form-label"><h5>Email: </h5></label>
                        <input type="email" class="form-control" id="emailId" v-model="register.email" placeholder="Nhập email...">
                    </div>
                    <div class="mb-3">
                        <label for="addressId" class="form-label"><h5>Địa chỉ: </h5></label>
                        <input type="text" class="form-control" id="addressId" v-model="register.address" placeholder="Nhập địa chỉ...">
                    </div>
                    <div class="mb-3">
                        <label for="phoneId" class="form-label"><h5>Số điện thoại: </h5></label>
                        <input type="tel" class="form-control" id="phoneId" v-model="register.phone"
                            placeholder="Nhập số điện thoại...">
                    </div>
                    <div class="mb-3">
                        <label for="select-classroom" class="form-label input-register"><h5>Chọn lớp học: </h5></label>
                        <select class="form-select" id="select-classroom" v-model="register.classroomId">
                            <option disabled value="" selected>Chọn lớp học</option>
                            <option v-for="(classroom, index) in classroomOfCourse" :key="index"
                                :value="classroom.classroomId">
                                {{ classroom.nameCourse }} - Thời gian học: {{ classroom.beginDate }} - {{
                                    classroom.endDate }}
                            </option>
                        </select>
                    </div>
                    <button type="button" class="btn btn-primary"
                        @click="sendMailRegister(register.classroomId, courseId)">Đăng ký</button>
                </form>
            </div>
        </div>
    </div>
</template>


<script setup lang="ts">
import { CourseResponse } from '@/interface/CourseResponse';
import { getCourseById } from '@/service/home/CourseService';
import axios from 'axios';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const courseId = Number(route.query.courseId);

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

// Register
interface Register {
    name: string,
    phone: string,
    address: string,
    email: string,
    classroomId: number,
    courseId: number
}

const register = ref<Register>({
    name: '',
    phone: '',
    address: '',
    email: '',
    classroomId: -1,
    courseId: -1
});

const sendMailRegister = async (classroomId: number, courseId: number) => {
    try {
        register.value.classroomId = classroomId;
        register.value.courseId = courseId;
        const response = axios.post<Register>("http://localhost:8080/course-management/mail-confirm", register.value);
        console.log("The data was sent: ", (await response).data)
        alert("Đăng ký thành công, vui lòng kiểm tra hòm thư của bạn!!");
    } catch (error) {
        console.log("Have a error while sendMailRegister")
        alert("Vui lòng thử lại!")
    }
}

// get class by Id
interface ClassroomOfCourse {
    classroomId: number,
    nameCourse: string,
    beginDate: string,
    endDate: string
}

const classroomOfCourse = ref<ClassroomOfCourse[]>([]);

const getClassroom = async (courseId: number) => {
    try {
        const response = axios.get<ClassroomOfCourse[]>("http://localhost:8080/course-management/get-class/" + courseId);
        classroomOfCourse.value = (await response).data;
        console.log("Data đã nhận được", classroomOfCourse.value)
    } catch (error) {
        console.log("Failure when get class of course", error)
    }
}

// ---------------Xử lý phần nội dung
interface Arcodition {
    title: string,
    content: string
}

const arcoditions = ref<Arcodition[]>([]);

const processText = (text: string) => {
    console.log("Đã chạy hàm processText: ")
    const lines = text.split("\n");
    let arcodition: Arcodition[] = [];
    let currentItem: Arcodition | null = null;

    lines.forEach(line => {
        console.log("line ngoài if: ", line)
        if (line.match(/^\d+\./)) {
            if (currentItem !== null) {
                arcodition.push(currentItem);
            }
            // if is "1., 2. v..v" add into object.title
            currentItem = { title: line.replace(/^\d+\.\s/, ''), content: '\n' };
            console.log("line: ", line)
        } else if (line.match(/^-\s/)) {
            // if is "-" add into object.content
            if (currentItem !== null) {
                currentItem.content += "- " + line.replace(/^- /, '').replace(/\n/, '') + '\n';
            }
            console.log("content: ", line)
        }

        console.log('currentItem: ', currentItem)
    });

    // add last item into object
    if (currentItem !== null) {
        arcodition.push(currentItem);
    }

    return arcodition;
}


onMounted(async () => {
    console.log("courseId after onMounted: ", courseId);
    try {
        const courseData = await getCourseById(courseId)
        course.value = courseData;
        arcoditions.value = processText(course.value.description); // process description
        console.log("Nội dung khoá học sau xử lý là: ", arcoditions)
        getClassroom(courseId); // get classroom of course

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
.custom-h5 {
    display: block;
    color: #555555;
}
</style>