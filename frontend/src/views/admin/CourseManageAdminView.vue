<template>
  <header>
    <HomeSidebar></HomeSidebar>
    <HomeHeader></HomeHeader>
  </header>

  <main style="margin-top: 58px; margin-left: 60px">
    <div class="container pt-4">
      <br>
      <!-- Content -->
      <div class="card">
        <!-- Body -->
        <div class="card-body">
          <div class="row">

            <!-- Input cho Điểm số -->
            <div class="col-9">
              <div class="form-group">
                <input type="text" class="form-control" id="gradeFilter" placeholder="Nhập khoá hoc">
                <button class="btn btn-primary" id="search-button" type="submit">Tìm kiếm</button>
              </div>
            </div>

            <!-- Modal -->
            <div class="col-3">
              <!-- Button trigger modal -->
              <button type="button" id="add-course-btn" class="btn btn-primary" data-bs-toggle="modal"
                data-bs-target="#exampleModal" @click="showModal = true">
                Thêm khoá học
              </button>

              <!-- Modal -->
              <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                aria-hidden="true">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h1 class="modal-title fs-5" id="exampleModalLabel">Tạo Khoá Học Mới</h1>
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                      <div class="form-group row">
                        <label for="courseNameId" class="col-sm-3 col-form-label">Tên khoá học:</label>
                        <div class="col-sm-9">
                          <input type="text" v-model="courseData.nameCourse" class="form-control" id="courseNameId">
                        </div>
                      </div> <br>

                      <div class="form-group row">
                        <label for="imageId" class="col-sm-3 col-form-label">URL: </label>
                        <div class="col-sm-9">
                          <input type="text" v-model="courseData.url" class="form-control" id="imageId">
                        </div>
                      </div> <br>

                      <div class="form-group row">
                        <label for="decriptionId" class="col-sm-3 col-form-label">Mô tả:</label>
                        <div class="col-sm-9">
                          <textarea v-model="courseData.description" class="form-control" rows="5"
                            id="decriptionId"></textarea>
                        </div>
                      </div>
                    </div>
                    <div class="modal-footer">
                      <button type="submit" class="btn btn-secondary" data-bs-dismiss="modal"
                        @click="createCourse">Tạo</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Bootstrap table version 5 -->
          <hr>
          <div class="row">
            <table class="table">
              <thead>
                <tr>
                  <th scope="col">
                    <div class="filters">
                      <div class="form-group">
                        <label for="classNumber">STT:</label>
                        <input type="number" class="form-control" id="classNumber" v-model="filterIndex">
                      </div>
                    </div>
                  </th>
                  <!-- Tên khoá học -->
                  <th scope="col">
                    <div class="filters">
                      <div class="form-group">
                        <label for="courseName">Tên khoá học:</label>
                        <input type="text" class="form-control" id="courseName" v-model="filterName">
                      </div>
                    </div>
                  </th>
                  <th scope="col">
                    <div class="filters">
                      <div class="form-group">
                        <label for="createdAt">Ngày tạo:</label>
                        <input type="date" class="form-control" id="createdAt" v-model="filterCreatedAt">
                      </div>
                    </div>

                  </th>
                  <th scope="col">
                    <div class="filters">
                      <div class="form-group">
                        <label for="classNumber">Số lớp:</label>
                        <input type="number" class="form-control" id="classNumber" v-model="filterNumClass">
                      </div>
                    </div>
                  </th>

                  <!-- Trạng thái -->
                  <th scope="col">
                    <div class="filters">
                      <div class="form-group">
                        <label for="status">Trạng thái:</label>
                        <select class="form-control" id="status" v-model="filterStatus">
                          <option value="">-- Chọn trạng thái --</option>
                          <option value="Đang mở">Đang mở</option>
                          <option value="Đã đóng">Đã đóng</option>
                          <!-- Thêm các lựa chọn khác tùy thuộc vào nhu cầu của bạn -->
                        </select>
                      </div>
                    </div>
                  </th>
                  <!-- Action -->
                  <th scope="col"></th>
                  <th scope="col"></th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(course, index) in filteredCourses" :key="index">
                  <th scope="row">{{ index + 1 }}</th>
                  <td>{{ course.nameCourse }}</td>
                  <td>{{ course.createdAt }}</td>
                  <td>{{ course.numClass }}</td>
                  <td>
                    <span v-if="course.isClosed" class="badge bg-warning">Đã đóng</span>
                    <span v-else class="badge bg-success">Đang mở</span>
                  </td>

                  <td>
                    <button v-if="course.isClosed" class="open-btn"
                      @click="sendRequestClose(course.courseId, true)"></button>
                    <button v-else class="delete-btn" @click="sendRequestClose(course.courseId, false)"></button>
                  </td>

                  <!-- Edit thông tin khoá học -->
                  <td>
                    <button class="edit-btn" type="button" data-bs-toggle="modal" data-bs-target="#exampleModalEdit"
                      @click="showModalEdit = true">
                    </button>

                    <!-- Modal -->
                    <div class="modal fade" id="exampleModalEdit" tabindex="-1" aria-labelledby="exampleModalLabel"
                      aria-hidden="true">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">Chỉnh sửa thông tin khoá học</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>

                          <div class="modal-body">
                            <div class="form-group row">
                              <label for="courseNameId" class="col-sm-3 col-form-label">Tên khoá học:</label>
                              <div class="col-sm-9">
                                <input type="text" v-model="editCourse.nameCourse" class="form-control"
                                  id="editCourseNameId">
                              </div>
                            </div> <br>

                            <div class="form-group row">
                              <label for="imageId" class="col-sm-3 col-form-label">URL: </label>
                              <div class="col-sm-9">
                                <input type="text" v-model="editCourse.url" class="form-control" id="editUrl"
                                  v-bind:placeholder="course.url">
                              </div>
                            </div> <br>

                            <div class="form-group row">
                              <label for="decriptionId" class="col-sm-3 col-form-label">Mô tả:</label>
                              <div class="col-sm-9">
                                <textarea class="form-control" rows="5" id="decriptionId"
                                  v-model="editCourse.description" :placeholder="course.description"></textarea>
                              </div>
                            </div>
                          </div>
                          <div class="modal-footer">
                            <button type="submit" class="btn btn-secondary" data-bs-dismiss="modal"
                              @click="editCourseFunction(course.courseId)">Cập nhật</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </td>
                  <!-- End edit thông tin khoá học -->

                  <!-- Chi tiết khoá học -->
                  <td>
                    <router-link :to="{ name: 'adminDetailCourse', params: { courseId: course.courseId } }"
                      class="btn btn-info">Chi tiết</router-link>
                  </td>
                  <router-view></router-view>
                </tr>
              </tbody>
            </table>

            <!-- Phân trang -->
            <div class="d-flex justify-content-center">
              <nav aria-label="Page navigation example">
                <ul class="pagination">
                  <li class="page-item">
                    <button class="page-link" :disabled="pageNumber === 0" @click="previousPage"
                      :class="{ 'fw-bold': pageNumber === 0 }">
                      <span aria-hidden="true">&laquo;</span>
                      <span class="visually-hidden">Previous</span>
                    </button>
                  </li>
                  <li class="page-item"><span class="page-link">{{ pageNumber + 1 }}</span></li>
                  <li class="page-item">
                    <button class="page-link" @click="nextPage" :disabled="pageNumber + 1 === totalPages"
                      :class="{ 'fw-bold': pageNumber + 1 === totalPages }">
                      <span aria-hidden="true">&raquo;</span>
                      <span class="visually-hidden">Next</span>
                    </button>
                  </li>
                </ul>
              </nav>
            </div>
          </div>
        </div>
        <!-- Khoá học đã đóng -->
        <hr>
        <div class="course-close">
          <h4 class="text-primary font-weight-bold">Danh sách khoá học</h4>
          <br>
          <!-- card -->
          <div class="d-flex justify-content-between mt-30">
            <div class="card" style="width: 18rem;" v-for="(course, index) in filteredCourses" v-bind:key=index>
              <div>
                <span v-if="course.isClosed" class="badge rounded-pill bg-warning">Đã đóng</span>
                <span v-else class="badge rounded-pill bg-success">Đang mở</span>
              </div>
              <h5> {{ course.nameCourse }}</h5>
              <img :src="course.url" class="card-img-top" alt="Ảnh khoá học">
              <!-- body -->
              <div class="card-body">
                <p class="card-text">{{ course.description }}</p>
              </div>

              <!-- footer -->
              <div class="card-footer">
                <button class="btn btn-primary">Chi tiết</button>
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
import axios from 'axios';
import { ref, onMounted, computed, watch } from 'vue';
import { useStore } from 'vuex';

