import { ClassroomOfCourse } from "@/interface/ClassroomOfCourse";
import { SubjectOfCourse } from "@/interface/SubjectOfCourse";
import axios from "axios";

export async function getMyCourses(jwtToken: string): Promise<ClassroomOfCourse[]> {
    try {
       const response = await axios.get<ClassroomOfCourse[]>("http://localhost:8080/course-management/user/get-my-course", {
        headers: {
            'Authorization': `Bearer ${jwtToken}`
          }
       });
       console.log("Called api get my course");
       
       return response.data;
    } catch(error) {
       console.error("Encountered error while get course: ", error);
       throw error; 
    }
}

export async function getSubjectOfCourse(jwtToken: string, courseId: number): Promise<SubjectOfCourse[]> {
   try {
      const response = await axios.get<SubjectOfCourse[]>("http://localhost:8080/course-management/user/subject-of-class/"+ courseId, {
       headers: {
           'Authorization': `Bearer ${jwtToken}`
         }
      });

      console.log("Called api get my course");
      return response.data;
   } catch(error) {
      console.error("Encountered error while get course: ", error);
      throw error; 
   }
}