export interface UpdateScore {
    classroomId: number, 
    subjectId: number,  
    scores: number[]
    enrollmentIds: number[]
    statues: string[]
}