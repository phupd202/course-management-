import { PersonalEvent } from "@/interface/lecturer/PeronalEvent";


export function getTimeOfStringDate(timeMixDay: string): string {
    const date = new Date(timeMixDay);

    const hour = ('0' + date.getHours()).slice(-2); // Lấy giờ và thêm số 0 nếu cần
    const minute = ('0' + date.getMinutes()).slice(-2); // Lấy phút và thêm số 0 nếu cần
    const second = ('0' + date.getSeconds()).slice(-2); // Lấy giây và thêm số 0 nếu cần

    return `${hour}:${minute}:${second}`;
}

export function resetModal(newEvent: PersonalEvent): void {
    newEvent.date = "";
    newEvent.beginTime= "";
    newEvent.endTime = "";
    newEvent.title = "";
}

export function hideModal(isVisible: boolean): void {
    if(isVisible) {
        isVisible = false;
    }
}

export function showModal(isVisible: boolean): void {
    if(isVisible === false) {
        isVisible = true;
    }
}


