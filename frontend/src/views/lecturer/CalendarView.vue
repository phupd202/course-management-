<template>
  <header>
    <HomeHeader></HomeHeader>
    <HomeSidebar></HomeSidebar>
  </header>

  <!-- Content -->
  <main style="margin-top: 70px;">
    <div class="container pt-4">
      <div class = "header-calendar">
        <h3>Lịch dạy giảng viên</h3>
      </div>
      
      <FullCalendar :options='calendarOptions' class="custom-calendar" id = "calendar-lecturer"/>
    </div>
  </main>
</template>

<script setup lang="ts">
import HomeHeader from '@/components/HomeHeader.vue';
import HomeSidebar from '@/components/HomeSidebar.vue';
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid';
import { computed, onMounted, ref } from 'vue';
import axios from 'axios';
import store from '@/store/store';
import {findFirstDayBeginDate, getDatePart, countDayInWeekBetweenDate} from '../../helpers/timehelpers';

// Calendar
const calendarOptions = ref({
  plugins: [dayGridPlugin],
  initialView: 'dayGridWeek',
  weekends: false,
  events: [{
    title: 'Dạy lớp Java 1',
    // start: new Date(2024, 3, 29, 7, 30, 0),
    start: '27/3/2024 18:00:00',
    // end: new Date(2024, 3, 29, 9, 30, 0)
    end: '27/3/2024 18:00:00'
  }],
  backgroundColor: 'blue',

  headerToolbar: {
    start: 'prevYear,nextYear prev,next today',
    center: 'title',
    end: 'dayGridMonth,timeGridWeek,timeGridDay'
  }
});

// Get all assignment
interface AssignmentLecturer {
  classroomId: number,
  beginDate: string,
  endDate: string,
  beginTime: string,
  endTime: string,
  codeClassroom: string,
  nameSubject: string,
  dayOfWeek: number,
  subjectId: number
}

const jwtToken = computed(() => store.getters.getAccessToken)
const assignmentLecturers = ref<AssignmentLecturer[]>([])

const getAssignmentLecturer = async () => {
  try {
    const response = await axios.get<AssignmentLecturer[]>("http://localhost:8080/course-management/lecturer/get-all-assignment", {
      headers: {
        'Authorization': `Bearer ${jwtToken.value}`
      }
    });
    assignmentLecturers.value = (await response).data;
    console.log("Get assignment successfull!")
    console.log("Dữ liệu nhận được, " + assignmentLecturers.value)

    createCalendar(assignmentLecturers.value);
  } catch (error) {
    console.log("error in while get assignment!", error)
  }
}

const createCalendar = (assignmentLecturers : AssignmentLecturer[]) => {
    assignmentLecturers.forEach((assignmentLecturer: AssignmentLecturer) => {
      const beginDate = new Date(assignmentLecturer.beginDate);
      const endDate = new Date(assignmentLecturer.endDate);

      const dayOfWeek = assignmentLecturer.dayOfWeek - 1;

      const teachingStartWeek = findFirstDayBeginDate(beginDate, dayOfWeek);

      console.log("Chưa chạy vòng for")
      const numDayOfWeek = countDayInWeekBetweenDate(beginDate, endDate, assignmentLecturer.dayOfWeek)

      for (let i = 0; i < numDayOfWeek + 4; i++) {
        console.log("Đã vào vòng for")
          const event = {
            title: "Môn: " + assignmentLecturer.nameSubject + "-" + assignmentLecturer.codeClassroom,
            start: getDatePart(teachingStartWeek.toLocaleDateString("en-CA")) + " " + assignmentLecturer.beginTime, 
            end: getDatePart(teachingStartWeek.toLocaleDateString("en-CA")) + " " + assignmentLecturer.endTime
          }

          console.log("event: ", event)
          calendarOptions.value.events.push(event)

          // update date
          teachingStartWeek.setDate(teachingStartWeek.getDate() + 7)
      }

      console.log("assignmentLecturer: ", assignmentLecturer)
      }
    );
  }
onMounted(async () => {
  await getAssignmentLecturer();
})

// ---------Thêm lịch ----------
</script>

<style>
#calendar-lecturer {
  background-color: pink;
  color: black;
}

#header-calendar {
    background-color: #007bff;
    color: #ffffff;
    padding: 10px;
    border-radius: 5px;
    margin-bottom: 20px;
}


.fc-event {
    background-color: #28a745; /* Màu xanh lá cây */
    color: #ffffff; /* Màu chữ trắng */
    border: none; /* Không có đường viền */
}

.custom-calendar {
    background-color: #f8f9fa; /* Màu nền */
    color: #343a40; /* Màu chữ chính */
    border-radius: 5px; /* Bo tròn viền */
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Đổ bóng */
}

.header-calendar {
    background-color: #007bff; /* Màu nền của tiêu đề */
    color: #ffffff; /* Màu chữ của tiêu đề */
    padding: 10px; /* Khoảng cách giữa các thành phần */
    border-radius: 5px; /* Bo tròn viền */
    margin-bottom: 20px; /* Khoảng cách với phần nội dung */
}

</style>
