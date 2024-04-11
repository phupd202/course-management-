export interface Enrollment {
    enrollmentId: number,
    name: string | null, 
    phone: string | null,
    email: string | null,
    address: string | null
    score: number, 
    status: string
}