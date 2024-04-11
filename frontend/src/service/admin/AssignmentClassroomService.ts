import { AssignmentRequest } from './../../interface/admin/AssignmentRequest';
import { Lecturer } from "@/interface/admin/Lecturer";
import { SubjectOfClass } from "@/interface/admin/SubjectOfClass";
import axios from "axios";

export async function getSubject(classroomId: number, jwtToken: string): Promise<SubjectOfClass[]> {
    try {
        const response = await axios.get<SubjectOfClass[]>("http://localhost:8080/course-management/admin/assign-classroom/" + classroomId, {
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        });
        console.log("")

        console.log("Sending data: classroomId = " + classroomId);
        console.log("Received data: subjects = ", response.data);

        if (response && response.data) {
            return response.data;
        } else {
            throw new Error("No data received from server");
        }
    } catch (error) {
        console.error("Get subject failure:", error);
        throw error;
    }
}

// get lecturer is freedom to create new assignment
export async function getLecturerFree(jwtToken: string, assignmentRequest: AssignmentRequest): Promise<Lecturer[]> {
    console.log("assignmentRequest: ", assignmentRequest)
    try {
        const response = await axios.post<Lecturer[]>("http://localhost:8080/course-management/admin/assign-classroom/get-lecturer-free", assignmentRequest, {
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        });
        console.log("Get free lecturer successful");
        console.log("Đã gọi hàm getLecturer");
        console.log("Lecturers: ", response.data);
        return response.data;
    } catch (error) {
        console.log("Encountered error at get free lecturer: " + error);
        console.log("Đã gọi hàm getLecturer");
        throw error;
    }
}

// create assignment for lecturer in database
export async function saveAssignment(jwtToken: string, assignmentRequest: AssignmentRequest): Promise<void> {
    console.log("assignmentRequest: ", assignmentRequest);
    try {
        await axios.put("http://localhost:8080/course-management/admin/assign-classroom/save-assignment", assignmentRequest, {
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        });
        console.log("Save assignment successfully!");
    } catch (error) {
        console.log("Encountered error at save assignment: " + error);
        throw error;
    }
}

