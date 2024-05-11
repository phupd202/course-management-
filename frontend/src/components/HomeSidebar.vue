<!-- <template>
<nav id="sidebarMenu" class="d-lg-block sidebar bg-white">
    
</nav>
</template> -->
<template>
  <nav id="mainnav-container" class="mainnav d-lg-block" style = "height: 100vh;">
    <div class="mainnav__inner">
      <!-- Navigation menu -->
      <div class="mainnav__top-content scrollable-content pb-5">

        <!-- Profile Widget -->
        <div class="mainnav__profile mt-3 d-flex3">

          <div class="mt-2 d-mn-max"></div>

          <!-- Profile picture  -->
          <div class="mininav-toggle text-center">
            <img class="mainnav__avatar img-md rounded-circle border" src="../../public/img/profile-photos/1.png"
              alt="Profile Picture">
          </div>

          <div class="mininav-content collapse d-mn-max">
            <div class="d-grid">

              <!-- User name and position -->
              <button class="d-block btn shadow-none p-2" data-bs-toggle="collapse" data-bs-target="#usernav"
                aria-expanded="false" aria-controls="usernav">
                <span class="dropdown-toggle d-flex justify-content-center align-items-center">
                  <h6 class="mb-0 me-3">Tên người dùng</h6>
                </span>
              </button>

              <!-- Collapsed user menu -->
              <div id="usernav" class="nav flex-column collapse">
                <a href="#" class="nav-link">
                  <i class="demo-pli-unlock fs-5 me-2"></i>
                  <span class="ms-1">Đăng xuất</span>
                </a>
              </div>

            </div>
          </div>
        </div>
        <!-- End - Profile widget -->
        <!-- Components Category -->
        <div class="mainnav__categoriy py-3">
          <ul class="mainnav__menu nav flex-column">

            
              <router-link v-for="(menu, index) in props.menus" :key="index" :to="menu.path"
                class="mininav-toggle nav-link collapsed">
                <i class="fas fa-tachometer-alt fa-fw me-2"></i><span>{{ menu.nameMenu }}</span>
              </router-link>
           
          </ul>
        </div>
        <!-- END : Components Category -->
      </div>
      <!-- End - Navigation menu -->

      <!-- Logout -->
      <div class="mainnav__bottom-content border-top pb-2">
        <ul id="mainnav" class="mainnav__menu nav flex-column">
          <li class="nav-item has-sub">
            <a href="#" class="nav-link mininav-toggle collapsed" aria-expanded="false">
              <i class="demo-pli-unlock fs-5 me-2"></i>
              <span class="nav-label ms-1">Logout</span>
            </a>
          </li>
        </ul>
      </div>
      <!-- End - Logout -->

    </div>
  </nav>
</template>

<script setup lang="ts">
import { Menu } from '@/interface/sidebar/Menu';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { defineProps } from 'vue';

const props = defineProps<{
  menus: Menu[]
}>();

const isUser = ref<boolean>(false);
const isAdmin = ref<boolean>(false);
const isLecturer = ref<boolean>(false);

onMounted(() => {
  checkUserRole();
});

function checkUserRole(): void {
  const route = useRoute();
  const path = route.path;
  if (path.startsWith("/course-management/user")) {
    isUser.value = true;
  }

  if (path.startsWith("/course-management/admin")) {
    isAdmin.value = true;
  }

  if (path.startsWith("/course-management/lecturer")) {
    isLecturer.value = true;
  }
}
</script>