export function isAssigned(beginDate: string | null, endDate: string | null, beginTime: string | null, endTime: string | null, lecturerId: number | null): boolean {
    if (beginDate === null && endDate === null && beginTime === null && endTime === null && lecturerId === null) {
        return false;
    }
    return true;
}
