<template>
    <header>
        <HomeSidebar></HomeSidebar>
        <HomeHeader></HomeHeader>
    </header>

    <main style="margin-top: 58px; margin-left: 60px;">
        <!-- content -->
        <div class="container pt-4">
            <h2 style="color: rgb(68, 68, 68);">Lịch đăng ký tư vấn đang chờ</h2> <br>
            <div class="container">
                <div class="row justify-content-center"> <!-- Sử dụng lớp justify-content-center để canh giữa -->
                    <div class="col-md-6"> <!-- Sử dụng col-md-6 để giới hạn chiều rộng -->
                        <div class="select-course">
                            <select class="form-select d-block mx-auto" aria-label="Default select example" id="ad-course-select" v-model="selectedCourseId">
                                <option selected>Chọn khoá học</option>
                                <option v-for="(courseResponse, index) in courseResponses " :key = "index" :value="courseResponse.courseId"> {{ courseResponse.nameCourse }}</option>
                            </select>
                            <br>
                            <button class="btn btn-primary d-block mx-auto" @click="getInterestPartyDtos(selectedCourseId)">Lọc</button> <br>
                        </div>
                    </div>
                </div>

                <div class = "row">
                    <table class="table">
                        <thead>
                            <tr>
                            <th scope="col">#</th>
                            <th scope="col">Họ và tên</th>
                            <th scope="col">Điện thoại</th>
                            <th scope="col">Email</th>
                            <th scope="col">Địa chỉ</th>
                            <th scope="col">Khoá học quan tâm</th>
                            <th scope="col">Đã hẹn</th>
                            <th scope="col">Email</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for = "(interestPartyDto, index) in interestPartyDtos" :key="index">
                            <th scope="row">{{  index + 1 }}</th>
                            <td>{{  interestPartyDto.name }}</td>
                            <td>{{  interestPartyDto.phone }}</td>
                            <td>{{  interestPartyDto.email }}</td>
                            <td>{{  interestPartyDto.address }}</td>
                            <td>{{  interestPartyDto.nameCourse }}</td>
                            <td>
                                <!-- nếu đã được tíck render ra -->
                                <input type = "checkbox" v-if = "interestPartyDto.isSaled" checked disabled>
                                <!--  -->
                                <input type="checkbox" v-else v-model = "selectedCheckbox" @change="tickSale(interestPartyDto.interestPartyId)">
                            </td>
                            <td>
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                Gửi mail
                                </button>

                                <!-- Modal -->
                                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel" style="color: rgb(68, 68, 68);">Soạn email</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group row">
                                            <label for="titleId" class="col-sm-3 col-form-label" style="color: rgb(68, 68, 68);">Chọn template:</label>
                                            <div class="col-sm-9">
                                                <select class="form-select form-select-lg" aria-label="Default select example" v-model = "templateMailId">
                                                    <option :value="null">Chọn template</option>
                                                    <option  v-for = "(emailResponse, index) in emailResponses" :key="index" :value = "emailResponse.mailTemplateId">{{ emailResponse.nameTemplate }}</option>
                                                </select>

                                                <div class = "mx-auto" style="margin-bottom: 15px; margin-top: 15px">
                                                    <button class="btn btn-primary" @click = "getEmailWithId(templateMailId)">Chọn</button>
                                                </div>
                                            </div> 
                                        </div>
                        
                                        <div v-if = "templateMailId !== null">
                                            <div class="form-group row">
                                                <label for="titleId" class="col-sm-3 col-form-label" style="color: rgb(68, 68, 68);">Tiêu đề email:</label>
                                                <div class="col-sm-9">
                                                    <input type="text" class="form-control" id="titleId" v-model="emailFromId.subject">
                                                </div>
                                            </div> <br> 

                                            <div class="form-group row">
                                                <label for="contentId" class="col-sm-3 col-form-label" style="color: rgb(68, 68, 68);">Nội dung email:</label>
                                                <div class="col-sm-9">
                                                    <editor
                                                    api-key="r6n0cmzqcjxw2ygq468ebfbqvb85htng9pdy9z9q62auetuv"
                                                    v-model = "emailFromId.content"
                                                    :init="{
                                                        height: 500,
                                                        menubar: false,
                                                        plugins: [
                                                        'advlist autolink lists link image charmap print preview anchor',
                                                        'searchreplace visualblocks code fullscreen',
                                                        'insertdatetime media table paste code help wordcount'
                                                        ],
                                                        toolbar:
                                                        'undo redo | formatselect | bold italic backcolor | \
                                                        alignleft aligncenter alignright alignjustify | \
                                                        bullist numlist outdent indent | removeformat | help',
                                                    }"
                                                    />
                                            </div>
                                        </div>

                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                        <!-- ###################Còn phần gửi mail -->
                                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="sendMail(interestPartyDto.email)">Gửi Email</button>
                                    </div>
                                    </div>
                                </div>
                                </div>  
                            </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </main>

