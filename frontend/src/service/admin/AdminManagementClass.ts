import { Classroom } from "@/interface/admin/Classroom";
import { CreateClassroom } from "@/interface/admin/CreateClassroom";
import { SelectCourse } from "@/interface/admin/SelectCourse";
import axios from "axios";

export async function getOptionSelect(jwtToken: string): Promise<SelectCourse[]> {
    try {
        const response = await axios.get<SelectCourse[]>('http://localhost:8080/course-management/admin/management-class/select-course', {
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        });
        console.log('Dữ liệu đã được lấy thành công:', response.data);
        return response.data;
    } catch (error) {
        console.error('Lỗi khi lấy dữ liệu:', error);
        throw error; // Ném lỗi để xử lý ở nơi gọi hàm này
    }
}

export async function getClassrooms(jwtToken: string, selectedCourseId: number) : Promise<Classroom[]> {
    try {
        const response = await axios.get<Classroom[]>("http://localhost:8080/course-management/admin/management-class/" + selectedCourseId, {
            headers: {
            'Authorization': `Bearer ${jwtToken}`
            }
        });

        console.log("Fetch classroom is successfully!", response.data)
        return response.data;
    } catch (error) {
        console.log("Có lỗi khi lấy danh sách lớp học");
        throw error;
    }
}

export async function postClassroom(jwtToken: string, createClassroom: CreateClassroom, selectedCourseId: number) {
    try {
        const response = await axios.post<CreateClassroom>("http://localhost:8080/course-management/admin/management-class/add-classroom", createClassroom, {
            headers: {
            'Authorization': `Bearer ${jwtToken}`
            }
        });

        alert("Thêm môn lớp học thành công!!!")
        await getClassrooms(jwtToken, selectedCourseId);
        console.log('Dữ liệu đã được gửi thành công:', response.data);
    } catch (error: any) {
        if (error && error.response && error.response.data && error.response.data.message === 'Mã lớp đã bị trùng') {
            console.error('Mã lớp đã bị trùng');
            alert("Mã lớp đã tồn tại trong cơ sở dữ liệu")
            console.log('Dữ liệu gửi đi là: ', createClassroom)
            console.log("postClassroom, CourseId: ", createClassroom)
        } else {
            console.error('Lỗi khi tạo lớp:', error);
            alert("Lỗi khi tạo lớp")
            console.log('Dữ liệu gửi đi là: ', createClassroom);
        }
    }
}