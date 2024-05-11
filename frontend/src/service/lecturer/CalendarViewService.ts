import { ResponsePersonalEvent } from "@/helpers/ResponsePersonalEvent";
import { AssignmentLecturer } from "@/interface/lecturer/AssignmentLecturer";
import { PersonalEvent } from "@/interface/lecturer/PeronalEvent";
import axios from "axios";
import Swal from "sweetalert2";

export async function getAssignmentLecturer(jwtToken: string): Promise<AssignmentLecturer[]> {
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

export async function saveNewEvent(jwtToken: string, personalEvent: PersonalEvent) {
  console.log("Data was sent: ", personalEvent)
  try {
    const response = await axios.post<PersonalEvent>("http://localhost:8080/course-management/lecturer/save-event", personalEvent, {
      headers: {
        'Authorization': `Bearer ${jwtToken}`
      }
    });

    Swal.fire("Success!", "Thêm sự kiện mới thành công!!", "success");

    console.log("Sent endpoint")
    console.log("Data was sent: ", personalEvent, "response: ", response)
  } catch (error) {
    console.log("error in while get assignment!", error)
    Swal.fire("Success!", "Thêm sự kiện mới không thành công! Vui lòng thử lại", "error");
    throw error
  }
}

export async function getPersonalEvent(jwtToken: string): Promise<ResponsePersonalEvent[]> {
  console.log("Called getPersonalEvent!!")
  try {
    const response = await axios.get<ResponsePersonalEvent[]>("http://localhost:8080/course-management/lecturer/get-personal-event", {
      headers: {
        'Authorization': `Bearer ${jwtToken}`
      }
    });

    console.log("Get personal event successful!")
    return response.data;
  } catch(error) {
    console.log("Get personal event failure!")
    throw error
  }
}