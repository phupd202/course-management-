import axios from 'axios';
import { Register } from './../../interface/home/Register';
import { Arcodition } from "@/interface/home/Arcodition";
import { ClassroomOfCourse } from '@/interface/home/ClassroomOfCourse';

export async function processText(text: string): Promise<Arcodition[]> {
    console.log("Đã chạy hàm processText: ")
    const lines = text.split("\n");
    const arcodition: Arcodition[] = [];
    let currentItem: Arcodition | null = null;

    lines.forEach(line => {
        console.log("line ngoài if: ", line)
        if (line.match(/^\d+\./)) {
            if (currentItem !== null) {
                arcodition.push(currentItem);
            }
            // if is "1., 2. v..v" add into object.title
            currentItem = { title: line.replace(/^\d+\.\s/, ''), content: '\n' };
            console.log("line: ", line)
        } else if (line.match(/^-\s/)) {
            // if is "-" add into object.content
            if (currentItem !== null) {
                currentItem.content += "- " + line.replace(/^- /, '').replace(/\n/, '') + '\n';
            }
            console.log("content: ", line)
        }
        console.log('currentItem: ', currentItem)
    });

    // add last item into object
    if (currentItem !== null) {
        arcodition.push(currentItem);
    }
    return arcodition;
}

export async function sendMailRegister(classroomId: number, courseId: number, register: Register): Promise<void> {
    try {
        register.classroomId = classroomId;
        register.courseId = courseId;
        await axios.post<Register>("http://localhost:8080/course-management/mail-confirm", register);
        alert("Đăng ký thành công, vui lòng kiểm tra hòm thư của bạn!!");
        resetForm(register);
        console.log("The data was sent successfully");
    } catch (error) {
        console.error("An error occurred while sending mail registration:", error);
        alert("Vui lòng thử lại!");
        resetForm(register)
        throw error; // Re-throw the error to handle it in the component if needed
    }
}

function resetForm(register: Register): void {
    register.name = '';
    register.phone = '';
    register.address = '';
    register.email = '';
    register.classroomId = -1;
    register.courseId = -1;
}

// get classroom by courseId
export async function getClassroom(courseId: number): Promise<ClassroomOfCourse[]> {
    try {
        const response = await axios.get<ClassroomOfCourse[]>("http://localhost:8080/course-management/get-class/" + courseId);
        console.log("Data đã nhận được", response.data);
        return response.data;
    } catch (error) {
        console.error("Failure when getting class of course", error);
        throw error;
    }
}
