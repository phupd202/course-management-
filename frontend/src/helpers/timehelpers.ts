
export function countDayInWeekBetweenDate(beginDate: Date, endDate: Date, dayOfWeek: number): number {
    const oneDay = 24 * 60 * 60 * 1000; 
    const daysDiff = Math.round(Math.abs((beginDate.getTime() - endDate.getTime()) / oneDay));

    const remainingDays = daysDiff % 7; 

    let count = 0;

    // tính số ngày dayOfWeek trong 2 khoảng thời gian k
    if (beginDate.getDay() === dayOfWeek) { 
        count = Math.floor(remainingDays / 7);
    } else {
        const daysToNextThursday = dayOfWeek - beginDate.getDay(); 
        count = Math.floor((remainingDays - daysToNextThursday) / 7) + 1;
    }

    return count;
}

// Tìm ngày có dayOfWeek gần với beginDate nhất
export function findClosestDayOfWeek(beginDate: Date, dayOfWeek: number): Date {
    const beginDay = beginDate.getDay(); // Lấy ngày trong tuần của beginDate

    let diff = dayOfWeek - beginDay;

    // Tìm chênh lệch
    if (diff < 0) {
        diff += 7; 
    }

    // Sao chép beginDate để không thay đổi giá trị gốc
    const resultDate = new Date(beginDate);

    // Thêm sự chênh lệch vào beginDate để tìm ngày gần nhất có dayOfWeek
    resultDate.setDate(beginDate.getDate() + diff);

    return resultDate;
}


export function countDaysBetweenDate(beginDate: Date, endDate: Date): number {
    const timeDiff = endDate.getTime() - beginDate.getTime();

    const daysDiff = Math.round(timeDiff / (1000 * 3600 * 24));

    return daysDiff;
}

// Lấy phần date của chuỗi 2002-14-3 15:00:00
export function getDatePart(dateString: string): string {
    const parts = dateString.split(' ');
    const datePart = parts[0];
    return datePart;
}

export function formatDateTime(dateTimeStr: string) {
    const dateTime = new Date(dateTimeStr);
    const day = dateTime.getDate();
    const month = dateTime.getMonth() + 1; 
    const year = dateTime.getFullYear();

    const formattedDay = day < 10 ? '0' + day : day;
    const formattedMonth = month < 10 ? '0' + month : month;
    return formattedDay + '/' + formattedMonth + '/' + year;
}

export function getCurrentDate() {
    return new Date();
}

export function isBeforeCurrentDay(dateTimeStr: string) {
    const dateTime = new Date(dateTimeStr);

    const currentTime = new Date();

    return currentTime.getTime() > dateTime.getTime();
}


export function isAfterCurrentDay(dateTimeStr: string) {
    const dateTime = new Date(dateTimeStr);

    const currentTime = new Date();

    return currentTime.getTime() < dateTime.getTime();
}

