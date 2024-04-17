<template>
<nav id="sidebarMenu" class="d-lg-block sidebar bg-white">
    <div class="list-group list-group-flush mx-3 mt-4">
      <div class="menu-group">
        <router-link  v-for="(menu, index) in props.menus" :key = "index" :to = "menu.path" class="list-group-item list-group-item-action rounded-3 py-3">
          <i class="fas fa-tachometer-alt fa-fw me-2"></i><span>{{ menu.nameMenu }}</span>
        </router-link>
      </div>
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

.menu-group {
    display: flex;
    flex-direction: column;
    align-items: center; /* Canh chỉnh các item theo chiều dọc */
}

.menu-group router-link {
    flex: 1; /* Mở rộng các router-link để chia đều không gian */
    text-align: center; /* Canh giữa nội dung */
}

.menu-group router-link .list-group-item {
    display: flex;
    align-items: center;
}

.menu-group router-link .list-group-item span {
    flex: 1; /* Mở rộng nội dung span để chia đều không gian */
    text-align: left; /* Canh giữa nội dung span theo chiều ngang */
}
</style>