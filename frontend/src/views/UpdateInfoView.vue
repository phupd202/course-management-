<template>
    <header>
        <HomeSidebar></HomeSidebar>
        <HomeHeader></HomeHeader>
    </header>

    <main style="margin-top: 58px; margin-left: 100px;">
        <div class="container pt-4">

            <div class="card">
                <div class="card-body">
                    <!-- Tab bar -->
                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab"
                                aria-controls="home" aria-selected="true">Thông tin tài khoản</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab"
                                aria-controls="profile" aria-selected="false">Cập nhật</a>
                        </li>
                    </ul>

                     <!-- Content -->
                    <div class="tab-content" id="myTabContent">
                        <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                            <div >
                                <h6>Email: </h6> 
                                <h6>Số điện thoại: </h6>
                                <h6>Ngày tạo: </h6>
                                <h6>Số học phần đã đăng ký: </h6>
                                <h6>GPA: </h6>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                            Cập nhật

                        </div>
                    </div>
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
        HomeHeader,
        HomeSidebar,
    }
})
export default class UpdateInfoView extends Vue { }
</script>