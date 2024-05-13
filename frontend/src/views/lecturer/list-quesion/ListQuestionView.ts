import { Question } from "@/interface/lecturer/Question";
import axios from "axios";

export async function getLecturerInfo(jwtToken: string): Promise<Number> {
    try {
        const response = await axios.get<number>("http://localhost:8080/course-management/lecturer/fetch-infor-lecturer", {
          headers: {
            'Authorization': `Bearer ${jwtToken}`
          }
        });
    
        console.log("Get info successful!")
        return response.data;
      } catch(error) {
        console.log("Get info failure!")
        throw error
      }
}

export async function getQuestion(jwtToken: string, subjectId: number): Promise<Question[]> {
    try {
        const response = await axios.get<Question[]>("http://localhost:8080/course-management/lecturer/list-question/" + subjectId, {
          headers: {
            'Authorization': `Bearer ${jwtToken}`
          }
        });
    
        console.log("Get question successful!")
        return response.data;
      } catch(error) {
        console.log("Get question failure!")
        throw error
      }
}