const store = useStore();
const jwtToken = computed(() => store.getters.getAccessToken)

interface CourseData {
  nameCourse: string;
  url: string;
  description: string;
}

const courseData = ref<CourseData>({
  nameCourse: '',
  url: '',
  description: ''
});

const showModal = ref(false)

// Hide modal
const hideModal = () => {
  showModal.value = false;
}

const createCourse = () => {
  console.log("Bắt đầu tạo khoá học")
  const data: CourseData = {
    nameCourse: courseData.value.nameCourse,
    url: courseData.value.url,
    description: courseData.value.description
  };

  axios.post<CourseData>('http://localhost:8080/course-management/admin/courses', data)
    .then(response => {
      console.log('Khoá học đã được tạo:', response.data);
      resetForm();
      fetchCourses();
      hideModal();
    })
    .catch(error => {
      console.error('Lỗi khi tạo khoá học:', error);
    });
};

const resetForm = () => {
  courseData.value.nameCourse = '';
  courseData.value.url = '';
  courseData.value.description = '';
};

// Render dữ liệu course
interface CourseResponse {
  courseId: number;
  nameCourse: string | null;
  createdAt: string;
  numClass: number;
  isClosed: boolean;
  url: string;
  description: string;
}

const courses = ref<CourseResponse[]>([]);
const pageNumber = ref<number>(0);
const totalPages = ref<number>(1);

