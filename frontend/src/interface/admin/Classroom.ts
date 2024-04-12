export interface Classroom {
    classroomId: number | null,
    beginDate: string,
    endDate: string,
    codeClassroom: string,
    sumPeriod: number | null,
    sumStudent: number | null,
    isFinished: boolean
}