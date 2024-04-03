import { ClassroomOfCourse } from '@/interface/ClassroomOfCourse';
import { CourseResponse } from '@/interface/CourseResponse';
import axios from 'axios';

export async function getAllCourses(): Promise<CourseResponse[]> {
   try {
      const response = await axios.get<CourseResponse[]>("http://localhost:8080/course-management/get-all-courses");
      return response.data;
   } catch(error) {
      console.error("Encountered error while get course: ", error);
      throw error; // Throw lỗi để xử lý ở nơi gọi hàm này
   }
}


export async function getClassroom(courseId: number): Promise<ClassroomOfCourse[]> {
   try {
      const response = await axios.get<ClassroomOfCourse[]>(`http://localhost:8080/course-management/get-class/${courseId}`);
      return response.data;
   } catch(error) {
      console.error("Failure when get class of course", error);
      throw error; // Throw lỗi để xử lý ở nơi gọi hàm này
   }
}