// fetch api
const fetchCourses = async () => {
  try {
    const response = await axios.get<{ content: CourseResponse[]; totalPages: number }>(
      `http://localhost:8080/course-management/admin/courses?page=${pageNumber.value}`, {
      headers: {
        'Authorization': `Bearer ${jwtToken.value}`
      }
    }
    );
    courses.value = response.data.content;
    totalPages.value = response.data.totalPages;
    console.log("Thành công")
  } catch (error) {
    console.error('Error fetching courses:', error);
    console.log("Thất bại")
  }
};

const nextPage = () => {
  if (pageNumber.value < totalPages.value) {
    pageNumber.value++;
    fetchCourses();
  }
};

const previousPage = () => {
  if (pageNumber.value > 0) {
    pageNumber.value--;
    fetchCourses();
  }
};

onMounted(() => {
  fetchCourses();
})

// Tạo bộ lọc 
const filterIndex = ref<string | null>(null);
const filterName = ref<string | null>(null);
const filterNumClass = ref<number>(0);
const filterStatus = ref<string>('');
const filterCreatedAt = ref<string | null>(null);

const filteredCourses = computed(() => {
  return courses.value.filter(course => {

    // Lọc theo filterName
    if (filterName.value !== null && !course.nameCourse?.toLowerCase().includes(filterName.value.toLowerCase())) {
      return false;
    }
    // Lọc theo filterCreatedAt
    if (filterCreatedAt.value !== null && !course.createdAt?.toLocaleLowerCase().includes(filterCreatedAt.value.toString())) {
      return false;
    }
    // Lọc theo filterNumClass
    if (filterNumClass.value !== 0 && course.numClass !== filterNumClass.value) {
      return false;
    }
    // Lọc theo filterStatus
    if (filterStatus.value !== undefined && filterStatus.value !== '') {
      if (filterStatus.value === 'Đã đóng' && course.isClosed) {
        return false;
      }
      if (filterStatus.value === 'Đang mở' && !course.isClosed) {
        return false;
      }
    }
    // Nếu không có bộ lọc nào áp dụng, trả về true để bao gồm mục này trong kết quả
    return true;
  });
});

