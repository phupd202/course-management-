import { CourseResponse } from "@/interface/home/CourseResponse";
import { FormRequest } from "@/interface/home/FormRequest";
import axios from "axios";

export async function sendForm(formRequest: FormRequest): Promise<void>{
    console.log("Dữ liệu gửi đi: " + formRequest)
    try {
       const response = await axios.post<FormRequest>("http://localhost:8080/course-management/interest-course", formRequest)
       console.log("Dữ liệu gửi đi: ", response.data);
       alert("Vui lòng kiểm tra hòm thư của bạn!")
       resetForm(formRequest);
       console.log("Yêu cầu đã thành công");
    } catch(error) {
       console.error("Gặp lỗi khi đăng ký:", error)
       resetForm(formRequest)
    }
}

export async function getAllCourse(): Promise<CourseResponse[]> {
    try {
       const response = axios.get<CourseResponse[]>("http://localhost:8080/course-management/get-all-courses"); 
       console.log("Get all courses successfull!")
       return (await response).data;
    } catch(error) {
       console.log("Encountered error while get course: ", error)
       throw error;
    }
 
 }

function resetForm(formRequest: FormRequest){
    formRequest.name = '', 
    formRequest.email = '',
    formRequest.phone = '',
    formRequest.address= '', 
    formRequest.courseId = -1
 }