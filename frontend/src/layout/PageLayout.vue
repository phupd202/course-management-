<template>
    <header>
        <HomeHeader></HomeHeader>
    </header>

    <main>

        <div class="row">
            <div class="col-2">
                <HomeSidebar v-if="isUser" :menus="userMenu"></HomeSidebar>
                <HomeSidebar v-else-if="isAdmin" :menus="adminMenu"></HomeSidebar>
                <HomeSidebar v-else-if="isLecturer" :menus="lecturerMenu"></HomeSidebar>
            </div>
            <div class="col-10">
                <slot name="content"></slot>
            </div>
        </div>

    </main>
</template>

<script setup lang="ts">
import HomeHeader from '@/components/HomeHeader.vue';
import HomeSidebar from '@/components/HomeSidebar.vue';
import { Menu } from '@/interface/sidebar/Menu';
import { computed } from 'vue';
import { useStore } from 'vuex';

const adminMenu: Menu[] = [
    {
        path: "/course-management/admin/courses",
        nameMenu: "Quản lý khoá học"
    },
    {
        path: "/course-management/admin/management-class",
        nameMenu: "Quản lý lớp học"
    },
    {
        path: "/course-management/admin/interest-party",
        nameMenu: "Lịch chờ tư vấn"
    },
    {
        path: "/course-management/admin/register-queue",
        nameMenu: "Đơn đăng ký"
    },
    {
        path: "/course-management/admin/mail-template",
        nameMenu: "Mail Template"
    }
]

const userMenu: Menu[] = [
    {
        path: "/course-management/user/my-course",
        nameMenu: "Khoá học của tôi"
    },
    {
        path: "/course-management/user/my-score",
        nameMenu: "Xem điểm"
    }
]

const lecturerMenu: Menu[] = [
    {
        path: "/course-management/lecturer/info",
        nameMenu: "Thông tin cá nhân"
    },
    {
        path: "/course-management/lecturer/score",
        nameMenu: "Quản lý điểm"
    },
    {
        path: "/course-management/lecturer/calendar",
        nameMenu: "Quản lý lịch dạy"
    },
]

// roles get from store
const store = useStore()
const rolesComputed = computed(() => store.getters.getRoles);
const roles: string[] = rolesComputed.value;


const isAdmin = computed(() => roles.includes('ADMIN'));
const isUser = computed(() => roles.includes('USER'));
const isLecturer = computed(() => roles.includes('LECTURER'))

</script>