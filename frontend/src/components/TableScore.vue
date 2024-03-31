<template>
    <table class="table">
        <thead>
            <tr>
                <th scope="col">STT</th>
                <th scope="col">
                    Tên học phần
                </th>
                <th scope="col">Mã lớp</th>
                <th scope="col">Điểm thi</th>
                <th scope="col"></th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <th scope="row">3</th>
                <td>Tính toán phân tán</td>
                <td>MAT-2345</td>
                <td>B+</td>
                <td>
                    
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
                        Chi tiết
                    </button>

                    <!-- Modal -->
                    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Thông tin môn học</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class = "row">


                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                        </div>
                        </div>
                    </div>
                    </div>
                
                </td>
            </tr>
            <tr>
                <th scope="row">4</th>
                <td>Thị giác máy tính</td>
                <td>MAT-2348</td>
                <td>D+</td>
                <td>
                    
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
                        Chi tiết
                    </button>

                    <!-- Modal -->
                    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Thông tin môn học</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class = "row">
                                <h3>Lịch học: </h3>
                                <h3>Phòng học</h3>
                                <h3>Giảng viên</h3>
                                <h3></h3>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                        </div>
                        </div>
                    </div>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<script lang = "ts">
import { Vue } from 'vue-class-component';
export default class TableScore extends Vue {}

</script>

<style>
#search-button {
    margin-top: 15px;
}

#gradeFilter {
    margin-left: 20px;
}

#semesterFilter {
    margin-right: 50px;
}


</style>