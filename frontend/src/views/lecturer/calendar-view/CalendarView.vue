<template>
  <PageLayout>
    <template v-slot:content>
      <div class="d-flex justify-content-center">
        <div class="header-calendar d-flex align-content-center justify-content-center">
          <h3>Lịch dạy giảng viên</h3>
        </div>
      </div>

      <div class="row">
        <div class="col-2 option-calendar">
          <!-- Thêm sự kiện -->
          <div class="add-event">
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal"
              style="margin-bottom: 5px;" @click="showModal(isVisible)">
              Thêm sự kiện +
            </button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
              aria-hidden="true" v-show="isVisible">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Sự kiện mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <div class="mb-3">
                      <label for="eventName" class="form-label fs-6 text">Tên sự kiện</label>
                      <input type="text" class="form-control" id="eventName" required v-model="newEvent.title">
                    </div>

                    <div class="mb-3 row">
                      <label for="endDate" class="form-label fs-6 text">Ngày diễn ra: </label>
                      <input type="date" class="form-control" id="endDate" required v-model="newEvent.date">
                    </div>

                    <div class="mb-3 row">
                      <div class="col">
                        <label for="startTime" class="form-label fs-6 text">Giờ bắt đầu> </label>
                        <input type="time" class="form-control" id="startDate" required v-model="newEvent.beginTime">
                      </div>
                      <div class="col">
                        <label for="endTime" class="form-label fs-6 text">Giờ kết thúc: </label>
                        <input type="time" class="form-control" id="endDate" required v-model="newEvent.endTime">
                      </div>
                    </div>

                    <!-- Ghi chu -->
                    <div class="mb-3 row">
                      <label for="endDate" class="form-label fs-6 text">Ghi chú: </label>
                      <input type="text" class="form-control" id="endDate" required v-model="newEvent.takenote">
                    </div>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="saveEvent(newEvent)">Tạo</button>
                    <!-- <button type="button" class="btn btn-primary" @click="saveEvent(newEvent)">Lưu</button> -->
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- End: Thêm sự kiện -->

          <!-- My calendar -->
          <div class="option-calendar">
            <p class="fs-4 text">Tuỳ chọn</p>
            <div class="form-check">
              <input class="form-check-input" type="checkbox" value="" id="teaching-calendar-id" v-model="isTeaching" checked>
              <label class="form-check-label" for="teaching-calendar-id">
                <p class="fs-6 text">Lịch dạy</p>
              </label>
            </div>
            <div class="form-check">
              <input class="form-check-input" type="checkbox" value="" id="personal-calendar-id" v-model="isPersonal">
              <label class="form-check-label" for="personal-calendar-id">
                <p class="fs-6 text">Cá nhân</p>
              </label>
            </div>
          </div>
          <!-- End: My calendar -->
        </div>
        <div class="col-10">
          <FullCalendar :options='calendarOptions' class="custom-calendar" id="calendar-lecturer"
            style="padding: 15px;"/>
          <!-- hiển thị thông tin sự kiện -->
          <span class="badge bg-success event-tooltip" v-if="selectedEvent"
            :style="{ top: tooltipTop, left: tooltipLeft }">
            <p class="fs-6 text">{{ selectedEvent.title }}</p>
            <p class="fs-6 text">({{ getTimeOfStringDate(selectedEvent.start) }} - {{
              getTimeOfStringDate(selectedEvent.end) }})</p>
          </span>
          <!-- End: hiển thị thông tin sự kiện -->
        </div>
      </div>

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
import { getAssignmentLecturer, getPersonalEvent, saveNewEvent } from '@/service/lecturer/CalendarViewService';
import { countDayInWeekBetweenDate, findClosestDayOfWeek, getDatePart } from '@/helpers/timehelpers';
import { Event } from '@/interface/lecturer/Event';
import { getTimeOfStringDate, hideModal, resetModal, showModal } from './CalendarView';
import { PersonalEvent } from '@/interface/lecturer/PeronalEvent';
import { ResponsePersonalEvent } from '@/helpers/ResponsePersonalEvent';
// token
const jwtToken = computed(() => store.getters.getAccessToken)

let selectedEvent = ref<Event | null>(null);
const tooltipLeft = ref<string>('0px');
const tooltipTop = ref<string>('0px');
const assignmentLecturers = ref<AssignmentLecturer[]>([])
const isVisible = ref<boolean>(false)

const isPersonal = ref<boolean>(false);
const isTeaching = ref<boolean>(true);

// fetch data from api 
const personalEvent = ref<ResponsePersonalEvent[]>([]);
const teachingEvent = [{
    title: '',
    start: '',
    end: ''
}]

// data gửi lên server
const newEvent = ref<PersonalEvent>({
  title: "",
  date: "",
  beginTime: "",
  endTime: "", 
  takenote: ""
});

// calendar
const calendarOptions = ref({
  plugins: [dayGridPlugin],
  initialView: 'dayGridMonth',
  selectable: true,
  weekends: false,
  events: [{
    title: '',
    start: '',
    end: ''
  }],
  themeSystem: 'bootstrap5',

  backgroundColor: '#25476A',

  headerToolbar: {
    start: 'prevYear,nextYear prev,next today',
    center: 'title',
    end: 'dayGridMonth,timeGridWeek,timeGridDay' // Thêm plugin vào end
  },

  eventMouseEnter: (info: any) => {
    handleEventMouseEnter(info)
  },

  eventMouseLeave: () => {
    console.log("eventMouseLeave was called!")
    console.log("selectedEvent: ", selectedEvent.value)
    handleEventMouseLeave()
  }
});

const createCalendar = (assignmentLecturers: AssignmentLecturer[]) => {
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
        title: assignmentLecturer.nameSubject + " - " + assignmentLecturer.codeClassroom,
        start: getDatePart(teachingStartWeek.toLocaleDateString("en-CA")) + " " + assignmentLecturer.beginTime,
        end: getDatePart(teachingStartWeek.toLocaleDateString("en-CA")) + " " + assignmentLecturer.endTime,
      }

      console.log("event: ", event)
      // calendarOptions.value.events.push(event)
      teachingEvent.push(event)

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

    // fetched teaching event
    createCalendar(assignmentLecturers.value);

    // fetch personal event
    const feachedPersonaleEvent = await getPersonalEvent(jwtToken.value);
    personalEvent.value = feachedPersonaleEvent;
  } catch (error) {
    console.log("error at fecth assignment Lecturer: ", error)
    throw error;
  }
});


const handleEventMouseEnter = (info: any) => {
  selectedEvent.value = info.event;
  console.log("Sự kiện được nhấn: ", selectedEvent.value)
  tooltipTop.value = info.jsEvent.clientY + 10 + 'px'
  tooltipLeft.value = info.jsEvent.clientX + 10 + 'px'
}

const handleEventMouseLeave = () => {
  console.log("Called handleEventMouseLeave!!")
  selectedEvent.value = null
}

const saveEvent = async (newEvent: PersonalEvent) => {
  saveNewEvent(jwtToken.value, newEvent);
  hideModal(isVisible.value)
  resetModal(newEvent)
}


</script>

<style scoped>
@import url(CalendarView.css);
</style>
