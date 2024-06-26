import axios from 'axios';
import { CourseData } from './../../interface/admin/CourseData';
import { CourseResponse } from '@/interface/admin/CourseResponse';

export async function createCourse(courseData: CourseData): Promise<void> {
    try {
        const response = await axios.post<CourseData>('http://localhost:8080/course-management/admin/courses', courseData)
        console.log("Khoá học đã được tạo: ", response.data)
        
        // resetForm();
        // fetchCourses();
        // hideModal();
    } catch (error) {
        console.log("Have a error when create course")
    }
}

// update course
export async function updateCourse(jwtToken: string, courseResponse: CourseResponse): Promise<void> {
    try {
        const response = await axios.post<CourseResponse>("http://localhost:8080/course-management/admin/update-course", courseResponse, {
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        })
        console.log("Update course succesfully!", response)
    } catch(error) {
        console.log("Hav a error when update course")
    }
}

// search course
export async function searchCourse(keyword: string): Promise<CourseResponse[]> {
    try {
        const response = await axios.get<CourseResponse[]>("http://localhost:8080/course-management/admin/search-course", {
            params: {
                keyword: keyword
            }
        });
        console.log("Fetch course by keyword successfull!")
        return response.data;
    } catch(error) {
        console.log("Hava a error while search!!")
        throw error;
    }
}