watch([filterIndex, filterName, filterNumClass, filterStatus, filterCreatedAt], () => {
  filteredCourses.value;
  console.log(courses)
  console.log(filterStatus)
  console.log(filterCreatedAt)
  console.log("chạy watch")
});

// Gửi yêu cầu đóng
const sendRequestClose = (courseId: number, isClosed: boolean) => {
  axios.put(`http://localhost:8080/course-management/admin/courses`, { isClosed: isClosed, courseId: courseId }, {
    headers: {
      'Authorization': `Bearer ${jwtToken.value}`
    }
  })
    .then(response => {
      fetchCourses();
      console.log("Đã gửi post")
      console.log("Dữ liệu gửi về cho yêu cầu đóng khoá học", response.data)
      console.log("Id của course: " + courseId);
      console.log("Dữ liệu gửi về: " + isClosed);
      console.log("Đường dẫn hiện tại: " + window.location.href)
    })
    .catch(error => {
      console.log("Id của course: " + courseId);
      console.log("Dữ liệu gửi về: " + isClosed);
      console.log("Gửi yêu cầu thất bại", error);
      console.log("Đường dẫn hiện tại: " + window.location.href)
    })
}

// Edit thông tin khoá học
interface EditCourse {
  courseId: number;
  nameCourse: string | null;
  url: string;
  description: string;
}

const editCourse = ref<EditCourse>({
  courseId: -1,
  nameCourse: '',
  url: '',
  description: '',
});

const editCourseFunction = (courseId: number) => {
  const data: EditCourse = {
    courseId: courseId,
    nameCourse: editCourse.value.nameCourse,
    url: editCourse.value.url,
    description: editCourse.value.description
  }

  axios.patch<EditCourse>(`http://localhost:8080/course-management/admin/courses`, data, {
    headers: {
      'Authorization': `Bearer ${jwtToken.value}`
    }
  })
    .then(response => {
      console.log("Bắt đầu gửi dữ liệu - Patch", response.data);
      console.log("Dữ liệu gửi đi: ", data)
      fetchCourses();
      resetFormEdit();
      hideModalEdit();
    })
    .catch(error => {
      console.log(data.courseId)
      console.log(data.nameCourse)
      console.log(data.url)
      console.log(data.description)
      console.error("Đã xảy ra lỗi khi gửi yêu cầu PATCH:", error);
    });
}

const resetFormEdit = () => {
  editCourse.value.courseId = -1;
  editCourse.value.nameCourse = '';
  editCourse.value.url = '';
  editCourse.value.description = '';
}

const showModalEdit = ref(false)

// Hide modal
const hideModalEdit = () => {
  showModalEdit.value = false;
}
</script>

<style>
#edit-course-btn {
  width: 150px;
  height: 40px;
}

.edit-btn {
  background-image: url('/src/assets/edit.png');
  background-size: cover;
  width: 40px;
  height: 40px;
  background-color: #ffc107;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.delete-btn:hover {
  background-color: #ffca28;
}

.delete-btn {
  background-image: url('/src/assets/delete.png');
  background-size: cover;
  width: 40px;
  height: 40px;
  background-color: #dc3545;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.delete-btn:hover {
  background-color: #c82333;
}

.open-btn {
  background-image: url('/src/assets/open.png');
  background-size: cover;
  width: 40px;
  height: 40px;
  background-color: #28a745;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.open-btn:hover {
  background-color: #218838;
  ;
}
</style>