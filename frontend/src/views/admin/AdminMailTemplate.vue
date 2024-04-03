<template>
    <header>
        <HomeSidebar></HomeSidebar>
        <HomeHeader></HomeHeader>
    </header>

    <main style="margin-top: 58px; margin-left: 60px;">
        <div class="container pt-4">
            <h3>Quản lý Mail Template</h3>
            <div class="add-template">
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                    Tạo template mới
                </button>

                <!-- Modal -->
                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel" style="color: rgb(68, 68, 68);">Thêm Tempalte Mới</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group row">
                                    <label for="nameTemplateId" class="col-sm-3 col-form-label"
                                        style="color: rgb(68, 68, 68);">Tên template</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="nameTemplateId" v-model="email.nameTemplate">
                                    </div>
                                </div> <br>

                                <div class="form-group row">
                                    <label for="subjectId" class="col-sm-3 col-form-label"
                                        style="color: rgb(68, 68, 68);">Tiêu đề email:</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="subjectId" v-model="email.subject">
                                    </div>
                                </div> <br>

                                <div class="form-group row">
                                    <label for="contentId" class="col-sm-3 col-form-label"
                                        style="color: rgb(68, 68, 68);">Nội dung email:</label>
                                    <div class="col-sm-9">
                                        <editor api-key="r6n0cmzqcjxw2ygq468ebfbqvb85htng9pdy9z9q62auetuv" v-model = "email.content" init="{
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
                                                    bullist numlist outdent indent | removeformat | help'
                                        }" />
                                    </div>
                                </div>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                <button type="button" class="btn btn-primary" @click="addTemplateMail">Thêm</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Bảng danh sách -->
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Tên template</th>
                        <th scope="col">Ngày tạo</th>
                        <th scope="col">Action</th>
                        <th scope="col">Chi tiêt</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(emailResponse, index) in emailResponses" :key="index">
                        <th scope="row">{{ index + 1 }}</th>
                        <td>{{ emailResponse.nameTemplate }}</td>
                        <td>{{ emailResponse.createdAt }}</td>
                        <td>
                            <button class = "btn btn-primary" @click="deletedTemplate(emailResponse.mailTemplateId)">
                                Xoá
                            </button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
                                Chi tiết
                            </button>

                            <!-- Modal -->
                            <div class="modal fade" :id="'exampleModal' + index" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel">Chi tiết email</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group row">
                                                <label for="titleId" class="col-sm-3 col-form-label" style="color: rgb(68, 68, 68);">
                                                    {{ emailResponse.subject }}
                                                </label>
                                                <div class="col-sm-9">
                                                    <input type="text" class="form-control" id="titleId" :placeholder="emailResponse.subject">
                                                </div>
                                            </div> <br>

                                            <div class="form-group row">
                                                <label for="contentId" class="col-sm-3 col-form-label" style="color: rgb(68, 68, 68);">
                                                    Nội dung email:
                                                </label>
                                                <div class="col-sm-9">
                                                    <!-- Render nội dung email vào text editor -->
                                                    <editor :api-key="'r6n0cmzqcjxw2ygq468ebfbqvb85htng9pdy9z9q62auetuv'" :init="{
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
                                                        content: '{{ emailResponse.content }}'
                                                    }"></editor>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                                            <button type="button" class="btn btn-primary" data-dismiss="modal" @click = "saveChangeTemplate(emailResponse)">Lưu thay đổi</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </main>
</template>

<script setup lang="ts">
import HomeHeader from '@/components/HomeHeader.vue';
import HomeSidebar from '@/components/HomeSidebar.vue';
import axios from 'axios';
import Editor from '@tinymce/tinymce-vue'
import { computed, onMounted, ref } from 'vue';
import { useStore } from 'vuex';

interface Email {
    nameTemplate: string, 
    subject: string, 
    content: string
}

const email = ref<Email>({
    nameTemplate: '', 
    subject: '',
    content: ''
});

const store = useStore();
const jwtToken = computed(() => store.getters.getAccessToken)
const addTemplateMail = () => {
    try {
        const response = axios.post("http://localhost:8080/course-management/admin/add-template-mail", email.value, {
            headers: {
            'Authorization': `Bearer ${jwtToken.value}`
            }
        });
        console.log("Add template mail successfully!", response);
    } catch(error) {
        console.log("Having error while add template mail", error);
    }
}

////// Get email 
interface EmailResponse {
    mailTemplateId: number; 
    nameTemplate: string; 
    subject: string;
    content: string;
    createdAt: string
}

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

onMounted(() => {
    getAllTemplate();
});

// save email
const saveChangeTemplate = async (emailResponse : EmailResponse) => {
    try {
        const response = await axios.put("http://localhost:8080/course-management/admin/save-change-template/", emailResponse, {
            headers: {
            'Authorization': `Bearer ${jwtToken.value}`
            }
        });
        console.log("Updated successfully", response);
        getAllTemplate();
    } catch(error) {
        console.error("Error while updating", error);
    }
}

// delete mail 
const deletedTemplate = async (mailTemplateId: number) => {
    try {
        const response = axios.delete("http://localhost:8080/course-management/admin/delete-template/" + mailTemplateId);
        console.log("Delete successfull!", response);
    } catch(error) {
        console.log("Delete template failure!");
    }
}
</script>