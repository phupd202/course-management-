<template>
        <nav id="main-navbar" class="navbar navbar-expand-lg fixed-top">
            <!-- Container wrapper -->
            <div class="container-fluid d-flex flex-row justify-content-between">
                <!-- Brand -->
                <div class = "d-flex flex-row" style="margin-left: 10px;">
                    <a class="navbar-brand" href="#">
                        <img src = "../assets/newwave_logo.png" alt = "Ảnh logo" height = "50" width = "50">
                    </a>

                    <!-- Search -->
                    <form class="d-none d-md-flex input-group w-auto my-auto">
                        <input autocomplete="off" type="search" class="form-control rounded"
                            placeholder='Nhập từ khoá cần tìm kiếm' style="min-width: 350px; margin-left: 50px;" />
                        <button type = "submit" class = "btn btn-info" style="margin-left: 10px;">Tìm kiếm</button>
                    </form>
                </div>

                <!-- Right links -->
                <ul class="navbar-nav d-flex  flex-row justify-content-start">
                    <li class="nav-item" @click="toggleDropdown">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/Avatars/img (31).webp" class="rounded-circle avatar" height="30" alt="Avatar" loading="lazy" />
                    </li>

                    <!-- dropdown -->
                    <div class="dropdown">
                        <button class="btn dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">

                            <RouterLink to = "/course-manager/update-info" class = "dropdown-item">Cập nhật</RouterLink>
                            <RouterLink  to = "/logout" class = "dropdown-item">Đăng xuất</RouterLink>
                        </div>
                    </div>
                </ul>

            </div>
            <!-- Container wrapper -->
        </nav>
</template>

<script lang="ts">
import { Vue } from 'vue-class-component';
export default class HomeHeader extends Vue { 
    showDropdown: boolean = false;

    toggleDropdown(): void {
        this.showDropdown != this.showDropdown
    }
}
</script>

<style>
#main-navbar {
    background-color: #003E83;
    margin: 0;
    padding: 0;
}

.btn {
    background-color:#003E83;
}

#dropdownMenuButton {
    background-color: #4BAAFE;
}
</style>