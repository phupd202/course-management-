import { RegisterResponse } from "@/interface/RegisterResponse";
import axios from "axios";

export async function getRegister(classroomId: number, courseId:number): Promise<RegisterResponse[]> {
    try {
        const response = await axios.get<RegisterResponse[]>("http://localhost:8080/course-management/admin/get-register-in-class", {
            params: {
                classroomId: classroomId, 
                courseId: courseId
            }
        });
        console.log("classroomId: " +  classroomId)
        console.log("Fetched register!!")
        return response.data;
    } catch(error) {
        console.log("Fetch register failure!");
        throw error; 
    }
}