</template>
<script setup lang="ts">
import HomeHeader from '@/components/HomeHeader.vue';
import HomeSidebar from '@/components/HomeSidebar.vue';
import Editor from '@tinymce/tinymce-vue'
import axios from 'axios';
import { computed, onMounted, ref } from 'vue';
import { useStore } from 'vuex';

const selectedCourseId: number = -1 

// Get course
interface CourseResponse {
   courseId: number, 
   nameCourse: string, 
   createdAt: string, 
   numClass: number, 
   isClosed: boolean, 
   url: string, 
   description: string
}

const courseResponses = ref<CourseResponse[]>([]); 

const store = useStore();
const jwtToken = computed(() => store.getters.getAccessToken)

const getAllCourse = async() => {
   try {
      const response = axios.get<CourseResponse[]>("http://localhost:8080/course-management/get-all-courses", {
        headers: {
            'Authorization': `Bearer ${jwtToken.value}`
            }
      }); 
      courseResponses.value = (await response).data;
      console.log("Get all courses successfull!")
   } catch(error) {
      console.log("Encountered error while get course: ", error)
   }

}

// Get interestPartyDto with course
interface InterestPartyDto {
    interestPartyId: number,
    name: string, 
    phone: string, 
    address: string, 
    email: string, 
    courseId: number, 
    nameCourse: string, 
    isSaled: boolean
}

const interestPartyDtos = ref<InterestPartyDto[]>([]);

const getInterestPartyDtos = async (courseId: number) => {
    try {
        const response = axios.get<InterestPartyDto[]>("http://localhost:8080/course-management/admin/get-interest-part-dtos/" + courseId);
        interestPartyDtos.value = (await response).data;

        console.log("Lấy danh sách quan tâm thành công!")
        console.log("Dữ liệu trả về: ", (await response).data)

    } catch(error) {
        console.log("Lỗi lấy danh sách quan tâm", error);
    }
}

// get all template
const emailResponses = ref<EmailResponse[]>([]);

const getAllTemplate = async () => {
    try {
        const response = await axios.get<EmailResponse[]>("http://localhost:8080/course-management/admin/get-all-template", {
            headers: {
            'Authorization': `Bearer ${jwtToken.value}`
            }
        });
        emailResponses.value = response.data;
        console.log("Get all templates successfully", response);
    } catch(error) {
        console.error("Error while getting templates", error);
    }
}

// Get mail with id
interface EmailResponse {
    mailTemplateId: number | null; 
    nameTemplate: string; 
    subject: string;
    content: string;
    createdAt: string
}

const emailFromId = ref<EmailResponse>({
    mailTemplateId: null, 
    nameTemplate: '',
    subject:'',
    content: '',
    createdAt: ''
});

const templateMailId:number = -1;
const getEmailWithId = async (templateMailId : number) => {
    try {
        const response = await axios.get("http://localhost:8080/course-management/admin/get-email-with-id/"+templateMailId , {
            headers: {
            'Authorization': `Bearer ${jwtToken.value}`
            }
        });
        emailFromId.value = response.data;
        console.log("Get template with id successfull", response);
        
    } catch(error) {
        console.error("Error while get template", error);
    }
}

// send mail
const sendMail = async (email: string) => {
    try {
        console.log("email from Id", emailFromId)
        const response = await axios.post<EmailResponse>("http://localhost:8080/course-management/admin/send-mail/" + email, emailFromId.value, {
            headers: {
                'Authorization': `Bearer ${jwtToken.value}`
            }
        });

        console.log("Send mail successfully", response);
    } catch (error) {
        console.log("Send mail failure", error);
    }
}

// checked box tự vấn
let selectedCheckbox: boolean = false;

const tickSale = (interestPartyId: number) => {
    try {
        axios.post("http://localhost:8080/course-management/admin/tick-sale/" + interestPartyId);
        console.log("Tick successfully!");
        // gán lại giá trị cho check box bằng false
        selectedCheckbox = false;
    } catch (error) {
        console.log("Action tick is not successful!");
    }
}



onMounted(() => {
   getAllCourse();
   getAllTemplate();
})
</script>

<style>
#ad-course-select {
    width: 500px;
}

hr {
    line-height: 5%
}
</style>