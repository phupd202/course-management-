<template>
    <header>
      <HomeSidebar></HomeSidebar>
      <HomeHeader></HomeHeader>
    </header>
  
    <main style="margin-top: 58px;">
      <div class="container pt-4">
        <h3>Danh sách các khoá học của bạn</h3> <br>
        <!-- Bootstrap  -->
        <div class="accordion" id="accordionPanelsStayOpenExample">
            <div class="accordion-item" v-for="(classroom, index) in myCourse" :key="index">
                <h2 class="accordion-header" id="panelsStayOpen-heading{{ index }}">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" :data-bs-target="'#panelsStayOpen-collapse' + index" aria-expanded="false" :aria-controls="'panelsStayOpen-collapse' + index" @click="fetchSubject(classroom.courseId)">
                        <h4 class = "text-my-course">{{ classroom.nameCourse }} - Thời gian học: {{ formatDateTime(classroom.beginDate) }} - {{ formatDateTime(classroom.endDate) }}</h4>
                    </button>
                </h2>
                <div :id="'panelsStayOpen-collapse' + index" class="accordion-collapse collapse" :aria-labelledby="'panelsStayOpen-heading' + index">
                    <div class="accordion" id="accordionPanel{{ index }}">
                        <!-- Accord 2 -->
                        <div class="accordion-item" v-for="(subject, subIndex) in subjects" :key="subIndex">
                            <h2 class="accordion-header" :id="'panelsStayOpen-subheading' + subIndex">
                                <button class="accordion-button" type="button" data-bs-toggle="collapse" :data-bs-target="'#panelsStayOpen-subcollapse' + subIndex" aria-expanded="true" :aria-controls="'panelsStayOpen-subcollapse' + subIndex">
                                    {{ subject.nameSubject }}
                                </button>
                            </h2>
                            <div :id="'panelsStayOpen-subcollapse' + subIndex" class="accordion-collapse collapse show" :aria-labelledby="'panelsStayOpen-subheading' + subIndex">
                                <div class="accordion-body">
                                    <p>Nội dung học: {{ subject.content }}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
      </div>
    </main>
  </template>
  
<script setup lang="ts">
import HomeHeader from '@/components/HomeHeader.vue';
import HomeSidebar from '@/components/HomeSidebar.vue';
import { ClassroomOfCourse } from '@/interface/ClassroomOfCourse';
import { computed, onMounted, ref } from 'vue';
import { useStore } from 'vuex';
import { SubjectOfCourse } from '@/interface/SubjectOfCourse';
import { formatDateTime } from '@/helpers/timehelpers'; 
import { getMyCourses, getSubjectOfCourse } from '@/service/user/ClassroomService';
  
const store = useStore();
const jwtToken = computed(() => store.getters.getAccessToken);
  
const myCourse = ref<ClassroomOfCourse[]>([]);
const subjects = ref<SubjectOfCourse[]>([]);
  
const fetchSubject = async (courseId: number) => {
    try {
      const response = await getSubjectOfCourse(jwtToken.value, courseId);
      console.log("Fetched subject of course");
      subjects.value = response;
      console.log("Subject.value: ", subjects.value);
    } catch (error) {
      console.log("Error when getting subjects: ", error);
    }
};

onMounted(async () => {
    try {
      myCourse.value = await getMyCourses(jwtToken.value);
      console.log("JWT token: ", jwtToken.value);
    } catch (error) {
      console.error("Encountered error while getting courses: ", error);
      console.log("JWT token: ", jwtToken.value);
    }
});
</script>
<style>
@import url(UserMyCourse.css);
</style>