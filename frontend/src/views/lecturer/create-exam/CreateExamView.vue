<template>
<PageLayout>
        <template v-slot:content>
            <div class="d-flex justify-content-center">
                <div class="header-calendar d-flex align-content-center justify-content-center">
                    <h3>Tạo đề ôn tập</h3>
                </div>
            </div>
            <div class="content__boxed">
                <div class="content__wrap">
                    <div class = "create-exam">
                        <div class = "intro-exam">
                            <label for="exam-name" class="form-label fs-6 text" style="color: black"><h3>Tên đề ôn tập</h3></label>
                            <input type="text" class="form-control" id="exam-name" required >
                        </div>

                        <button type = "button" class = "btn btn-primary" style="margin-top: 10px">Tạo đề ôn tập</button>
                    </div>

                    <hr>
                    <div class = "create-question" style="margin-top: 20px; margin-bottom: 20px">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                            Tạo câu hỏi
                        </button>

                        <!-- Form -->
                        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Nhập nội dung câu hỏi</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="mb-3 row">
                                        <label for="question" class="form-label fs-6 text" style ="color : black">Nội dung câu hỏi: </label>
                                        <input type="text" class="form-control" id="question" required >
                                    </div>

                                    <div class="mb-3 row">
                                        <div class="col">
                                            <label for="answer1" class="form-label fs-6 text" style ="color : black">Đáp án số 1 </label>
                                            <input type="text" class="form-control" id="answer1" required >
                                        </div>
                                        <div class="col">
                                            <label for="answer2" class="form-label fs-6 text" style ="color : black">Đáp án số 2: </label>
                                            <input type="text" class="form-control" id="answer2" required >
                                        </div>
                                    </div>

                                    <div class="mb-3 row">
                                        <div class="col">
                                            <label for="answer3" class="form-label fs-6 text" style ="color : black">Đáp án số 3: </label>
                                            <input type="text" class="form-control" id="answer3" required >
                                        </div>
                                        <div class="col">
                                            <label for="answer4" class="form-label fs-6 text" style ="color : black">Đáp án số 4: </label>
                                            <input type="text" class="form-control" id="answer4" required >
                                        </div>
                                    </div>

                                    <div class="mb-3 row">
                                        <label for="correct-answer" class="form-label fs-6 text" style ="color : black">Đáp án đúng: </label>
                                        <input type="text" class="form-control" id="correct-answer" required >
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
                    <hr>
                    <div class = "list-question">
                        <h3>Danh sách câu hỏi</h3>

                    </div>
                
                </div>
            </div>
        </template>
    </PageLayout>
</template>
<script setup lang="ts">
import PageLayout from '@/layout/PageLayout.vue';
</script>