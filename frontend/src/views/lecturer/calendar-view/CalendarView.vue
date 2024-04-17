<template>
  <PageLayout>
    <template v-slot:content>
      <div class = "header-calendar">
        <h3>Lịch dạy giảng viên</h3>
      </div>
      
      <FullCalendar :options='calendarOptions' class="custom-calendar" id = "calendar-lecturer"/>
    </template>
  </PageLayout>
</template>

<script setup lang="ts">
import PageLayout from '@/layout/PageLayout.vue';
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid';
import { computed, onMounted, ref } from 'vue';
import store from '@/store/store';
import { AssignmentLecturer } from '@/interface/lecturer/AssignmentLecturer';
import { getAssignmentLecturer } from '@/service/lecturer/CalendarViewService';
import { countDayInWeekBetweenDate, findClosestDayOfWeek, getDatePart } from '@/helpers/timehelpers';

// token
const jwtToken = computed(() => store.getters.getAccessToken)

// data
const assignmentLecturers = ref<AssignmentLecturer[]>([])

// calendar
const calendarOptions = ref({
  plugins: [dayGridPlugin],
  initialView: 'dayGridWeek',
  weekends: false,
  events: [{
    title: '',
    start: '',
    end: ''
  }],
backgroundColor: 'blue',

headerToolbar: {
  start: 'prevYear,nextYear prev,next today',
  center: 'title',
  end: 'dayGridMonth,timeGridWeek,timeGridDay'
  }
});


const createCalendar = (assignmentLecturers : AssignmentLecturer[]) => {
    assignmentLecturers.forEach((assignmentLecturer: AssignmentLecturer) => {
      const beginDate = new Date(assignmentLecturer.beginDate);
      const endDate = new Date(assignmentLecturer.endDate);

      const dayOfWeek = assignmentLecturer.dayOfWeek;

      // tìm ngày có dayofweek gần nhất
      const teachingStartWeek = findClosestDayOfWeek(beginDate, dayOfWeek);

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

// fetch calendar
onMounted(async () => {
    try {
      const fetchedAssignment = await getAssignmentLecturer(jwtToken.value);
      assignmentLecturers.value = fetchedAssignment;
      createCalendar(assignmentLecturers.value);
    } catch(error) {
      console.log("error at fecth assignment Lecturer: ", error)
      throw error;
    }
});

</script>

<style>
@import url(CalendarView.css);
</style>
