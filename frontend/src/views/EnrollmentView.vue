<template>
    <!-- header -->
    <header>
        <HomeSidebar></HomeSidebar>
        <HomeHeader></HomeHeader>
    </header>

    <!-- Content -->
    <main style="margin-top: 58px; margin-left: 60px;">
        <div class="container pt-4">
            <!-- Tab bar -->
            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab"
                        aria-controls="home" aria-selected="true">Quản lý học phần</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab"
                        aria-controls="profile" aria-selected="false">Tiến trình học tập</a>
                </li>
            </ul>

            <!-- Content -->
            <div class="tab-content" id="myTabContent">
                <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                    <div class="card">
                        <div class = "row">
                            <!-- Input cho Điểm số -->
                            <div class = "col-9">
                                <div class="form-group">
                                    <label for="gradeFilter">Tìm kiếm</label>
                                    <input type="text" class="form-control" id="gradeFilter" placeholder="Nhập điểm, giảng viên...">
                                    <button class = "btn btn-primary" id = "search-button" type = "submit">Tìm kiếm</button>
                                </div>
                            </div>

                            <div class = "col-3">
                                <div class="form-group">
                                    <label for="semesterFilter">Chọn Kỳ:</label>
                                    <select class="form-control" id="semesterFilter">
                                        <option value="">Tất cả</option>
                                        <option value="semester1">Kỳ 1</option>
                                        <option value="semester2">Kỳ 2</option>
                                        <option value="semester2">Kỳ 2</option>
                                        <!-- Thêm các option khác nếu cần -->
                                    </select>
                                </div>
                            </div>
                        </div>

                        <!-- Body -->
                        <div class="card-body">
                           <TableScore></TableScore>
                        </div>
                    </div>
                </div>
                <!-- Tab 2 -->
                <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                    Cập nhật

                </div>
            </div>
        </div>
    </main>
</template>

<script lang="ts">
import HomeHeader from '@/components/HomeHeader.vue';
import HomeSidebar from '@/components/HomeSidebar.vue';
import { Options, Vue } from 'vue-class-component';

@Options({
    components: {
        HomeSidebar,
        HomeHeader
    }
})

export default class EnrollmentView extends Vue { }

</script>

