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

export function countFishedSubject(subjects: SubjectOfClass[]): number {
    let finishedSubject = 0;

    subjects.forEach(subject => {
        if(subject.isClosed === true) {
            finishedSubject++;
        }
    });

    return finishedSubject;
}

export async function closeClassroom(classroomId: number, jwtToken: string): Promise<void> {
    console.log("running close classroom!!");

    const confirmClose = window.confirm("Thao tác chỉ được thực hiện 1 lần! Vui lòng cân nhắc kỹ. Bạn có muốn đóng lớp không?");

    if (confirmClose) {
        try {
            await axios.put(`http://localhost:8080/course-management/admin/assign-classroom/close-classroom/${classroomId}`, null, {
                headers: {
                    'Authorization': `Bearer ${jwtToken}`
                }
            });
            alert("Đóng lớp thành công");
            console.log("Close classroom successful!!");
        } catch(error) {
            console.log("Have a problem while closing classroom!");
            console.log(error); 
            throw error;
        }
    }
}

/*
* Output: endDate from estimate
*/
export function autoFillEndDate(subjectId: number, beginDate: string, subjects: SubjectOfClass[]): string {
    const beginDateObject = new Date(beginDate);
    let estimate: number = 0;

    for (const subject of subjects) {
        if (subject.subjectId === subjectId) {
            estimate = subject.estimate;
            break; 
        }
    }

    // find endDate with estimate time
    const endDateObject = new Date(beginDateObject);
    endDateObject.setMonth(endDateObject.getMonth() + Number(estimate));
    console.log("estimate is: ", estimate)

    return endDateObject.toISOString().slice(0, 16);
}
