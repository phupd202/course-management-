import { countDayInWeekBetweenDate, findClosestDayOfWeek, getDatePart } from "@/helpers/timehelpers";
import { AssignmentLecturer } from "@/interface/lecturer/AssignmentLecturer";
import axios from "axios";

export async function getAssignmentLecturer(jwtToken: string) : Promise<AssignmentLecturer[]>{
    try {
      const response = await axios.get<AssignmentLecturer[]>("http://localhost:8080/course-management/lecturer/get-all-assignment", {
        headers: {
          'Authorization': `Bearer ${jwtToken}`
        }
      });
      console.log("Get assignment successfull!")
      console.log("Dữ liệu nhận được, " + response.data)
      return response.data;
  
    //   createCalendar(assignmentLecturers.value);
    } catch (error) {
      console.log("error in while get assignment!", error)
      throw error
    }
}
