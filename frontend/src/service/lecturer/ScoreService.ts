import { Enrollment } from "@/interface/lecturer/Enrollment";
import { UpdateScore } from "@/interface/lecturer/UpdateScore";
import axios from "axios";

export async function getEnrollmentOfClass(classroomId: number, subjectId: number, jwtToken: string): Promise<Enrollment[]> {
    try {
        const response = await axios.get<Enrollment[]>("http://localhost:8080/course-management/lecturer/get-enrollment", {
            params: {
                classroomId: classroomId,
                subjectId: subjectId
            },
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        });
        console.log("Lấy danh sách enrollment thành công");
        return response.data;
    } catch (error) {
        console.log("Có lỗi xảy ra khi lấy danh sách enrollment", error);
        throw error;
    }
}

// update score on server
export async function sendScore(updateScore: UpdateScore, jwtToken: string): Promise<any> {
    try {
        const response = await axios.post<UpdateScore>("http://localhost:8080/course-management/lecturer/send-score", updateScore, {
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        });
        console.log("Data was sent is: ", updateScore.scores, updateScore.statues, updateScore.enrollmentIds)
        return response.data;
    } catch (error) {
        console.log("Having a error while update score");
        throw error;
    }
}
