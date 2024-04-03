export interface RegisterResponse {
    registerId: number,
    courseId: number,
    classroomId: number,
    name: string,
    email: string, 
    phone: string
    address: string
    createdAccount: boolean, 
    addedClassroom: boolean,
}