<template>
  <nav id="sidebarMenu" class="d-lg-block sidebar bg-white">
    <div class="list-group list-group-flush mx-3 mt-4">
      <div>
        <a v-if="isUser" href="#" class="list-group-item list-group-item-action rounded-3 py-3">
          <i class="fas fa-tachometer-alt fa-fw me-2"></i><span>Trang chủ</span>
        </a>
        
        <router-link v-if = "isAdmin" to="/course-management/admin/courses" class="list-group-item list-group-item-action rounded-3 py-3">
          <i class="fas fa-tachometer-alt fa-fw me-2"></i><span>Quản lý khoá học</span>
        </router-link>

        <router-link  v-if ="isLecturer" to="/course-management/lecturer/info" class="list-group-item list-group-item-action rounded-3 py-3">
          <i class="fas fa-tachometer-alt fa-fw me-2"></i><span>Thông tin cá nhân</span>
        </router-link>
      </div>

      <div>
        <a v-if="isUser" href="#" class="list-group-item list-group-item-action rounded-3 py-3">
          <i class="fas fa-chart-area fa-fw me-2"></i><span>Quản lý khoá học</span>
        </a>

        <router-link v-if="isAdmin" to="/course-management/admin/management-class" class="list-group-item list-group-item-action rounded-3 py-3">
          <i class="fas fa-tachometer-alt fa-fw me-2"></i><span>Quản lý lớp học</span>
        </router-link>

        <router-link  v-if ="isLecturer" to="/course-management/lecturer/score" class="list-group-item list-group-item-action rounded-3 py-3">
          <i class="fas fa-tachometer-alt fa-fw me-2"></i><span>Quản lý điểm</span>
        </router-link>
      </div>
      
      <div>
        <a v-if="isUser" href="#" class="list-group-item list-group-item-action rounded-3 py-3">
          <i class="fas fa-lock fa-fw me-2"></i><span>Điểm thi</span>
        </a>

        <router-link v-if = "isLecturer" to="/course-management/lecturer/calendar" class="list-group-item list-group-item-action rounded-3 py-3">
          <i class="fas fa-tachometer-alt fa-fw me-2"></i><span>Quản lý lịch dạy</span>
        </router-link>

      </div>

      <div>
        <router-link v-if="isAdmin" to="/course-management/admin/interest-party" class="list-group-item list-group-item-action rounded-3 py-3">
          <i class="fas fa-lock fa-fw me-2"></i><span>Lịch chờ tư vấn</span>
        </router-link>
      </div>

      <div>
        <router-link v-if="isAdmin" to="/course-management/admin/mail-template" class="list-group-item list-group-item-action rounded-3 py-3">
          <i class="fas fa-lock fa-fw me-2"></i><span>Template Mail</span>
        </router-link>
      </div>

      <router-view/>
    </div>
  </nav>
</template>


<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

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

<style>
#sidebarMenu{
    max-height: 100vh;
    overflow-y: scroll;
    margin-top: 60px;
    padding: 0;
}
/* Sidebar */
.sidebar {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  padding: 58px 0 0; 
  box-shadow: 0 10px 20px 0 rgb(0 0 0 / 10%), 0 2px 10px 0 rgb(0 0 0 / 5%);
  width: 300px;
  z-index: 1;
  max-height: 100%;
  color: #003E83;
}

a {
    margin-bottom: 5px;
    margin-top: 5px;
}

.sidebar .active {
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgb(0 0 0 / 16%), 0 2px 10px 0 rgb(0 0 0 / 12%);
}

.sidebar-sticky {
  position: relative;
  top: 0;
  height: calc(100vh - 48px);
  padding-top: 0.5rem;
  overflow-x: hidden;
  overflow-y: auto; /* Scrollable contents if viewport is shorter than content. */
}

@media (min-width: 991.98px) {
  main {
    padding-left: 240px;
  }
}

.list-group-item {
    color: #000; 
    text-decoration: none; 
    border-radius: 10px;
}

  /* Màu của liên kết khi được active */
.list-group-item.active {
    background-color:  #003E83;
    color: #fff;
}

.list-group-item {
    color: #000; 
    background-color: #fff; 
    border: none; 
    transition: background-color 0.3s ease; 
}

.list-group-item:hover {
    background-color: #2974C8;
    color: #fff;
}

.list-group-item span {
    font-weight: bold; 
}
</style>