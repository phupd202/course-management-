export interface AssignmentLecturer {
    assignmentId: number
    classroomId: number, 
    beginDate: string, 
    endDate: string , 
    beginTime: string | null, 
    endTime: string | null, 
    codeClassroom: string | null, 
    nameSubject: string | null, 
    dayOfWeek: number,
    subjectId: number
}