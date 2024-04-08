
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

export function findFirstDayBeginDate(beginDate: Date, dayOfWeek: number): Date {
    const dayBeginDate = beginDate.getDay() - 1;

    const diffDay = dayOfWeek - beginDate.getDay();

    if(diffDay >= 0) {
        return new Date(beginDate.setDate(beginDate.getDate()) + diffDay)
    } else {
        return new Date(beginDate.setDate(beginDate.getDate()) + 7 - diffDay)
    }
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

