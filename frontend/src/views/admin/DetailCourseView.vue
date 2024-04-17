<template>
    <PageLayout>
        <template v-slot:content>
            <h3> Chi tiết khoá học</h3>
            <!-- Start: Content tab -->
            <div class="row">
                <div class="col-10">
                    <table class="table" v-if="subjectResponses != null">
                        <thead>
                            <tr>xam
                                <th scope="col">#</th>
                                <th scope="col">Tên môn học</th>
                                <th scope="col">Nội dung học</th>
                                <th scope="col">Thời gian học</th>
                                <th scope="col">Action</th>
                                <th scope="col">Sửa</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(subject, index) in subjectResponses" v-bind:key=index>
                                <th scope="row">{{ index + 1 }}</th>
                                <td>{{ subject.nameSubject }}</td>
                                <td>{{ subject.content }}</td>
                                <td>{{ subject.estimate }} tháng</td>
                                <td>
                                    <button v-if="!subject.isDeleted" class="delete-btn" id="deleteBtnId"
                                        @click="actionWithSubject(true, subject.subjectId)"></button>
                                    <button v-else class="open-btn" id="openBtnId"
                                        @click="actionWithSubject(false, subject.subjectId)"></button>
                                </td>
                                <td>
                                    <button class="edit-btn"></button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="col-2">
                    <button id="classroom-btn" type="button" class="btn btn-primary" data-bs-toggle="modal"
                        data-bs-target="#exampleModal" @click="getAllMajor">
                        Thêm môn học +
                    </button>

                    <!-- Modal -->
                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                        aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Thêm môn học</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group row">
                                        <label for="subjectId" class="col-sm-3 col-form-label">Tên môn học: </label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control" id="subjectId"
                                                v-model="subjectRequest.nameSubject">
                                        </div>
                                    </div> <br>

                                    <div class="form-group row">
                                        <label for="estimateId" class="col-sm-3 col-form-label">Thời gian học
                                            (tháng):</label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control" id="estimateId"
                                                v-model="subjectRequest.estimate"> <br>
                                        </div>
                                    </div> <br>

                                    <div class="form-group row">
                                        <label for="labelId" class="col-sm-3 col-form-label">Chuyên môn: </label>
                                        <div class="col-sm-9">
                                            <select class="form-control" name="label" id="labelId"
                                                v-model="subjectRequest.label">
                                                <option value="Chưa chọn chuyên môn" disabled selected>Chọn chuyên môn
                                                </option>
                                                <option v-for="(major, index) in majors" :key="index" :value="major">{{
                                                    major }}</option>
                                            </select>
                                        </div>
                                    </div> <br>

                                    <div class="form-group row">
                                        <label for="contentId" class="col-sm-3 col-form-label">Nội dung môn học:</label>
                                        <div class="col-sm-9">
                                            <textarea class="form-control" rows="5" id="contentId"
                                                v-model="subjectRequest.content"></textarea>
                                        </div>
                                    </div>
                                </div>

                                <div class="modal-footer">
                                    <!--Catch event add subject  -->
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"
                                        @click="addSubject">Thêm môn</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Thống kế -->
                    <p></p>
                    <div class="static-subject">
                        <h5 v-if="subjectResponses.length > 0">Tổng số môn: {{ subjectResponses[0].sumSubject }} môn
                        </h5>
                        <h5 v-if="subjectResponses.length > 0">Thời gian học dự kiến: {{ subjectResponses[0].sumEstimate
                            }} tháng</h5>
                    </div>
                </div>
            </div>
        </template>
    </PageLayout>
</template>

<script setup lang="ts">
import PageLayout from '@/layout/PageLayout.vue';
import axios from 'axios';
import { computed, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { useStore } from 'vuex';

const store = useStore();
const jwtToken = computed(() => store.getters.getAccessToken)

interface SubjectResponse {
    subjectId: number | null;
    nameSubject: string | null;
    estimate: number;
    content: string | null;
    sumEstimate: number;
    sumSubject: number;
    isDeleted: boolean;
}

const subjectResponses = ref<SubjectResponse[]>([]);

const getSubjectsByCourseId = async (courseId: number) => {
    try {
        const response = await axios.get<SubjectResponse[]>("http://localhost:8080/course-management/admin/detail-course/" + courseId, {
            headers: {
                'Authorization': `Bearer ${jwtToken.value}`
            }
        });
        subjectResponses.value = response.data;
        console.log(courseId)
        console.log("Lấy danh sách môn học thành công");
    } catch (error) {
        console.log(courseId)
        console.log("Lấy danh sách môn học thất bại");
    }
};

const router = useRoute();

onMounted(() => {
    const courseId = Number(router.params.courseId);
    getSubjectsByCourseId(courseId);
});

// ------------------Add subject--------------
interface SubjectRequest {
    nameSubject: string;
    estimate: number;
    content: string;
    label: string;
}

const subjectRequest = ref<SubjectRequest>({
    nameSubject: '',
    estimate: 0,
    content: '',
    label: ''
});

// send post to add subject
const addSubject = () => {
    console.log("Begin send post to add subject")
    const courseId = Number(router.params.courseId);
    // post to add subject
    axios.post<SubjectRequest>("http://localhost:8080/course-management/admin/add-subject/" + courseId, subjectRequest.value, {
        headers: {
            'Authorization': `Bearer ${jwtToken.value}`
        }
    })
        .then(response => {
            console.log("Add subject successfull!!, subjectRequest = ", subjectRequest, response)
            console.log("Selected label: ", subjectRequest.value.label)
            getSubjectsByCourseId(courseId);
        })
        .catch(error => {
            console.log("Add subject failure!!,  subjectRequest = ", subjectRequest, error)
        })
}

// Label
const majors = ref<string[]>([])
const getAllMajor = async () => {
    try {
        const response = axios.get<string[]>("http://localhost:8080/course-management/admin/get-all-majors", {
            headers: {
                'Authorization': `Bearer ${jwtToken.value}`
            }
        });
        majors.value = (await response).data
        console.log("Get major successful!, data: ", majors.value);
    } catch (error) {
        console.log("Error while get major list ", error)
    }
}

// Delete
const actionWithSubject = async (isDeleted: boolean, subjectId: any) => {
    try {
        const response = await axios.post("http://localhost:8080/course-management/admin/delete", null, {
            params: {
                subjectId: subjectId,
                isDeleted: isDeleted
            },
            headers: {
                'Authorization': `Bearer ${jwtToken.value}`
            }
        });
        console.log("Action successful!", response);
    } catch (error) {
        console.log("Action failure!", error);
    }
}

</script>

<style>
#class-btn {
    width: 800px;
    height: 40px;
}

#classroom-btn {
    width: 150px;
    height: 40px;
}

.table {
    border-collapse: collapse;
    width: 100%;
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

.delete-btn:hover {
    background-color: #ffca28;
}

.delete-btn {
    background-image: url('/src/assets/delete.png');
    background-size: cover;
    width: 40px;
    height: 40px;
    background-color: #dc3545;
    border: none;
    cursor: pointer;
    border-radius: 5px;
    transition: background-color 0.3s ease;
}

.delete-btn:hover {
    background-color: #c82333;
}

.open-btn {
    background-image: url('/src/assets/open.png');
    background-size: cover;
    width: 40px;
    height: 40px;
    background-color: #28a745;
    border: none;
    cursor: pointer;
    border-radius: 5px;
    transition: background-color 0.3s ease;
}

.open-btn:hover {
    background-color: #218838;
    ;
}
</style>