<template>
  <PageLayout>
    <template v-slot:content>
      <div class="d-flex justify-content-center">
        <div class="header-calendar d-flex align-content-center justify-content-center">
          <h3>Ngân hàng câu hỏi</h3>
        </div>
      </div>

      <!-- content -->
      <div class="content__boxed">
        <div class="content__wrap">
          <!-- tạo câu hỏi -->
          <div class="create-question d-flex justify-content-end" style="margin-top: 20px; margin-bottom: 20px">
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
              + Tạo câu hỏi
            </button>

            <!-- Form -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
              aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Nhập nội dung câu hỏi</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <div class="mb-3 row">
                      <label for="question" class="form-label fs-6 text" style="color : black">Nội dung câu hỏi:
                      </label>
                      <input type="text" class="form-control" id="question" required>
                    </div>

                    <div class="mb-3 row">
                      <div class="col">
                        <label for="answer1" class="form-label fs-6 text" style="color : black">Đáp án số 1 </label>
                        <input type="text" class="form-control" id="answer1" required>
                      </div>
                      <div class="col">
                        <label for="answer2" class="form-label fs-6 text" style="color : black">Đáp án số 2: </label>
                        <input type="text" class="form-control" id="answer2" required>
                      </div>
                    </div>

                    <div class="mb-3 row">
                      <div class="col">
                        <label for="answer3" class="form-label fs-6 text" style="color : black">Đáp án số 3: </label>
                        <input type="text" class="form-control" id="answer3" required>
                      </div>
                      <div class="col">
                        <label for="answer4" class="form-label fs-6 text" style="color : black">Đáp án số 4: </label>
                        <input type="text" class="form-control" id="answer4" required>
                      </div>
                    </div>

                    <div class="mb-3 row">
                      <label for="correct-answer" class="form-label fs-6 text" style="color : black">Đáp án đúng:
                      </label>
                      <input type="text" class="form-control" id="correct-answer" required>
                    </div>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="button" class="btn btn-primary">Tạo câu hỏi</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- End tạo câu hỏi -->

          <table class="table table-light">
            <thead>
              <tr>
                <th scope="col">STT</th>
                <th scope="col">Nội dung câu hỏi</th>
                <th scope="col">Đáp án 1</th>
                <th scope="col">Đáp án 2</th>
                <th scope="col">Đáp án 3</th>
                <th scope="col">Đáp án 4</th>
                <th scope="col">Câu trả lời đúng</th>
                <th scope="col">Đề xuất bởi</th>
                <th scope="col">Hành động</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(question, index) in questions" :key = "index">
                <th scope="row"> {{ index + 1 }}</th>
                <td>{{question.nameQuestion}}</td>
                <td> {{ question.firstAnswer }}</td>
                <td>{{ question.secondAnswer }}</td>
                <td>{{question.thirdAnswer}}</td>
                <td>{{ question.fourthAnswer }}</td>
                <td>{{ question.correctAnswer }}</td>
                <td>{{ question.nameLecturer }}</td>
                <td>
                  <button class="btn btn-warning" v-if="question.lecturerId === lecturerId">Sửa</button>
                  <button class="btn btn-warning " v-else disabled>Sửa</button>

                  <button class="btn btn-danger" v-if="question.lecturerId === lecturerId" style="margin-left: 10px">Xoá</button>
                  <button class="btn btn-danger disable" v-else style="margin-left: 10px" disabled>Xoá</button>

                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <!-- end: content -->
    </template>
  </PageLayout>
</template>
<script setup lang="ts">
import PageLayout from '@/layout/PageLayout.vue';
import store from '@/store/store';
import { computed, onMounted, ref } from 'vue';
import {getLecturerInfo, getQuestion } from './ListQuestionView';
import { useRoute } from 'vue-router';
import { Question } from '@/interface/lecturer/Question';

const jwtToken = computed(() => store.getters.getAccessToken)
const route = useRoute();
const subjectId = Number(route.params.subjectId);
const lecturerId = ref<Number>();
lecturerId.value = Number(getLecturerInfo(jwtToken.value))

const questions = ref<Question[]>([]);

onMounted( async() => {
  try {
    const fetchedQuestion = await getQuestion(jwtToken.value, subjectId)
    questions.value = fetchedQuestion
  } catch {
    console.log("have a error")
  }
})
</script>
<style>

</style>