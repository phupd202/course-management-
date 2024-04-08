import { Score } from "@/interface/Score";
import axios from "axios";

export async function getScore(jwtToken: string, classroomId: number): Promise<Score[]> {
    try {
       const response = await axios.get<Score[]>("http://localhost:8080/course-management/user/get-score/" + classroomId, {
         headers: {
            'Authorization': `Bearer ${jwtToken}`
          }
       });
       console.log("Called api get enrollment");
       return response.data;
    } catch(error) {
       console.error("Encountered error while get enrollment: ", error);
       throw error; 
    }
}