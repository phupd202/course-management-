

import { createRouter, createWebHistory } from 'vue-router'
import UpdateInfoView from '@/views/UpdateInfoView.vue'
import SignInView from '@/views/SignInView.vue'
import SignUpView from '@/views/SignUpView.vue'
import EnrollmentView from '@/views/EnrollmentView.vue'

import DetailCourseView from '@/views/admin/DetailCourseView.vue'

import HomeUserView from '@/views/guest/home_user/HomeUserView.vue'
import AdminInterestParty from '@/views/admin/AdminInterestParty.vue'
import AdminMailTemplate from '@/views/admin/AdminMailTemplate.vue'
import AdminRegisterQueue from '@/views/admin/AdminRegisterQueue.vue'
import DetailCourseGuestView from '@/views/guest/detail_course/DetailCourseGuestView.vue';
import AssignClassroom from '@/views/admin/assign-classroom/AssignClassroom.vue'
import CalendarView from '@/views/lecturer/calendar-view/CalendarView.vue'
import DetailAssignmentView from '@/views/lecturer/detail-assignment/DetailAssignmentView.vue'
import LecturerScoreView from '@/views/lecturer/lecture-score/LecturerScoreView.vue'
import UserScoreView from '@/views/user/user-score-view/UserScoreView.vue'
import UserMyCourse from '@/views/user/user-my-course/UserMyCourse.vue'
import CourseManageAdminView from '@/views/admin/course-manage-admin/CourseManageAdminView.vue'
import AdminManagementClass from '@/views/admin/manage-classroom/AdminManagementClass.vue'
import TestView from '@/views/test/TestView.vue'
import ExamAndReview from '@/views/lecturer/exam-and-review/ExamAndReview.vue'
import CreateExamView from '@/views/lecturer/create-exam/CreateExamView.vue'
import ListQuestionView from '@/views/lecturer/list-quesion/ListQuestionView.vue'

const routes = [
  {
    path: '/course-management/user/update-info',
    name: 'update-info',
    component: UpdateInfoView
  },
  {
    path: '/course-management/signin',
    name: 'signin',
    component: SignInView
  },
  {
    path: '/course-management/signup',
    name: 'signup',
    component: SignUpView
  },
  {
    path: '/course-management/user/enrollment',
    name: 'enrollment',
    component: EnrollmentView,
    // meta: { requiresAuth: true, allowedRoles: ["USER"] }
  },
  {
    path: '/course-management/admin/courses',
    name: 'adminCourse',
    component: CourseManageAdminView,
    meta: { requiresAuth: true, allowedRoles: ["ADMIN"] }
  },
  {
    path: '/course-management/admin/detail-course/:courseId',
    name: 'adminDetailCourse',
    component: DetailCourseView,
    meta: { requiresAuth: true, allowedRoles: ["ADMIN"] }
  },
  {
    path: '/course-management/admin/management-class',
    name: 'adminManagementClass',
    component: AdminManagementClass,
    meta: { requiresAuth: true, allowedRoles: ["ADMIN"] }
  },

  {
    path: '/course-management/lecturer/score',
    name: 'lecturerScore',
    component: LecturerScoreView,
    meta: { requiresAuth: true, allowedRoles: ["LECTURER"] }
  },
  {
    path: '/course-management/admin/assign-classroom/:classroomId',
    name: 'adminAssignClassroom',
    component: AssignClassroom,
    meta: { requiresAuth: true, allowedRoles: ["ADMIN"] }
  },
  {
    path: '/course-management/admin/interest-party',
    name: 'adminInterestParty',
    component: AdminInterestParty,
    meta: { requiresAuth: true, allowedRoles: ["ADMIN"] }
  },
  {
    path: '/course-management/lecturer/detail-assignment',
    name: 'lecturerDetailAssignment',
    component: DetailAssignmentView,
    props: (route: { query: { classroomId: number; subjectId: number } }) => ({
      classroomId: route.query.classroomId as number, // Ép kiểu route.query.classroomId về kiểu string
      subjectId: route.query.subjectId as number // Ép kiểu route.query.subjectId về kiểu string
    }),
    meta: { requiresAuth: true, allowedRoles: ["LECTURER"] }

  },
  {
    path: '/course-management/lecturer/calendar',
    name: 'lecturerCalendar',
    component: CalendarView,
    meta: { requiresAuth: true, allowedRoles: ["LECTURER"] }
  },

  {
    path: '/course-management/lecturer/create-exam/:subjectId',
    name: 'createExam',
    component: CreateExamView,
    meta: { requiresAuth: true, allowedRoles: ["LECTURER"] }
  },

  {
    path: '/course-management/lecturer/review-and-exam',
    name: 'examAndReview',
    component: ExamAndReview,
    meta: { requiresAuth: true, allowedRoles: ["LECTURER"] }
  },

  {
    path: '/course-management/lecturer/list-question/:subjectId',
    name: 'listQuestionView',
    component: ListQuestionView,
    meta: { requiresAuth: true, allowedRoles: ["LECTURER"] }
  },

  {
    path: '/course-management/admin/mail-template',
    name: 'adminMailTemplate',
    component: AdminMailTemplate,
    meta: { requiresAuth: true, allowedRoles: ["ADMIN"] }
  },
  {
    path: '/course-management/admin/register-queue',
    name: 'adminRegisterQueue',
    component: AdminRegisterQueue,
    meta: { requiresAuth: true, allowedRoles: ["ADMIN"] }
  },
  {
    path: '/course-management/home',
    name: 'home',
    component: HomeUserView
  },
  {
    path: '/course-management/user/my-course',
    name: 'userMyCourse',
    component: UserMyCourse,
    meta: { requiresAuth: true, allowedRoles: ["USER"] }
  },
  {
    path: '/course-management/user/my-score',
    name: 'userMyScore',
    component: UserScoreView,
    meta: { requiresAuth: true, allowedRoles: ["USER"] }
  },
  {
    path: '/course-management/detail-course',
    name: 'detailCourseGuest',
    component: DetailCourseGuestView,
    props: (route: { params: { courseId: number } }) => ({
      courseId: route.params.courseId as number
    })
  }, 
  {
    path: '/test',
    name: 'test',
    component: TestView
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// const store = useStore();

// router.beforeEach((to, from, next) => {
//   const isLoggedIn = store.state.isLoggedIn as boolean;
//   const role = store.state.role as string; // Đổi state.role thành state.userRole
//   const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
//   const allowedRoles = to.meta.allowedRoles as string[];

//   if (requiresAuth && !isLoggedIn) {
//     // Nếu yêu cầu đăng nhập nhưng người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
//     next('/course-management/sigin');
//   } else if (requiresAuth && isLoggedIn) {
//     // nếu không hợp lệ hoặc ko có quyền
//     if (!allowedRoles || !allowedRoles.includes(role)) {
//       next('/course-managment/login'); // redirect to home
//     } else {
//       // Nếu có quyền hợp lệ
//       if (role === 'USER') {
//         next('/course-management/user/home');
//       } else if (role === 'ADMIN') {
//         next('/course-management/admin/home');
//       } else {
//         next();
//       }
//     }
//   } else {
//     next(); //  Else --> next
//   }
// });

export default router;
