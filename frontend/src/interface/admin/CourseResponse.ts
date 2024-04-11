export interface CourseResponse {
    courseId: number;
    nameCourse: string | null;
    createdAt: string;
    numClass: number;
    isClosed: boolean;
    url: string;
    description: string;
    price: number;
}